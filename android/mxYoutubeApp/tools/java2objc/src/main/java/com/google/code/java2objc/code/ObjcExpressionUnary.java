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

import japa.parser.ast.expr.UnaryExpr;
import japa.parser.ast.expr.UnaryExpr.Operator;

import com.google.code.java2objc.converters.ExpressionConverter;
import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.ObjcOperator;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * A unary expression in Objective C
 * 
 * @author David Gileadi
 */
public class ObjcExpressionUnary extends ObjcExpression {

  private final ObjcOperator operator;
  private final boolean post;
  private final ObjcExpression expr;

  public ObjcExpressionUnary(CompilationContext context, UnaryExpr expr) {
    super((ObjcType)null);
    operator = new ObjcOperator(expr.getOperator());
    post = expr.getOperator() == Operator.posDecrement || expr.getOperator() == Operator.posIncrement;
    ExpressionConverter exprConverter = context.getExpressionConverter();
    this.expr = exprConverter.to(expr.getExpr());
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    if (post) {
      writer.append(expr).append(operator);
    } else {
      writer.append(operator).append(expr);
    }
  }
}
