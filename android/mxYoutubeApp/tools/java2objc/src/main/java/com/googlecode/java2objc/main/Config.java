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

import java.io.File;

import com.googlecode.java2objc.util.Preconditions;

/**
 * Class that holds configuration of the command-line input parameters to the program
 * 
 * @author Inderjeet Singh
 */
public final class Config {
  private File outputDir = null;
  private File workingDir = null;
  private String prefix = null;
  private boolean preserveDirs = false;
  private int indentSize = 2;
  private boolean indentTabs = false;

  public File getOutputDir() {
    if (!preserveDirs || workingDir == null) {
      if (outputDir == null) {
        outputDir = new File(".");
      }
      return outputDir;
    } else {
      return workingDir;
    }
  }

  public File getWorkingDir() {
    if (workingDir == null) {
      workingDir = outputDir;
    }
    return workingDir;
  }

  public void setWorkingDir(File subDir) {
    this.workingDir = subDir;
  }

  public String getPrefix() {
    return prefix;
  }

  public String getIndent() {
    StringBuilder indent = new StringBuilder(indentSize);
    for (int i = 0; i < indentSize; i++) {
      indent.append(indentTabs ? '\t' : ' ');
    }
    return indent.toString();
  }

  public void update(String arg) {
    Preconditions.assertTrue(arg.startsWith("--"));
    String[] parts = arg.split("=");
    String name = parts[0];
    String value = parts.length > 1 ? parts[1] : null;
    if (name.equals("--outputdir")) {
      outputDir = new File(value);
    } else if (name.equals("--prefix")) {
      prefix = value;
    } else if (name.equals("--preservedirs")) {
      preserveDirs = value != null ? Boolean.parseBoolean(value) : true;
    } else if (name.equals("--indent")) {
      indentSize = Integer.parseInt(value);
    } else if (name.equals("--indentType")) {
      indentTabs = value.toLowerCase().startsWith("tab");
    }
  }

  static String availableOptions() {
    return "--outputdir=/tmp --preservedirs=false --indent=2 --indentType=space";
  }    
}