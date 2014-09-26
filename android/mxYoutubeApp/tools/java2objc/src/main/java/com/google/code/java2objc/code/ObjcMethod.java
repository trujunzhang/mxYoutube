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

import japa.parser.ast.body.JavadocComment;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.type.Type;

import java.util.List;

import com.google.common.collect.Lists;
import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.ObjcMethodParam;
import com.googlecode.java2objc.objc.ObjcNode;
import com.googlecode.java2objc.objc.ObjcUtils;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * Base class for all Objective C static or class methods
 * 
 * @author Inderjeet Singh
 */
public class ObjcMethod extends ObjcNode {

  private final List<ObjcMethodParam> params;
  private final ObjcType returnType;
  private final String name;
  private final ObjcStatementBlock methodBody;
  private final int modifiers;
  private final boolean getter;
  private final boolean setter;
  private final boolean simple;
  private final String propertyName;
  private final String comments;

  public ObjcMethod(CompilationContext context, MethodDeclaration n) {
    this(context, n.getName(), n.getType(), n.getParameters(), n.getModifiers(), n.getBody(), n
        .getJavaDoc());
  }

  public ObjcMethod(CompilationContext context, String name, Type returnType,
      List<Parameter> parameters, int modifiers, BlockStmt body, JavadocComment comment) {
    if (context != null) {
      context.setCurentMethod(this);
    }

    this.returnType = context.getTypeRepo().getOrCreate(returnType);
    this.modifiers = modifiers;
    this.comments = comment != null ? comment.getContent() : null;
    this.params = convertParameters(context, parameters);

    name = getMethodName(context, name, new ObjcExpressionSimple(context, "self"));
    if ("copyWithZone".equals(name)) {
      this.params.add(0, new ObjcMethodParam(new ObjcType(context, "NSZone"), "zone", 0));
    }

    this.getter = ObjcUtils.isGetter(name, modifiers, this.returnType, this.params.size());
    this.setter = !this.getter && ObjcUtils.isSetter(name, modifiers, this.returnType, this.params.size());
    this.propertyName = (this.getter || this.setter) ? ObjcUtils.propertyNameFor(name) : null;

    if (this.getter) {
      this.name = this.propertyName;
    } else {
      this.name = name;
    }

    this.methodBody = new ObjcStatementBlock(context, body);
    if (this.getter) {
      this.simple = ObjcUtils.isSimpleGetter(methodBody);
    } else if (this.setter) {
      this.simple = ObjcUtils.isSimpleSetter(methodBody);
    } else {
      this.simple = false;
    }

    if (context != null) {
      context.setCurentMethod(null);
    }
  }

  public ObjcMethod(CompilationContext context, String name, ObjcType returnType,
      List<ObjcMethodParam> params, int modifiers, ObjcStatementBlock methodBody, String comments) {
    if (context != null) {
      context.setCurentMethod(this);
    }

    this.returnType = returnType;
    this.modifiers = modifiers;
    this.methodBody = methodBody;
    this.comments = comments;
    this.params = Lists.newArrayList();
    if (params != null) {
      for (ObjcMethodParam param : params) {
        this.params.add(param);
        context.registerLocal(param);
      }
    }

    if ("copyWithZone".equals(name)) {
      this.params.add(0, new ObjcMethodParam(new ObjcType(context, "NSZone"), "zone", 0));
    }

    this.getter = ObjcUtils.isGetter(name, modifiers, returnType, this.params.size());
    this.setter = !this.getter && ObjcUtils.isSetter(name, modifiers, returnType, this.params.size());
    this.propertyName = (this.getter || this.setter) ? ObjcUtils.propertyNameFor(name) : null;

    if (this.getter) {
      this.name = this.propertyName;
    } else {
      this.name = name;
    }

    if (this.getter) {
      this.simple = ObjcUtils.isSimpleGetter(methodBody);
    } else if (this.setter) {
      this.simple = ObjcUtils.isSimpleSetter(methodBody);
    } else {
      this.simple = false;
    }

    if (context != null) {
      context.setCurentMethod(null);
    }
  }

  /**
   * Returns true if this method is a static method
   */
  public boolean isStatic() {
    return ModifierSet.isStatic(modifiers);
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    if (writer.isWritingHeaderFile()) {
      appendDeclaration(writer);
    } else {
      appendDefinition(writer);
    }
  }

  private void appendDeclaration(SourceCodeWriter writer) {
    if (!ModifierSet.isPrivate(modifiers)) {
      appendMethodSignature(writer);
      writer.append(";").newLine();
    }
  }
  
  private void appendDefinition(SourceCodeWriter writer) {
    if (comments != null) {
      writer.appendDocComment(comments);
    }
    appendMethodSignature(writer);
    writer.append(" ");
    writer.append(methodBody);
    writer.newLine();
  }
  
  private void appendMethodSignature(SourceCodeWriter writer) {    
    writer.append(isStatic() ? "+" : "-");
    writer.append(" (").append(returnType.getPointerTypeName()).append(") ");
    writer.append(name);
    if (params == null || params.size() == 0) {
      return;
    }
    writer.append(":");
    boolean first = true;
    for (ObjcMethodParam param : params) {
      if (first) {
        first = false;
      } else {
        writer.append(" ").append(param.getName()).append(":");
      }
      writer.append('(').append(param.getType().getPointerTypeName());
      for (int i = 0; i < param.getArrayCount(); i++) {
        writer.append("*");
      }
      writer.append(")");
      writer.append(param.getName());
    }
  }

  public String getName() {
    return name;
  }

  public List<ObjcMethodParam> getParams() {
    return params;
  }

  public ObjcType getReturnType() {
    return returnType;
  }

  public ObjcStatementBlock getMethodBody() {
    return methodBody;
  }

  public int getModifiers() {
    return modifiers;
  }

  public boolean isGetter() {
    return getter;
  }

  public boolean isSetter() {
    return setter;
  }

  public boolean isSimple() {
    return simple;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public static String getMethodName(CompilationContext context, String name, ObjcExpression target) {

    // find the target type
    if (target != null) {
      ObjcType targetType = target.getType();
      if (targetType != null) {
        String mappedName = targetType.mapMethodName(name);
        if (!mappedName.equals(name)) {
          return mappedName;
        }
      }
    }

    // if not replaced, do replacements for NSObject
    return context.getTypeRepo().getNSObject().mapMethodName(name);
  }

  public static List<ObjcMethodParam> convertParameters(CompilationContext context,
      List<Parameter> params) {
    List<ObjcMethodParam> result = Lists.newArrayList();
    if (params != null) {
      for (Parameter param : params) {
        ObjcType type = context.getTypeRepo().getOrCreate(param.getType());
        String typeName = param.getId().getName();
        ObjcMethodParam objcParam =
            new ObjcMethodParam(type, typeName, param.getId().getArrayCount());
        result.add(objcParam);
        context.registerLocal(objcParam);
      }
    }
    return result;
  }
}
