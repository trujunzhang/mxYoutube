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

import japa.parser.ast.expr.CastExpr;

import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * A cast expression in Objective C
 * 
 * @author David Gileadi
 */
public class ObjcExpressionCast
    extends ObjcExpression {

  private final ObjcExpression expr;

  public ObjcExpressionCast(CompilationContext context, CastExpr expr) {
    super(context.getTypeRepo().getOrCreate(expr.getType()));
    this.expr = context.getExpressionConverter().to(expr.getExpr());
  }

  public ObjcExpression getExpression() {
    return expr;
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.append('(').append(getType().getPointerTypeName()).append(')').append(expr);
  }
}
