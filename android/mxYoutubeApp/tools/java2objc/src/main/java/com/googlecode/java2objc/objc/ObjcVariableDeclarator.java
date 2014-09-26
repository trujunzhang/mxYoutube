package com.googlecode.java2objc.objc;

import com.google.code.java2objc.code.ObjcExpression;

public class ObjcVariableDeclarator extends ObjcNode {

  private final String name;
  private final int arrayCount;
  private final ObjcExpression init;

  protected ObjcVariableDeclarator(String name, int arrayCount, ObjcExpression init) {
    this.name = name;
    this.arrayCount = arrayCount;
    this.init = init;
  }

  public String getName() {
    return name;
  }

  public int getArrayCount() {
    return arrayCount;
  }

  public ObjcExpression getInit() {
    return init;
  }

  @Override
  public void append(SourceCodeWriter writer) {
    appendDeclaration(writer);
    if (this.init != null) {
      writer.append(" = ").append(this.init);
    }
  }

  public void appendDeclaration(SourceCodeWriter writer) {
    writer.append(name);
    for (int i = 0; i < arrayCount; i++) {
      writer.append("[]");
    }
  }
}