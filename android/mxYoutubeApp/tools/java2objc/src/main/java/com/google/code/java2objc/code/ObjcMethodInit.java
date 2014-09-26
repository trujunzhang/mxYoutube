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

import java.util.List;

import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.stmt.ExplicitConstructorInvocationStmt;
import japa.parser.ast.stmt.Statement;

import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.ObjcMethodParam;

/**
 * Method body for creating an init method
 * 
 * @author Inderjeet Singh
 */
public final class ObjcMethodInit extends ObjcMethod {

  private final boolean baseInit;

  public ObjcMethodInit(CompilationContext context, ConstructorDeclaration n) {
    super(context, getMethodName(context, n), context.getTypeRepo().getId(), ObjcMethod.convertParameters(context,
        n.getParameters()), n.getModifiers(), getConstructorBody(context, n),
        n.getJavaDoc() != null ? n.getJavaDoc().getContent() : null);
    ExplicitConstructorInvocationStmt init = getInit(n);
    baseInit = init == null || !init.isThis();
  }

  public ObjcMethodInit(CompilationContext context, String name, ObjcType returnType,
      List<ObjcMethodParam> params, int modifiers, boolean baseInit, ObjcStatementBlock methodBody,
      String comments) {
    super(context, name, returnType, params, modifiers, methodBody, comments);
    this.baseInit = baseInit;
  }

  private static String getMethodName(CompilationContext context, ConstructorDeclaration n) {
    if (n.getParameters() == null || n.getParameters().size() != 1)
      return "init";
    // if a single param, use "initWith" as the name to differentiate from no-param init
    String name = n.getParameters().get(0).getId().getName();
    return "initWith" + name.substring(0, 1).toUpperCase() + name.substring(1);
  }

  private static ObjcStatementBlock getConstructorBody(CompilationContext context, 
      ConstructorDeclaration n) {

    ObjcExpression condition;
    ExplicitConstructorInvocationStmt init = getInit(n);
    if (init != null) {
      ObjcExpression scope;
      if (init.isThis()) {
        scope = new ObjcExpressionSimple(context, "self");
      } else {
        scope = new ObjcExpressionSimple(context, "super");
      }
      ObjcExpressionMethodCall call = new ObjcExpressionMethodCall(context, scope, "init", init.getArgs());
      condition = new ObjcExpressionAssign(new ObjcExpressionSimple(context, "self"), call);
    } else {
      condition = new ObjcExpressionSimple(context, "self = [super init]");
    }
    condition = new ObjcExpressionEnclosed(context, condition);

    ObjcStatement thenStmt = new ObjcStatementBlock(context, n.getBlock());
    ObjcStatementIf ifStmt = new ObjcStatementIf(condition, thenStmt, null);     
    return new ObjcStatementBlock.Builder()
      .addStatement(ifStmt)
      .addStatement(new ObjcStatementSimple("return self;"))
      .build();
  }

  private static ExplicitConstructorInvocationStmt getInit(ConstructorDeclaration n) {
    if (n.getBlock().getStmts() != null && n.getBlock().getStmts().size() > 0) {
      Statement statement = n.getBlock().getStmts().get(0);
      if (statement instanceof ExplicitConstructorInvocationStmt) {
        return (ExplicitConstructorInvocationStmt)statement;
      }
    }
    return null;
  }

  public boolean isBaseInit() {
    return baseInit;
  }
}
