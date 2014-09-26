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

import japa.parser.ast.stmt.IfStmt;

import com.google.code.java2objc.converters.StatementConverter;
import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * Objective C if-then-else statement
 * 
 * @author Inderjeet Singh
 */
public final class ObjcStatementIf extends ObjcStatement {
  private final ObjcExpression condition;
  private final ObjcStatement thenStmt;
  private final ObjcStatement elseStmt;
  
  public ObjcStatementIf(CompilationContext context, IfStmt n) {
    this.condition = context.getExpressionConverter().to(n.getCondition());
    StatementConverter converter = context.getStatementConverter();
    this.thenStmt = converter.to(n.getThenStmt());
    this.elseStmt = converter.to(n.getElseStmt());
  }

  public ObjcStatementIf(ObjcExpression condition, ObjcStatement thenStmt, ObjcStatement elseStmt) {
    this.condition = condition;
    this.thenStmt = thenStmt;
    this.elseStmt = elseStmt;
  }

  public ObjcExpression getCondition() {
    return condition;
  }

  public ObjcStatement getThenStmt() {
    return thenStmt;
  }

  public ObjcStatement getElseStmt() {
    return elseStmt;
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.append("if (").append(condition).append(')');
    boolean isIfStmtABlock = thenStmt instanceof ObjcStatementBlock;
    if (isIfStmtABlock) {      
      writer.append(' ');
    } else {
      writer.newLine().indent();
    }
    writer.append(thenStmt);
    if (!isIfStmtABlock) {
      writer.unIndent();
    }
    if (elseStmt != null) {
      if (isIfStmtABlock) {
        writer.append(' ');
      }
      writer.append("else");      
      boolean isElseABlock = elseStmt instanceof ObjcStatementBlock;
      boolean isElseAnIfStmt = elseStmt instanceof ObjcStatementIf;
      if (isElseABlock || isElseAnIfStmt) {
        writer.append(' ');
      } else {
        writer.newLine().indent();
      }
      writer.append(elseStmt);      
      if (!isElseABlock && !isElseAnIfStmt) {
        writer.unIndent();
      }
    }
  }
}
