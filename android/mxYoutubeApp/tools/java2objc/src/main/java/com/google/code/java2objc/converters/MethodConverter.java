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

import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.InitializerDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;

import com.google.code.java2objc.code.ObjcMethod;
import com.google.code.java2objc.code.ObjcMethodInit;
import com.google.code.java2objc.code.ObjcStatementBlock;
import com.googlecode.java2objc.objc.CompilationContext;

/**
 * Converts Java methods to their equivalent Objective C methods
 * 
 * @author Inderjeet Singh
 */
public class MethodConverter {
  private final CompilationContext context;

  public MethodConverter(CompilationContext context) {
    this.context = context;
  }

  public ObjcMethod to(MethodDeclaration method) {
    return new ObjcMethod(context, method);
  }

  public ObjcMethodInit to(ConstructorDeclaration constructor) {
    return new ObjcMethodInit(context, constructor);
  }

  public ObjcMethod to(InitializerDeclaration initializer) {
    String comments = null;
    if (initializer.getJavaDoc() != null) {
      comments = initializer.getJavaDoc().getContent();
    }
    ObjcStatementBlock body = new ObjcStatementBlock(context, initializer.getBlock());
    return new ObjcMethod(context, "initialize", context.getTypeRepo().getVoid(), null,
        ModifierSet.STATIC, body, comments);
  }
}
