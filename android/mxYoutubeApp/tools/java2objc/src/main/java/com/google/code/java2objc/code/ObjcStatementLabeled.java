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

import japa.parser.ast.stmt.LabeledStmt;

/**
 * Objective C labeled statement
 * 
 * @author Inderjeet Singh
 */
public final class ObjcStatementLabeled extends ObjcStatement {
  // TODO: Need to add handling of break label 
  private final String label;
  private final ObjcStatement stmt;

  public ObjcStatementLabeled(CompilationContext context, LabeledStmt stmt) {
    this.label = stmt.getLabel();
    this.stmt = context.getStatementConverter().to(stmt.getStmt());
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    writer.newLine().append(label).append(": ").append(stmt);
  }
}
