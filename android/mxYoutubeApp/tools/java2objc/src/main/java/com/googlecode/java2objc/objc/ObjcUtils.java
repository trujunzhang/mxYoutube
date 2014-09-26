package com.googlecode.java2objc.objc;

import japa.parser.ast.body.ModifierSet;

import com.google.code.java2objc.code.ObjcExpression;
import com.google.code.java2objc.code.ObjcExpressionAssign;
import com.google.code.java2objc.code.ObjcExpressionFieldAccess;
import com.google.code.java2objc.code.ObjcExpressionSimple;
import com.google.code.java2objc.code.ObjcStatement;
import com.google.code.java2objc.code.ObjcStatementBlock;
import com.google.code.java2objc.code.ObjcStatementExpression;
import com.google.code.java2objc.code.ObjcStatementReturn;
import com.google.code.java2objc.code.ObjcType;

public class ObjcUtils {

  public static boolean isGetter(String name, int modifiers, ObjcType returnType, int numParams) {
    char next = '\0';
    if (name.length() > 3 && name.startsWith("get")) {
      next = name.charAt(3);
    } else if (name.length() > 2 && name.startsWith("is")
        && (returnType == null || "BOOL".equals(returnType.getName()))) {
      next = name.charAt(2);
    }
    if (next >= 'A' && next <= 'Z') {
      return ModifierSet.isPublic(modifiers)
          && (returnType == null || !"void".equals(returnType.getName())) && numParams == 0;
    }
    return false;
  }

  public static boolean isSetter(String name, int modifiers, ObjcType returnType, int numParams) {
    if (name.length() > 3 && name.startsWith("set")) {
      char next = name.charAt(3);
      if (next >= 'A' && next <= 'Z') {
        return ModifierSet.isPublic(modifiers)
            && (returnType == null || "void".equals(returnType.getName())) && numParams == 1;
      }
    }
    return false;
  }

  public static boolean isSimpleGetter(ObjcStatementBlock body) {
    if (body.getStatements().size() == 1) {
      ObjcStatement statement = body.getStatements().get(0);
      if (statement instanceof ObjcStatementReturn) {
        ObjcExpression expression = ((ObjcStatementReturn)statement).getExpression();
        return expression instanceof ObjcExpressionFieldAccess
            || expression instanceof ObjcExpressionSimple;
      }
    }
    return false;
  }

  public static boolean isSimpleSetter(ObjcStatementBlock body) {
    if (body.getStatements().size() == 1) {
      ObjcStatement statement = body.getStatements().get(0);
      if (statement instanceof ObjcStatementExpression) {
        return ((ObjcStatementExpression)statement).getExpression() instanceof ObjcExpressionAssign;
      }
    }
    return false;
  }

  public static String propertyNameFor(String name) {
    if (name.length() > 3 && (name.startsWith("get") || name.startsWith("set"))) {
      return name.substring(3, 4).toLowerCase() + name.substring(4);
    } else if (name.length() > 2 && name.startsWith("is")) {
      return name.substring(2, 3).toLowerCase() + name.substring(3);
    }
    return null;
  }
}
