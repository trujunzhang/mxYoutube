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

import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.Statement;

import java.util.List;

import com.google.common.collect.Lists;
import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * An Objective C statement block
 * 
 * @author Inderjeet Singh
 */
public final class ObjcStatementBlock extends ObjcStatement {
  
  public static class Builder {
    private final List<ObjcStatement> stmts = Lists.newArrayList();
    public Builder addStatement(ObjcStatement stmt) {
      stmts.add(stmt);
      return this;
    }
    public ObjcStatementBlock build() {
      return new ObjcStatementBlock(stmts);
    }
  }

  private final List<ObjcStatement> stmts;

  public ObjcStatementBlock(CompilationContext context, BlockStmt block) {
    stmts = Lists.newArrayList();
    context.startBlock();
    if (block != null && block.getStmts() != null) {
      for (Statement stmt : block.getStmts()) {
        stmts.add(context.getStatementConverter().to(stmt));
      }    
    }
    context.endBlock();
  }

  public ObjcStatementBlock(List<ObjcStatement> stmts) {
    this.stmts = stmts;
  }

  public List<ObjcStatement> getStatements() {
    return stmts;
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.append("{").newLine();
    writer.indent();
    for (ObjcStatement stmt : stmts) {
      writer.append(stmt);
    }
    writer.unIndent();
    writer.append("}").newLine();
  }
}
