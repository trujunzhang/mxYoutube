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
package com.googlecode.java2objc.objc;

import java.util.List;

import com.google.code.java2objc.code.ObjcMethod;
import com.google.code.java2objc.code.ObjcStatementBlock;
import com.google.code.java2objc.code.ObjcType;
import com.google.common.collect.Lists;

/**
 * A Builder to build a user-defined Objective C type 
 * 
 * @author Inderjeet Singh
 */
public final class UserDefinedObjcTypeBuilder {
  private final CompilationContext context;
  private final ObjcType type;
  private final ObjcType containingClass;
  private ObjcType baseClass;
  private final List<ObjcType> protocols;
  private final List<ObjcMethod> methods;
  private final List<ObjcField> fields;
  protected final List<ObjcStatementBlock> initializers;
  private final List<ObjcType> subTypes;
  private final String comments;
  
  public UserDefinedObjcTypeBuilder(CompilationContext context, ObjcType type, String comments,
      ObjcType containingClass) {
    this.context = context;
    this.containingClass = containingClass;
    this.baseClass = context.getTypeRepo().getNSObject();
    this.protocols = Lists.newArrayList();
    this.methods = Lists.newArrayList();
    this.fields = Lists.newArrayList();
    this.initializers = Lists.newArrayList();
    this.subTypes = Lists.newArrayList();
    this.type = type;
    this.comments = comments;
    context.setCurrentType(type);
  }

  public ObjcType build() {
    type.init(context, baseClass, protocols, containingClass, methods, fields, initializers, subTypes, comments);
    context.setCurrentType(null);
    return type;
  }

  public UserDefinedObjcTypeBuilder setBaseClass(ObjcType baseClass) {
    this.baseClass = baseClass;
    return this;
  }

  public UserDefinedObjcTypeBuilder addProtocol(ObjcType protocol) {
    if (protocol != null) {
      protocols.add(protocol);
    }
    return this;
  }

  public UserDefinedObjcTypeBuilder addMethod(ObjcMethod objcMethod) {
    methods.add(objcMethod);
    return this;
  }

  public UserDefinedObjcTypeBuilder addField(ObjcField objcField) {
    fields.add(objcField);
    return this;
  }

  public UserDefinedObjcTypeBuilder addInitializer(ObjcStatementBlock initializer) {
    initializers.add(initializer);
    return this;
  }

  public UserDefinedObjcTypeBuilder addSubType(ObjcType objcType) {
    subTypes.add(objcType);
    return this;
  }
}
