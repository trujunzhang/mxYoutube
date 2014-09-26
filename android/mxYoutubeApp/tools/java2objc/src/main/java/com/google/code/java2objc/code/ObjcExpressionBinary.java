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

import japa.parser.ast.expr.BinaryExpr;

import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.ObjcOperator;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * A binary expression in Objective C
 * 
 * @author Inderjeet Singh
 */
public class ObjcExpressionBinary extends ObjcExpression {

  private final ObjcOperator operator;
  private final ObjcExpression left;
  private final ObjcExpression right;

  public ObjcExpressionBinary(CompilationContext context, BinaryExpr expr) {
    this(new ObjcOperator(expr.getOperator()), context.getExpressionConverter().to(expr.getLeft()),
        context.getExpressionConverter().to(expr.getRight()));
  }

  public ObjcExpressionBinary(ObjcOperator operator, ObjcExpression left, ObjcExpression right) {
    super((ObjcType)null);
    this.operator = operator;
    this.left = left;
    this.right = right;
  }

  public ObjcOperator getOperator() {
    return operator;
  }

  public ObjcExpression getLeft() {
    return left;
  }

  public ObjcExpression getRight() {
    return right;
  }

  @Override
  public void append(SourceCodeWriter writer) {
    if ("+".equals(operator.getOperator()) && (isString(left) || isString(right))) {
      writer.append('[').append(left).append(" stringByAppendingString:").append(right).append(']');
    } else {
      writer.append(left).append(" ");
      writer.append(operator).append(" ");
      writer.append(right);
    }
  }

  private boolean isString(ObjcExpression expr) {
    return expr.getType() != null && "NSString".equals(expr.getType().getName());
  }
}
