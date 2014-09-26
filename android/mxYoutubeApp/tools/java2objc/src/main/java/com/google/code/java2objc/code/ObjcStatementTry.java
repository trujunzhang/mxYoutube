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

import japa.parser.ast.stmt.CatchClause;
import japa.parser.ast.stmt.TryStmt;

import java.util.List;

import com.google.common.collect.Lists;
import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * Objective C try/catch/finally statement
 * 
 * @author David Gileadi
 */
public final class ObjcStatementTry
    extends ObjcStatement {

  private final ObjcStatementBlock tryBlock;
  private final ObjcStatementBlock finallyBlock;
  private final List<ObjcCatchClause> catchs;

  public ObjcStatementTry(CompilationContext context, TryStmt stmt) {
    this.tryBlock = new ObjcStatementBlock(context, stmt.getTryBlock());
    if (stmt.getFinallyBlock() != null) {
      this.finallyBlock = new ObjcStatementBlock(context, stmt.getFinallyBlock());
    } else {
      this.finallyBlock = null;
    }
    this.catchs = Lists.newArrayList();
    if (stmt.getCatchs() != null) {
      for (CatchClause clause : stmt.getCatchs()) {
        this.catchs.add(new ObjcCatchClause(context, clause));
      }
    }
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.newLine();
    writer.append("@try ").append(tryBlock);
    for (ObjcCatchClause ctch : catchs) {
      writer.append(ctch);
    }
    if (finallyBlock != null) {
      writer.append("@finally ").append(finallyBlock);
    }
  }
}
