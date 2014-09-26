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
package com.google.code.java2objc.converters;

import japa.parser.ast.expr.ArrayAccessExpr;
import japa.parser.ast.expr.ArrayCreationExpr;
import japa.parser.ast.expr.ArrayInitializerExpr;
import japa.parser.ast.expr.AssignExpr;
import japa.parser.ast.expr.BinaryExpr;
import japa.parser.ast.expr.BooleanLiteralExpr;
import japa.parser.ast.expr.CastExpr;
import japa.parser.ast.expr.CharLiteralExpr;
import japa.parser.ast.expr.ClassExpr;
import japa.parser.ast.expr.ConditionalExpr;
import japa.parser.ast.expr.DoubleLiteralExpr;
import japa.parser.ast.expr.EnclosedExpr;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.FieldAccessExpr;
import japa.parser.ast.expr.InstanceOfExpr;
import japa.parser.ast.expr.IntegerLiteralExpr;
import japa.parser.ast.expr.LongLiteralExpr;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.expr.NullLiteralExpr;
import japa.parser.ast.expr.ObjectCreationExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.expr.ThisExpr;
import japa.parser.ast.expr.UnaryExpr;
import japa.parser.ast.expr.VariableDeclarationExpr;

import java.util.List;

import com.google.code.java2objc.code.ObjcExpression;
import com.google.code.java2objc.code.ObjcExpressionArrayAccess;
import com.google.code.java2objc.code.ObjcExpressionArrayInit;
import com.google.code.java2objc.code.ObjcExpressionAssign;
import com.google.code.java2objc.code.ObjcExpressionBinary;
import com.google.code.java2objc.code.ObjcExpressionBooleanLiteral;
import com.google.code.java2objc.code.ObjcExpressionCast;
import com.google.code.java2objc.code.ObjcExpressionClass;
import com.google.code.java2objc.code.ObjcExpressionConditional;
import com.google.code.java2objc.code.ObjcExpressionEnclosed;
import com.google.code.java2objc.code.ObjcExpressionFieldAccess;
import com.google.code.java2objc.code.ObjcExpressionIsKindOf;
import com.google.code.java2objc.code.ObjcExpressionMethodCall;
import com.google.code.java2objc.code.ObjcExpressionObjectCreation;
import com.google.code.java2objc.code.ObjcExpressionSimple;
import com.google.code.java2objc.code.ObjcExpressionStringLiteral;
import com.google.code.java2objc.code.ObjcExpressionUnary;
import com.google.code.java2objc.code.ObjcExpressionVariableDeclaration;
import com.google.common.collect.Lists;
import com.googlecode.java2objc.objc.CompilationContext;

/**
 * Converts Java expressions into Objective C expressions
 * 
 * @author Inderjeet Singh
 */
public final class ExpressionConverter {

  private final CompilationContext context;

  /**
   * @param compilationContext
   */
  public ExpressionConverter(CompilationContext context) {
    this.context = context;
  }

  public List<ObjcExpression> to(List<Expression> expressions) {
    List<ObjcExpression> objcExpressions = Lists.newArrayList();
    if (expressions != null) {
      for (Expression expr : expressions) {
        ObjcExpression oexpr = to(expr);
        if (oexpr != null)
          objcExpressions.add(oexpr);
      }
    }
    return objcExpressions;
  }

  public ObjcExpression to(Expression expr) {
    if (expr instanceof CharLiteralExpr || expr instanceof DoubleLiteralExpr
        || expr instanceof IntegerLiteralExpr || expr instanceof LongLiteralExpr) {
      return new ObjcExpressionSimple(context, expr.toString());
    } else if (expr instanceof StringLiteralExpr) {
      return new ObjcExpressionStringLiteral(context, (StringLiteralExpr) expr);
    } else if (expr instanceof BooleanLiteralExpr) {
      return new ObjcExpressionBooleanLiteral(context, expr);
    } else if (expr instanceof NullLiteralExpr) {
      return new ObjcExpressionSimple(context, "nil");
    } else if (expr instanceof MethodCallExpr) {
      return new ObjcExpressionMethodCall(context, (MethodCallExpr)expr);
    } else if (expr instanceof BinaryExpr) {
      return new ObjcExpressionBinary(context, (BinaryExpr) expr);
    } else if (expr instanceof UnaryExpr) {
      return new ObjcExpressionUnary(context, (UnaryExpr) expr);
    } else if (expr instanceof ConditionalExpr) {
      return new ObjcExpressionConditional(context, (ConditionalExpr) expr);
    } else if (expr instanceof AssignExpr) {
      return new ObjcExpressionAssign(context, (AssignExpr) expr);
    } else if (expr instanceof VariableDeclarationExpr) {
      return new ObjcExpressionVariableDeclaration(context, (VariableDeclarationExpr) expr);
    } else if (expr instanceof ObjectCreationExpr) {
      return new ObjcExpressionObjectCreation(context, (ObjectCreationExpr) expr);
    } else if (expr instanceof ArrayCreationExpr) {
      return new ObjcExpressionArrayInit(context, ((ArrayCreationExpr) expr).getInitializer());
    } else if (expr instanceof ArrayInitializerExpr) {
      return new ObjcExpressionArrayInit(context, (ArrayInitializerExpr) expr);
    } else if (expr instanceof FieldAccessExpr) {
      return new ObjcExpressionFieldAccess(context, (FieldAccessExpr) expr);
    } else if (expr instanceof ArrayAccessExpr) {
      return new ObjcExpressionArrayAccess(context, (ArrayAccessExpr) expr);
    } else if (expr instanceof EnclosedExpr) {
      return new ObjcExpressionEnclosed(context, (EnclosedExpr) expr);
    } else if (expr instanceof CastExpr) {
      return new ObjcExpressionCast(context, (CastExpr) expr);
    } else if (expr instanceof InstanceOfExpr) {
      return new ObjcExpressionIsKindOf(context, (InstanceOfExpr) expr);
    } else if (expr instanceof ClassExpr) {
      return new ObjcExpressionClass(context, (ClassExpr) expr);
    } else if (expr instanceof ThisExpr) {
      return new ObjcExpressionSimple(context, "self");
    } else if (expr instanceof NameExpr) {
      String name = expr.toString();
      name = context.isLocalDeclared(name) ? name : context.prefix(name);
      return new ObjcExpressionSimple(context, name);
    } else if (expr != null) {
      return new ObjcExpressionSimple(context, expr.toString());
    } else {
      return null;
    }
  }
}
