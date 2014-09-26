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

import japa.parser.ast.body.VariableDeclarator;
import japa.parser.ast.type.Type;

import java.util.List;

import com.google.code.java2objc.code.ObjcType;
import com.google.code.java2objc.converters.ExpressionConverter;
import com.google.common.collect.Lists;

/**
 * Variable or field
 * 
 * @author Inderjeet Singh, David Gileadi
 */
public class ObjcVariable extends ObjcNode {

  private final ObjcType type;
  private final List<ObjcVariableDeclarator> vars;

  public ObjcVariable(CompilationContext context, List<VariableDeclarator> vars, Type type) {
    this.type = context.getTypeRepo().getOrCreate(type);
    this.vars = Lists.newArrayList();
    ExpressionConverter converter = context.getExpressionConverter();
    for (VariableDeclarator var : vars) {
      ObjcVariableDeclarator decl =
          new ObjcVariableDeclarator(var.getId().getName(), var.getId().getArrayCount(),
              converter.to(var.getInit()));
      this.vars.add(decl);
    }
    context.registerLocal(this);
  }

  public ObjcType getType() {
    return type;
  }

  public List<ObjcVariableDeclarator> getVars() {
    return vars;
  }

  @Override
  public void append(SourceCodeWriter writer) {
    appendExpression(writer);
    writer.endStatement();
  }

  public void appendExpression(SourceCodeWriter writer) {
    writer.append(type.getPointerTypeName()).append(" ");
    boolean first = true;
    for (ObjcVariableDeclarator var : vars) {
      if (!first) {
        writer.append(", ");
      } else {
        first = false;
      }
      var.append(writer);
    }
  }
}
