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

import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.AssignExpr.Operator;
import japa.parser.ast.expr.NameExpr;

import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.ObjcOperator;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * An assign expression in Objective C
 * 
 * @author David Gileadi
 */
public class ObjcExpressionAssign extends ObjcExpression {

  private final ObjcExpression target;
  private final ObjcExpression value;
  private final ObjcOperator operator;

  public ObjcExpressionAssign(CompilationContext context, AssignExpr expr) {
    super(getTarget(context, expr));
    this.target = getTarget(context, expr);
    this.value = context.getExpressionConverter().to(expr.getValue());
    this.operator = new ObjcOperator(expr.getOperator());
  }

  public ObjcExpressionAssign(ObjcExpression target, ObjcExpression value) {
    this(target, value, new ObjcOperator(Operator.assign));
  }

  public ObjcExpressionAssign(ObjcExpression target, ObjcExpression value, ObjcOperator operator) {
    super(target);
    this.target = target;
    this.value = value;
    this.operator = operator;
  }

  public ObjcExpression getTarget() {
    return target;
  }

  public ObjcExpression getValue() {
    return value;
  }

  public ObjcOperator getOperator() {
    return operator;
  }

  private static ObjcExpression getTarget(CompilationContext context, AssignExpr expr) {
    // rewrite to self.target if not accessing a local
    if ((expr.getTarget() instanceof NameExpr) && !context.isLocalDeclared(((NameExpr)expr.getTarget()).getName())) {
      return new ObjcExpressionFieldAccess(new ObjcExpressionSimple(context, "self"),
          ((NameExpr)expr.getTarget()).getName());
    } else {
      return context.getExpressionConverter().to(expr.getTarget());
    }
  }

  @Override
  public void append(SourceCodeWriter writer) {
    if ("+=".equals(operator.getOperator()) && (isString(target) || isString(value))) {
      writer.append(target).append(" = ");
      writer.append('[').append(target).append(" stringByAppendingString:").append(value).append(']');
    } else {
      writer.append(target).append(' ').append(operator).append(' ').append(value);
    }
  }

  private boolean isString(ObjcExpression expr) {
    return expr.getType() != null && "NSString".equals(expr.getType().getName());
  }
}
