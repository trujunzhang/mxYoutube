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
 */package com.googlecode.java2objc.objc;

import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.ReferenceType;
import japa.parser.ast.type.Type;

import java.util.Map;

import com.google.code.java2objc.code.ObjcType;
import com.google.common.collect.Maps;

public class ObjcTypeRepository {

  private final CompilationContext context;
  private final Map<String, ObjcType> types = Maps.newHashMap();
  
  public ObjcTypeRepository(CompilationContext context) {
    this.context = context;
  }

  public ObjcType get(Type type) {
    String typeName = getTypeName(type);
    ObjcType objcType = types.get(typeName);
    if (objcType == null) {
      objcType = types.get(context.prefix(typeName));
    }
    return objcType;
  }

  public ObjcType get(String className) {
    return types.get(className);
  }

  public void put(String name, ObjcType objcType) {
    put(null, name, objcType);
  }

  public void put(String pkgName, String className, ObjcType objcType) {
    if (!types.containsKey(objcType.getName())) {
      types.put(objcType.getName(), objcType);
    }
    types.put(className, objcType);
    if (pkgName != null) {
      types.put(pkgName + '.' + className, objcType);
    }
  }

  public ObjcType getOrCreate(Type type) {
    String name = getTypeName(type);
    boolean pointerType = type instanceof ReferenceType || type instanceof ClassOrInterfaceType;
    return getOrCreate(null, name, context.prefix(name), getNSObject(), pointerType);
  }

  private String getTypeName(Type type) {
    String name = null;
    if (type instanceof ReferenceType) {
      if (((ReferenceType)type).getArrayCount() > 0) {
        name = "Array";
      } else {
        type = ((ReferenceType)type).getType();
      }
    }
    if (type instanceof ClassOrInterfaceType) {
      name = ((ClassOrInterfaceType)type).getName();
    } else if (name == null) {
      name = type.toString();
    }
    return name;
  }
  
  public ObjcType getOrCreate(String pkgName, String name, String objcName) {
    return getOrCreate(pkgName, name, objcName, getNSObject(), true);
  }

  private ObjcType getOrCreate(String pkgName, String className, String objcName, ObjcType baseType, boolean pointerType) {
    if (objcName == null) {
      objcName = className;
    }
    return getOrCreate(pkgName, className, new ObjcType(context, objcName, baseType, null, pointerType));
  }

  public ObjcType getOrCreate(String pkgName, String className, ObjcType defaultType) {
    // Try to look up by name first
    ObjcType result = get(className);
    if (result == null && pkgName != null) {
      result = get(pkgName + '.' + className);
    }
    // If not found, create
    if (result == null) {
      result = defaultType;
      put(pkgName, className, result);
    }
    return result;
  }

  public ObjcType getNSObject() {
    return getOrCreate(null, "NSObject", null, null, true);
  }

  public ObjcType getVoid() {
    return getOrCreate(null, "void", null, null, false);
  }

  public ObjcType getId() {
    return getOrCreate(null, "id", null, null, false);
  }
}
