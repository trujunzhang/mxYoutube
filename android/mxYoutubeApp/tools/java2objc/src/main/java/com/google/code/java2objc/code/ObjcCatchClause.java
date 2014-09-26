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

import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.ObjcMethodParam;
import com.googlecode.java2objc.objc.ObjcNode;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * Objective C catch clause
 * 
 * @author David Gileadi
 */
public class ObjcCatchClause extends ObjcNode {

  private final ObjcMethodParam except;
  private final ObjcStatementBlock catchBlock;
  
  public ObjcCatchClause(CompilationContext context, CatchClause clause) {
    ObjcType type = context.getTypeRepo().getOrCreate(clause.getExcept().getType());
    String name = clause.getExcept().getId().getName();
    this.except = new ObjcMethodParam(type, name, 0);
    this.catchBlock = new ObjcStatementBlock(context, clause.getCatchBlock());
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.append("@catch (").append(except.getType().getPointerTypeName()).append(' ')
        .append(except.getName()).append(") ").append(catchBlock);
  }
}
