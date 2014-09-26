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

import japa.parser.ast.expr.VariableDeclarationExpr;

import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.ObjcVariable;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * A variable declaration expression in Objective C
 * 
 * @author David Gileadi
 */
public class ObjcExpressionVariableDeclaration extends ObjcExpression {

  private final ObjcVariable var;

  public ObjcExpressionVariableDeclaration(CompilationContext context, VariableDeclarationExpr expr) {
    super(context.getTypeRepo().getOrCreate(expr.getType()));
    this.var = new ObjcVariable(context, expr.getVars(), expr.getType());
  }

  public ObjcVariable getVar() {
    return var;
  }

  @Override
  public void append(SourceCodeWriter writer) {
    var.appendExpression(writer);
  }
}
