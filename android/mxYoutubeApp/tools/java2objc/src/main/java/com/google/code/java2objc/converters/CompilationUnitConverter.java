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
package com.google.code.java2objc.converters;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.TypeDeclaration;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.google.code.java2objc.code.ObjcType;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.googlecode.java2objc.main.Config;
import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.ObjcTypeRepository;

/**
 * Converts a Java compilation unit into its equivalent Objective C classes
 * 
 * @author Inderjeet Singh
 */
public final class CompilationUnitConverter {

  private final CompilationUnit cu;
  private final Config config;
  private final File file;
  private final Properties mappings;
  private final List<ObjcType> objcTypes;

  public CompilationUnitConverter(Config config, CompilationUnit cu, File file,
      Properties mappings) {
    this.cu = cu;
    this.config = config;
    this.file = file;
    this.mappings = mappings;
    this.objcTypes = parseInput();
  }

  public List<ObjcType> getObjcTypes() {
    return objcTypes;
  }

  private List<ObjcType> parseInput() {
    CompilationContext context = new CompilationContext(config);
    ObjcTypeRepository repo = new ObjcTypeRepository(context);
    context.initRepo(repo);
    addExternalMappings(context);

    PackageDeclaration pkg = cu.getPackage();
    String pkgName = pkg != null ? pkg.getName().toString() : null;
    Set<ObjcType> imports = toObjcImports(pkgName, repo, cu.getImports());
    context.init(imports);

    List<ObjcType> objcTypes = Lists.newArrayList();
    if (cu.getTypes() != null) {
      for (TypeDeclaration type : cu.getTypes()) {
        if (type instanceof ClassOrInterfaceDeclaration || type instanceof EnumDeclaration) {
          ObjcType objcType = context.getTypeConverter().to(type, pkgName);
          if (objcType != null) {
            objcTypes.add(objcType);
          }
        }
      }
    }
    return objcTypes;
  }

  private void addExternalMappings(CompilationContext context) {
    if (mappings != null) {
      ObjcTypeRepository repo = context.getTypeRepo();
      for (Object key : mappings.keySet()) {
        String fullName = key.toString();
        String className = getClassName(fullName);
        String pkgName = getPkgName(fullName);
        String[] value = mappings.get(key).toString().trim().split("[\\s,]+");
        String kind = "class";
        if (value.length > 1) {
          kind = value[1];
        }
        if ("class".equalsIgnoreCase(kind)) {
          ObjcType type = repo.getOrCreate(null, value[0], new ObjcType(context, value[0], false, true));
          repo.put(pkgName, className, type);
        } else if ("struct".equalsIgnoreCase(kind)) {
          ObjcType type = repo.getOrCreate(null, value[0], new ObjcType(context, value[0], false, false));
          repo.put(pkgName, className, type);
        } else if ("protocol".equalsIgnoreCase(kind)) {
          ObjcType type = repo.getOrCreate(null, value[0], new ObjcType(context, value[0], true, true));
          repo.put(pkgName, className, type);
        } else if ("method".equalsIgnoreCase(kind)) {
          String methodName = className;
          String objcMethodName = getClassName(value[0]);
          String objcClassName = getPkgName(value[0]);
          className = getClassName(pkgName);
          pkgName = getPkgName(pkgName);
          ObjcType type = repo.getOrCreate(pkgName, className, objcClassName);
          type.addMethodMapping(methodName, objcMethodName);
        }
      }
    }
  }

  private Set<ObjcType> toObjcImports(String thisPkgName, ObjcTypeRepository repo,
      List<ImportDeclaration> importedTypes) {
    Set<ObjcType> imports = Sets.newHashSet();
    if (importedTypes != null) {
      for (ImportDeclaration importedType : importedTypes) {
        String fullyQualifiedClassName = importedType.getName().toString();
        String pkgName = getPkgName(fullyQualifiedClassName);
        String className = getClassName(fullyQualifiedClassName);
        if (importedType.isAsterisk()) {
          pkgName = fullyQualifiedClassName;
          List<String> classNames = getClassNames(thisPkgName, pkgName);
          for (String name : classNames) {
            imports.add(repo.getOrCreate(pkgName, name, prefix(name)));
          }
        } else if (importedType.isStatic()) {
          className = getClassName(pkgName);
          pkgName = getPkgName(pkgName);
          imports.add(repo.getOrCreate(pkgName, className, prefix(className)));
        } else {
          imports.add(repo.getOrCreate(pkgName, className, prefix(className)));
        }
      }
    }
    return imports;
  }

  private String prefix(String name) {
    return config.getPrefix() != null ? config.getPrefix() + name : name;
  }

  private String getPkgName(String fullyQualifiedClassName) {
    int index = fullyQualifiedClassName.lastIndexOf('.');
    return index == -1 ? null : fullyQualifiedClassName.substring(0, index);
  }

  private String getClassName(String fullyQualifiedClassName) {
    int index = fullyQualifiedClassName.lastIndexOf('.');
    return index == -1 ? fullyQualifiedClassName : fullyQualifiedClassName.substring(index + 1);
  }

  private List<String> getClassNames(String thisPkgName, String importPkgName) {
    List<String> classNames = Lists.newArrayList();
    // try to find matching source code
    StringBuilder path = new StringBuilder("../");
    if (thisPkgName != null) {
      int index = thisPkgName.indexOf('.');
      while (index != -1) {
        path.append("../");
        index = thisPkgName.indexOf('.', index + 1);
      }
    }
    path.append(importPkgName.replace('.', '/'));
    File pkgFile = new File(file.getParentFile(), path.toString());
    if (pkgFile.exists() && pkgFile.isDirectory()) {
      String[] files = pkgFile.list();
      for (String file : files) {
        if (file.endsWith(".java")) {
          classNames.add(file.substring(0, file.length() - 5));
        }
      }
    }
    return classNames;
  }
}
