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
package com.google.code.java2objc.code;

import japa.parser.ast.body.ModifierSet;

import com.google.code.java2objc.code.ObjcStatementBlock.Builder;
import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.ObjcField;
import com.googlecode.java2objc.objc.ObjcVariableDeclarator;

/**
 * Method for implementing dealloc for an object
 * 
 * @author Inderjeet Singh
 */
public final class ObjcMethodDealloc extends ObjcMethod {

  public ObjcMethodDealloc(CompilationContext context, ObjcType parent) {
    super(context, "dealloc", context.getTypeRepo().getVoid(), null, ModifierSet.PRIVATE,
        getDeallocBody(context, parent), null);
  }

  private static ObjcStatementBlock getDeallocBody(CompilationContext context, ObjcType parent) {
    // release all fields
    Builder builder = new ObjcStatementBlock.Builder();
    for (ObjcField field : parent.fields) {
      if (!ModifierSet.isStatic(field.getModifiers()) && field.getType().isPointerType()) {
        for (ObjcVariableDeclarator decl : field.getVars()) {
          ObjcExpressionMethodCall release =
              new ObjcExpressionMethodCall(context, new ObjcExpressionSimple(context, decl.getName()),
                  "release", null);
          builder.addStatement(new ObjcStatementExpression(release));
        }
      }
    }
    builder.addStatement(new ObjcStatementSimple("[super dealloc];"));
    return builder.build();
  }
}
