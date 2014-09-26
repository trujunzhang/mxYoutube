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

import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * Base class for all Objective C statements
 * 
 * @author Inderjeet Singh
 */
public class ObjcStatementSimple extends ObjcStatement {

  private final String stmt;

  public ObjcStatementSimple() {
    this(null);
  }

  public ObjcStatementSimple(String stmt) {
    if (stmt != null) {
      if (!stmt.endsWith(";")) {
        stmt = stmt + ";";
      }
      stmt = stmt.replace("this.", "");
    }
    this.stmt = stmt;
  }

  @Override
  public void append(SourceCodeWriter writer) {
    if (stmt == null || stmt.trim().equals("")) {
      return;
    }
    writer.append(stmt);
    writer.newLine();
  }
}
