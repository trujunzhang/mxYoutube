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

import japa.parser.ast.body.ModifierSet;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;

import java.util.List;

import com.google.code.java2objc.converters.ExpressionConverter;
import com.google.common.collect.Lists;
import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.ObjcUtils;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * An Objective C expression involving a method call
 * 
 * @author Inderjeet Singh
 */
public class ObjcExpressionMethodCall extends ObjcExpression {

  protected final ObjcExpression target;
  protected final ObjcType enclosingType;
  protected final String methodName;
  protected final List<ObjcExpression> args;
  protected final int numParams;
  protected final boolean getter;
  protected final boolean setter;
  
  public ObjcExpressionMethodCall(CompilationContext context, MethodCallExpr expr) {
    this(context, getScope(context, expr), ObjcMethod.getMethodName(context, expr.getName(),
        getScope(context, expr)), expr.getArgs());
  }

  protected ObjcExpressionMethodCall(CompilationContext context, ObjcExpression scope, 
      String methodName, List<Expression> args) {
    super(getReturnType(scope, methodName));
    this.target = scope;
    if (scope instanceof ObjcExpressionSimple
        && ("self".equals(((ObjcExpressionSimple)scope).getExpression()) || context
            .getCurrentType().getName().equals(((ObjcExpressionSimple)scope).getExpression()))) {
      this.enclosingType = context.getCurrentType();
    } else {
      this.enclosingType = null;
    }
    this.args = Lists.newArrayList();
    if (args != null) {
      ExpressionConverter converter = context.getExpressionConverter();
      for (Expression arg : args) {
        this.args.add(converter.to(arg));
      }
    }
    numParams = this.args.size();
    this.getter = ObjcUtils.isGetter(methodName, ModifierSet.PUBLIC, null, numParams);
    this.setter = !this.getter && ObjcUtils.isSetter(methodName, ModifierSet.PUBLIC, null, numParams);
    if (this.getter) {
      this.methodName = ObjcUtils.propertyNameFor(methodName);
    } else {
      this.methodName = methodName;
    }
  }

  private static ObjcExpression getScope(CompilationContext context, MethodCallExpr expr) {
    Expression scope = expr.getScope();
    String methodName = expr.getName();
    if (scope == null) {
      ObjcType enclosingType = context.getCurrentType();
      ObjcMethod invokedMethod = enclosingType.getMethodWithName(methodName);
      if (invokedMethod != null && invokedMethod.isStatic()) {
        return new ObjcExpressionSimple(context, enclosingType.getName());
      } else {
        return new ObjcExpressionSimple(context, "self");
      }
    } else {
      return context.getExpressionConverter().to(scope);
    }
  }

  private static ObjcType getReturnType(ObjcExpression scope, String methodName) {
    if (scope.getType() != null) {
      ObjcMethod method = scope.getType().getMethodWithName(methodName);
      if (method != null)
        return method.getReturnType();
    }
    return null;
  }

  public String getMethodName() {
    return methodName;
  }

  public int getNumParams() {
    return numParams;
  }

  public boolean isGetter() {
    return getter;
  }

  public boolean isSetter() {
    return setter;
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.append("[").append(target).append(" ");
    writer.append(methodName);
    if (numParams > 0) {
      // Write first argument
      writer.append(":").append(args.get(0));
    }
    // Write remaining params
    for (int i = 1; i < numParams; ++i) {
      writer.append(' ').append(getParamName(i)).append(':').append(args.get(i));
    }
    writer.append("]");
  }

  private String getParamName(int index) {
    if (enclosingType != null) {
      ObjcMethod method = enclosingType.getMethodWithName(methodName, args.size());
      if (method != null) {
        return method.getParams().get(index).getName();
      }
    }
    return String.format("param%d", index);
  }
}
