/*
 * Copyright (C) 2009 Inderjeet Singh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.java2objc.main;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.google.code.java2objc.code.ObjcType;
import com.google.code.java2objc.converters.CompilationUnitConverter;
import com.google.common.collect.Lists;
import com.googlecode.java2objc.util.OrderedProperties;
import com.googlecode.java2objc.util.Preconditions;

/**
 * Main entry point for java2objc
 * 
 * @author Inderjeet Singh
 */
public final class Main {

  private static void printUsageAndExit() {
    System.err.printf(
        "Usage: java -jar %s java2objc.jar /path/to/MyClass1.java /path/to/second/MyClass2.java\n",
        Config.availableOptions());
    System.exit(-1);
  }

  private final Config config;
  private final Collection<String> javaFiles;
  
  public Main(Config config, Collection<String> javaFiles) {
    this.config = config;
    this.javaFiles = javaFiles;
  }

  public static void main(String[] args) throws Exception {
    if (args.length == 0) {
      printUsageAndExit();
    }
    Config config = new Config();
    List<String> javaFiles = Lists.newArrayList();
    for (String arg : args) {
      if (arg.startsWith("--")) {
        // A configuration setting
        config.update(arg);
      } else { // Probably a Java file
        javaFiles.add(arg);
      }
    }
    Main main = new Main(config, javaFiles);
    main.execute();
  }
  
  public void execute() throws IOException, ParseException {
    List<List<ObjcType>> objcTypes = convertFilesToObjcTypes();
    ObjcFileGenerator generator = new ObjcFileGenerator(config.getOutputDir(), config.getIndent());
    generator.writeSourceCodeForFiles(objcTypes);
  }

  public List<List<ObjcType>> convertFilesToObjcTypes() throws IOException, ParseException {
    // load the default mappings
    Properties mappings = new OrderedProperties();
    InputStream in = getClass().getClassLoader().getResourceAsStream("mappings.properties");
    if (in != null) {
      mappings.load(in);
    }

    List<List<ObjcType>> objcTypes = Lists.newArrayList();
    processFiles(null, javaFiles.toArray(new String[0]), mappings, objcTypes);
    return objcTypes;
  }

  private void processFiles(File dir, String[] fileNames, Properties mappings,
      List<List<ObjcType>> objcTypes)
      throws ParseException, IOException {
    for (String fileName : fileNames) {
      fileName = fileName.trim();
      File file = (dir != null) ? new File(dir, fileName) : new File(fileName);
      mappings = loadLocalMappings(file, mappings);
      // If a directory, recurse through its subdirectories and .java files
      if (file.isDirectory()) {
        String[] files = file.list(new FilenameFilter() {

          @Override
          public boolean accept(File dir, String name) {
            File file = new File(dir, name);
            return !file.isHidden() && (name.endsWith(".java") || file.isDirectory());
          }
        });

        // update the working directory and process children
        File workingDir = config.getWorkingDir();
        if (dir != null) {
          config.setWorkingDir(new File(config.getWorkingDir(), file.getName()));
        }
        processFiles(file, files, mappings, objcTypes);
        config.setWorkingDir(workingDir);
      } else {
        objcTypes.add(convertJavaFileToObjcTypes(mappings, fileName, file));
      }
    }
  }

  private List<ObjcType> convertJavaFileToObjcTypes(Properties mappings, String fileName, File file)
      throws FileNotFoundException, ParseException {
    Preconditions.assertTrue(fileName.endsWith(".java"), fileName + " isn't a Java file.");
    FileInputStream in = new FileInputStream(file);
    CompilationUnit cu = JavaParser.parse(in);
    CompilationUnitConverter conv = new CompilationUnitConverter(config, cu, file, mappings);
    return conv.getObjcTypes();
  }

  private Properties loadLocalMappings(File forFile, Properties mappings) {
    File file;
    if (forFile.isDirectory()) {
      file = new File(forFile, "mappings.properties");
    } else {
      file = new File(forFile.getParent(), "mappings.properties");
    }
    if (file.exists()) {
      mappings = (Properties)mappings.clone();
      try {
        mappings.load(new FileInputStream(file));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return mappings;
  }
}
