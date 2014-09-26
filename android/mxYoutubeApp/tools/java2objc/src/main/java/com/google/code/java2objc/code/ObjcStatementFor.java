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

import japa.parser.ast.stmt.ForStmt;

import java.util.List;

import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * Objective C For statement
 * 
 * @author Inderjeet Singh
 */
public final class ObjcStatementFor extends ObjcStatement {
  
  private final List<ObjcExpression> init;
  private final ObjcExpression compare;
  private final List<ObjcExpression> update;
  private final ObjcStatement body;

  public ObjcStatementFor(CompilationContext context, ForStmt stmt) {
    this.init = context.getExpressionConverter().to(stmt.getInit());
    this.compare = context.getExpressionConverter().to(stmt.getCompare());
    this.update = context.getExpressionConverter().to(stmt.getUpdate());
    this.body = context.getStatementConverter().to(stmt.getBody());
  }
  
  public List<ObjcExpression> getInit() {
    return init;
  }

  public ObjcExpression getCompare() {
    return compare;
  }

  public List<ObjcExpression> getUpdate() {
    return update;
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
    writer.append("for (").append(init, ", ").append("; ");
    writer.append(compare).append("; ");
    writer.append(update, ", ").append(')');
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
