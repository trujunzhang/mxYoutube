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

import japa.parser.ast.expr.InstanceOfExpr;

import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * An is-kind-of expression in Objective C
 * 
 * @author David Gileadi
 */
public class ObjcExpressionIsKindOf
    extends ObjcExpression {

  private final ObjcExpression expr;
  private final ObjcType type;
  private final boolean isProtocol;

  public ObjcExpressionIsKindOf(CompilationContext context, InstanceOfExpr expr) {
    super(context.getTypeRepo().get("BOOL"));
    this.expr = context.getExpressionConverter().to(expr.getExpr());
    this.type = context.getTypeRepo().getOrCreate(expr.getType());
    this.isProtocol = type.isProtocol();
  }

  @Override
  public void append(SourceCodeWriter writer) {
    if (isProtocol) {
      writer.append('[').append(expr).append(" isKindOfClass:[").append(type.getName()).append(" class]]");
    } else {
      writer.append('[').append(expr).append(" conformsToProtocol:@protocol(").append(type.getName()).append(")]");
    }
  }
}
