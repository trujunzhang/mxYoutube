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

import japa.parser.ast.stmt.SwitchEntryStmt;
import japa.parser.ast.stmt.SwitchStmt;

import java.util.List;

import com.google.common.collect.Lists;
import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * An Objective C switch statement
 * 
 * @author Inderjeet Singh
 */
public final class ObjcStatementSwitch extends ObjcStatement {

  private final ObjcExpression selector;
  private final List<ObjcStatementSwitchEntry> stmts;

  public ObjcStatementSwitch(CompilationContext context, SwitchStmt stmt) {
    selector = context.getExpressionConverter().to(stmt.getSelector());
    stmts = Lists.newArrayList();
    for (SwitchEntryStmt entry : stmt.getEntries()) {
      stmts.add(new ObjcStatementSwitchEntry(context, entry));
    }
  }  
  
  @Override
  public void append(SourceCodeWriter writer) {
    writer.newLine();
    writer.append("switch (").append(selector).append(") {");
    writer.newLine();
    for (ObjcStatementSwitchEntry stmt : stmts) {
      writer.append(stmt);
    }
    writer.append("}").newLine();    
  }
}
