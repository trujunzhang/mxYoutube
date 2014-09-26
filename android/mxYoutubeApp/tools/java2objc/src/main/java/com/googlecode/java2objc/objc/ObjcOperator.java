package com.googlecode.java2objc.objc;

import japa.parser.ast.expr.BinaryExpr.Operator;

public class ObjcOperator extends ObjcNode {

  private final String operator;
  
  public ObjcOperator(Operator operator) {
    String op;
    switch (operator) {
      case and:
        op = "&&";
        break;
      case binAnd:
        op = "&";
        break;
      case binOr:
        op = "|";
        break;
      case divide:
        op = "/";
        break;
      case equals:
        op = "==";
        break;
      case greater:
        op = ">";
        break;
      case greaterEquals:
        op = ">=";
        break;
      case less:
        op = "<";
        break;
      case lessEquals:
        op = "<=";
        break;
      case lShift:
        op = "<<";
        break;
      case minus:
        op = "-";
        break;
      case notEquals:
        op = "!=";
        break;
      case or:
        op = "||";
        break;
      case plus:
        op = "+";
        break;
      case remainder:
        op = "%";
        break;
      case rSignedShift:
        op = ">>";
        break;
      case rUnsignedShift:
        op = ">>>";
        break;
      case times:
        op = "*";
        break;
      case xor:
        op = "^";
        break;
      default:
        throw new UnsupportedOperationException();
    }
    this.operator = op;
  }

  public ObjcOperator(japa.parser.ast.expr.UnaryExpr.Operator operator) {
    String op;
    switch (operator) {
      case inverse:
        op = "~";
        break;
      case negative:
        op = "-";
        break;
      case not:
        op = "!";
        break;
      case posDecrement:
        op = "--";
        break;
      case posIncrement:
        op = "++";
        break;
      case positive:
        op = "+";
        break;
      case preDecrement:
        op = "--";
        break;
      case preIncrement:
        op = "++";
        break;
      default:
        throw new UnsupportedOperationException();
    }
    this.operator = op;
  }

  public ObjcOperator(japa.parser.ast.expr.AssignExpr.Operator operator) {
    String op;
    switch (operator) {
      case and:
        op = "&=";
        break;
      case assign:
        op = "=";
        break;
      case lShift:
        op = "<<=";
        break;
      case minus:
        op = "-=";
        break;
      case or:
        op = "|=";
        break;
      case plus:
        op = "+=";
        break;
      case rem:
        op = "%=";
        break;
      case rSignedShift:
        op = ">>=";
        break;
      case rUnsignedShift:
        op = ">>>=";
        break;
      case slash:
        op = "/=";
        break;
      case star:
        op = "*=";
        break;
      case xor:
        op = "^=";
        break;
      default:
        throw new UnsupportedOperationException();
    }
    this.operator = op;
  }

  public String getOperator() {
    return operator;
  }

  @Override
  public void append(SourceCodeWriter writer) {
    writer.append(operator);
  }
}
