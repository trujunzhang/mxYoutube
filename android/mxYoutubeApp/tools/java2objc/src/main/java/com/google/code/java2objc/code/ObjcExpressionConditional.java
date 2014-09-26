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

import japa.parser.ast.expr.ConditionalExpr;

import com.google.code.java2objc.converters.ExpressionConverter;
import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * A conditional (ternary) expression in Objective C
 * 
 * @author David Gileadi
 */
public class ObjcExpressionConditional extends ObjcExpression {

  private final ObjcExpression condition;
  private final ObjcExpression thenExpr;
  private final ObjcExpression elseExpr;

  public ObjcExpressionConditional(CompilationContext context, ConditionalExpr expr) {
    super((ObjcType)null);
    ExpressionConverter exprConverter = context.getExpressionConverter();
    this.condition = exprConverter.to(expr.getCondition());
    this.thenExpr = exprConverter.to(expr.getThenExpr());
    this.elseExpr = exprConverter.to(expr.getElseExpr());
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.append(condition).append(" ? ").append(thenExpr).append(" : ").append(elseExpr);
  }
}
