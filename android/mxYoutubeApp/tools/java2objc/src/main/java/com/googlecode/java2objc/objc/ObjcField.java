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


import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.ModifierSet;

/**
 * Field of an An Objective C class
 * 
 * @author Inderjeet Singh
 */
public final class ObjcField extends ObjcVariable {

  private final int modifiers;
  private final String comments;

  public ObjcField(CompilationContext context, FieldDeclaration field) {
    super(context, field.getVariables(), field.getType());
    this.modifiers = field.getModifiers();
    if (field.getJavaDoc() != null) {
      this.comments = field.getJavaDoc().getContent();
    } else {
      this.comments = null;
    }
  }

  public int getModifiers() {
    return modifiers;
  }

  public boolean hasName(String fieldName) {
    for (ObjcVariableDeclarator var : getVars()) {
      if (var.getName().equals(fieldName))
        return true;
    }
    return false;
  }

  @Override
  public void append(SourceCodeWriter writer) {
    if (comments != null) {
      writer.appendDocComment(comments);
    }
    if (writer.isWritingHeaderFile() && ModifierSet.isStatic(modifiers)
        && ModifierSet.isPublic(modifiers)) {
      writer.append("extern ");
    }
    writer.append(getType().getPointerTypeName()).append(" ");
    if (ModifierSet.isStatic(modifiers) && ModifierSet.isFinal(modifiers)) {
      writer.append("const ");
    }
    boolean first = true;
    for (ObjcVariableDeclarator var : getVars()) {
      if (!first) {
        writer.append(", ");
      } else {
        first = false;
      }
      var.appendDeclaration(writer);
      if (!writer.isWritingHeaderFile() && ModifierSet.isStatic(modifiers) && var.getInit() != null) {
        writer.append(" = ").append(var.getInit());
      }
      writer.endStatement();
    }
  }
}
