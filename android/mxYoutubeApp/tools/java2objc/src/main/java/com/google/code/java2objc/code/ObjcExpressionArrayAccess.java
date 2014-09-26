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

import japa.parser.ast.expr.ArrayAccessExpr;

import com.google.code.java2objc.converters.ExpressionConverter;
import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * An Objective C expression involving an array access
 * 
 * @author David Gileadi
 */
public class ObjcExpressionArrayAccess extends ObjcExpression {

  protected final ObjcExpression index;
  protected final ObjcExpression name;

  public ObjcExpressionArrayAccess(CompilationContext context, ArrayAccessExpr expr) {
    super(context.getExpressionConverter().to(expr.getName()));
    ExpressionConverter converter = context.getExpressionConverter();
    this.index = converter.to(expr.getIndex());
    this.name = converter.to(expr.getName());
  }

  @Override
  public void append(SourceCodeWriter writer) {
    // TODO(inder): If we are accessing an NSArray for RHS expression we should instead be
    // returning [name objectAtIndex: index]. The following expression achieves that
    // writer.append('[').append(name).append(" objectAtIndex: ").append(index).append(']');
    // However, for LHS access of an array (for array assignment), we should be using
    // [name insertObject: @value atIndex: index]
    
    writer.append(name).append('[').append(index).append(']');
  }
}
