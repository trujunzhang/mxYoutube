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


import com.googlecode.java2objc.objc.CompilationContext;

import japa.parser.ast.expr.Expression;

/**
 * A boolean literal in Objective C
 * 
 * @author David Gileadi
 */
public final class ObjcExpressionBooleanLiteral extends ObjcExpressionSimple {

  private ObjcExpressionBooleanLiteral(CompilationContext context, boolean value) {
    super(value ? "YES" : "NO", context.getTypeRepo().get("BOOL"));
  }

  /**
   * @param context TODO
   * @param expr the Java expression
   */
  public ObjcExpressionBooleanLiteral(CompilationContext context, Expression expr) {
    this(context, Boolean.parseBoolean(expr.toString()));
  }
}
