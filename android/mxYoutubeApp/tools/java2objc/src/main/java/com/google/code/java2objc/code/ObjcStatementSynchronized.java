package com.google.code.java2objc.code;

import com.googlecode.java2objc.objc.CompilationContext;
import com.googlecode.java2objc.objc.SourceCodeWriter;

import japa.parser.ast.stmt.SynchronizedStmt;

/**
 * Objective C equivalent of a Java synchronized block
 * 
 * @author Inderjeet Singh
 */
public final class ObjcStatementSynchronized extends ObjcStatement {

  private ObjcExpression expr;
  private ObjcStatementBlock stmt;
  
  public ObjcStatementSynchronized(CompilationContext context, SynchronizedStmt stmt) {
    this.expr = context.getExpressionConverter().to(stmt.getExpr());
    this.stmt = new ObjcStatementBlock(context, stmt.getBlock());
  }
  
  @Override
  public void append(SourceCodeWriter writer) {
    writer.newLine().append("@synchronized(");
    if (expr != null) {
      writer.append(expr);
    } else {
      writer.append("self");
    }
    writer.append(") ");
    writer.newLine().append(stmt);
  }
}
