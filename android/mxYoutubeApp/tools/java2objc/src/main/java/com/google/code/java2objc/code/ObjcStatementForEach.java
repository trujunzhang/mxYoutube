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
import com.googlecode.java2objc.objc.SourceCodeWriter;

import japa.parser.ast.expr.VariableDeclarationExpr;
import japa.parser.ast.stmt.ForeachStmt;

/**
 * Objective C for-each statement
 * 
 * @author Inderjeet Singh
 */
public final class ObjcStatementForEach extends ObjcStatement {

  private final String varName;
  private final ObjcType varType;
  private final ObjcExpression iterable;
  private final ObjcStatement body;
  public ObjcStatementForEach(CompilationContext context, ForeachStmt stmt) {
    VariableDeclarationExpr variable = stmt.getVariable();
    this.varName = variable.getVars().get(0).getId().getName();
    this.varType = context.getTypeRepo().getOrCreate(variable.getType());
    this.iterable = context.getExpressionConverter().to(stmt.getIterable());
    this.body = context.getStatementConverter().to(stmt.getBody());
  }
  
  public String getVarName() {
    return varName;
  }

  public ObjcType getVarType() {
    return varType;
  }

  public ObjcExpression getIterable() {
    return iterable;
  }

  public ObjcStatement getBody() {
    return body;
  }

  public ObjcStatementBlock getBodyAsBlock() {
    return (ObjcStatementBlock) body;
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.newLine();
    writer.append("for (").append(varType.getPointerTypeName()).append(" ");
    writer.append(varName).append(" in ");
    writer.append(iterable);
    writer.append(')');
    if (body instanceof ObjcStatementBlock) {
      writer.append(' ');
    } else {
      writer.newLine().indent();
    }
    writer.append(body);
    if (!(body instanceof ObjcStatementBlock)) {
      writer.unIndent();
    }
    writer.newLine();    
  }
}
