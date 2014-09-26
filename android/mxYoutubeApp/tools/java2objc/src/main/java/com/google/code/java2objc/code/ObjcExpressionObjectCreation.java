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

import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.type.ClassOrInterfaceType;

import java.util.List;

import com.google.common.collect.Lists;
import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * An object creation expression in Objective C
 * 
 * @author David Gileadi
 */
public class ObjcExpressionObjectCreation
    extends ObjcExpressionMethodCall {

  public ObjcExpressionObjectCreation(CompilationContext context, ObjectCreationExpr expr) {
    super(context, getAllocExpression(context, expr), "init", expr.getArgs());
  }

  private static ObjcExpressionSimple getAllocExpression(CompilationContext context,
      ObjectCreationExpr expr) {
    String objectName = getObjectName(context, expr);
    ObjcMethod method = context.getCurrentMethod();
    if (method != null && method.getName().equals("copyWithZone")) {
      return new ObjcExpressionSimple(context, String.format("[%s allocWithZone:zone]", objectName));
    } else {
      return new ObjcExpressionSimple(context, String.format("[%s alloc]", objectName));
    }
  }

  private static String getObjectName(CompilationContext context, ObjectCreationExpr expr) {
    String name = context.getTypeRepo().getOrCreate(expr.getType()).getName();

    // if an anonymous object, create it and modify the name
    if (expr.getAnonymousClassBody() != null) {
      List<ClassOrInterfaceType> implementsList = Lists.newArrayList();
      implementsList.add(new ClassOrInterfaceType(0, 0, 0, 0, null, name, null));

      name = context.getCurrentType().getNextAnonymousName();

      ClassOrInterfaceDeclaration declaration =
          new ClassOrInterfaceDeclaration(0, 0, 0, 0, null, ModifierSet.PRIVATE, null, false, name,
              null, null, implementsList, expr.getAnonymousClassBody());
      ObjcType subType = context.getTypeConverter().to(declaration, null, context.getCurrentType());
      context.getCurrentType().addSubType(subType);
    }
    return name;
  }

  @Override
  public void append(SourceCodeWriter writer) {// TODO djzhang
//    writer.append('[');
    super.append(writer);
//    writer.append(" autorelease]");
  }
}
