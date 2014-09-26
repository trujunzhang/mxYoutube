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

import japa.parser.ast.stmt.DoStmt;

/**
 * Representation of an Objective C do-while statement
 *
 * @author Inderjeet Singh
 */
public final class ObjcStatementDoWhile extends ObjcStatement {

  private final ObjcStatement body;
  private final ObjcExpression condition;

  public ObjcStatementDoWhile(CompilationContext context, DoStmt stmt) {
    body = context.getStatementConverter().to(stmt.getBody());
    condition = context.getExpressionConverter().to(stmt.getCondition());
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.newLine();
    writer.append("do ");
    writer.append(body);
    writer.append(" while (").append(condition).append(");").newLine();
  }
}
