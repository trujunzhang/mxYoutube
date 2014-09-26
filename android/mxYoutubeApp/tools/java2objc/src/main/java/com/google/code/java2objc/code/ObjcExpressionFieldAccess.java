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
import japa.parser.ast.expr.FieldAccessExpr;

import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.ObjcField;
import com.googlecode.java2objc.objc.SourceCodeWriter;

/**
 * An Objective C expression involving a field access
 * 
 * @author David Gileadi
 */
public class ObjcExpressionFieldAccess
    extends ObjcExpression {

  protected final ObjcExpression target;
  protected final String field;

  public ObjcExpressionFieldAccess(CompilationContext context, FieldAccessExpr expr) {
    this(getScope(context, expr), expr.getField());
  }

  public ObjcExpressionFieldAccess(ObjcExpression target, String field) {
    super(getFieldType(target, field));
    this.field = field;
    this.target = target;
  }

  
  public ObjcExpression getTarget() {
    return target;
  }

  private static ObjcExpression getScope(CompilationContext context, FieldAccessExpr expr) {
    Expression scope = expr.getScope();
    String field = expr.getField();
    if (scope == null) {
      ObjcType enclosingType = context.getCurrentType();
      ObjcField accessedField = enclosingType.getFieldWithName(field);
      if (accessedField == null || ModifierSet.isStatic(accessedField.getModifiers())) {
        return new ObjcExpressionSimple(context, enclosingType.getName());
      } else {
        return new ObjcExpressionSimple(context, "self");
      }
    } else {
      return context.getExpressionConverter().to(scope);
    }
  }

  private static ObjcType getFieldType(ObjcExpression target, String fieldName) {
    if (target.getType() != null) {
      ObjcField field = target.getType().getFieldWithName(fieldName);
      if (field != null)
        return field.getType();
    }
    return null;
  }

  public String getField() {
    return field;
  }

  @Override
  public void append(SourceCodeWriter writer) {
    // reduce to bare field access if the target is self
    if (target instanceof ObjcExpressionSimple && "self".equals(((ObjcExpressionSimple) target).getExpression())) {
      writer.append(field);
    } else if (target.getType().isArray() && "length".equals(field)) {
      writer.append('[').append(target).append(" count]");
    } else {
      writer.append(target).append('.').append(field);
    }
  }
}
