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

import japa.parser.ast.stmt.WhileStmt;

/**
 * An Objective C while statement
 * 
 * @author Inderjeet Singh
 */
public final class ObjcStatementWhile extends ObjcStatement {

  private final ObjcExpression condition;
  private final ObjcStatement body;

  public ObjcStatementWhile(CompilationContext context, WhileStmt stmt) {
    this.condition = context.getExpressionConverter().to(stmt.getCondition());
    this.body = context.getStatementConverter().to(stmt.getBody());
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.newLine();
    writer.append("while (").append(condition).append(')');
    boolean isBlock = body instanceof ObjcStatementBlock;
    if (isBlock) {
      writer.append(' ');
    } else {
      writer.newLine().indent();
    }
    writer.append(body);
    if (!isBlock) {
      writer.unIndent();
    }
    writer.newLine();
  }
}
