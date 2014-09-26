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

import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.Expression;

import java.util.List;

import com.google.code.java2objc.converters.ExpressionConverter;
import com.google.common.collect.Lists;
import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * An object creation expression in Objective C
 * 
 * @author David Gileadi
 */
public class ObjcExpressionArrayInit extends ObjcExpression {

  private final List<ObjcExpression> initializers;

  public ObjcExpressionArrayInit(CompilationContext context, ArrayInitializerExpr expr) {
    super(context.getTypeRepo().get("NSArray"));
    ExpressionConverter converter = context.getExpressionConverter();
    this.initializers = Lists.newArrayList();
    if (expr != null) {
      for (Expression value : expr.getValues()) {
        this.initializers.add(converter.to(value));
      }
    }
  }

  @Override
  public void append(SourceCodeWriter writer) {
    if (initializers.size() > 0) {
      writer.append("[NSArray arrayWithObjects:");
      for (ObjcExpression value : this.initializers) {
        writer.append(value).append(", ");
      }
      writer.append("nil]");
    } else {
      writer.append("[NSArray array]");
    }
  }
}
