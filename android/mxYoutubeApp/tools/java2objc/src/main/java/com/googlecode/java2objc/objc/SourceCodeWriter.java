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
package com.googlecode.java2objc.objc;

import java.io.PrintWriter;
import java.util.List;

/**
 * A specialized class to write Objective C source-code. It provides convenient methods to indent
 * statements, terminate lines, or write comments.
 * 
 * @author Inderjeet Singh
 */
public final class SourceCodeWriter {

  private final PrintWriter writer;
  private boolean writingHeaderFile;  
  private int indentLevel;
  private boolean newLine;
  private final String indent;
  
  public SourceCodeWriter(PrintWriter writer, boolean writingHeaderFile, String indent) {
    this.writer = writer;
    this.writingHeaderFile = writingHeaderFile;
    this.indentLevel = 0;
    this.newLine = true;
    this.indent = indent;
  }

  public boolean isWritingHeaderFile() {
    return writingHeaderFile;
  }

  public void setWritingHeaderFile(boolean writingHeaderFile) {
    this.writingHeaderFile = writingHeaderFile;
  }

  public SourceCodeWriter indent() {
    ++indentLevel;
    return this;
  }
  
  public SourceCodeWriter unIndent() {
    --indentLevel;
    indentLevel = Math.max(indentLevel, 0);
    return this;
  }

  public SourceCodeWriter append(String str) {
    applyIndent();
    writer.append(str);
    return this;
  }

  public SourceCodeWriter append(char ch) {
    applyIndent();
    writer.append(ch);
    return this;
  }
  
  public <T extends ObjcNode> SourceCodeWriter append(T node) {
    node.append(this);
    return this;
  }

  private SourceCodeWriter applyIndent() {
    if (newLine) {
      for (int i = 0; i < indentLevel; ++i) {
        writer.append(indent);
      }
      newLine = false;
    }
    return this;
  }
    
  public SourceCodeWriter endStatement() {
    return append(";").newLine();
  }

  public SourceCodeWriter newLine() {
    newLine = true;
    writer.append('\n');
    return this;
  }

  public <T extends ObjcNode> SourceCodeWriter appendLine(T node) {
    applyIndent();
    node.append(this);
    newLine();
    return this;
  }

  public SourceCodeWriter appendBlankLine() {
    return newLine();
  }

  public SourceCodeWriter appendToDo(String comment) {
    return appendComment("TODO: " + comment);
  }

  public SourceCodeWriter appendComment(String comment) {
    String[] lines = comment.trim().split("\n");
    for (String line : lines) {
      applyIndent();
      writer.append("// ");
      writer.append(line);
      newLine();
    }
    return this;
  }

  public SourceCodeWriter appendDocComment(String comment) {
    newLine();
    applyIndent();
    writer.append("/**");
    newLine();
    String[] lines = comment.trim().split("\n");
    for (String line : lines) {
      applyIndent();
      line = line.replaceFirst("^\\s*\\*\\s*", "");
      writer.append(" * ");
      writer.append(line);
      newLine();
    }
    writer.append(" */");
    newLine();
    return this;
  }

  public void close() {
    writer.close();
  }

  /**
   * Appends a list of nodes each separated by the specified separator.
   * 
   * @param <T>
   * @param nodes
   * @param separator
   */
  public <T extends ObjcNode> SourceCodeWriter append(List<T> nodes, String separator) {
    boolean first = true;
    for (T node : nodes) {
      if (first) {
        first = false;
      } else {
        writer.append(separator);
      }
      append(node);
    }
    return this;
  }
}
