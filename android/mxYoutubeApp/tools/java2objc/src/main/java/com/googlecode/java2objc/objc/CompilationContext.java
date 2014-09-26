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
package com.googlecode.java2objc.objc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import com.google.code.java2objc.code.ObjcMethod;
import com.google.code.java2objc.code.ObjcType;
import com.google.code.java2objc.converters.ExpressionConverter;
import com.google.code.java2objc.converters.MethodConverter;
import com.google.code.java2objc.converters.StatementConverter;
import com.google.code.java2objc.converters.TypeConverter;
import com.google.common.collect.Maps;
import com.googlecode.java2objc.main.Config;

/**
 * class to keep track of current method and type being navigated in the source-code
 * 
 * @author Inderjeet Singh
 */
public class CompilationContext {

  private Config config;
  private ObjcTypeRepository repo;
  private TypeConverter typeConverter;
  private final MethodConverter methodConverter;
  private final StatementConverter statementConverter;
  private final ExpressionConverter expressionConverter;
  private ObjcMethod currentMethod;
  private Stack<ObjcType> currentType;
  private Stack<HashMap<String, ObjcType>> locals;

  public CompilationContext(Config config) {
    this.config = config;
    this.repo = null;
    this.methodConverter = new MethodConverter(this);
    this.statementConverter = new StatementConverter(this);
    this.expressionConverter = new ExpressionConverter(this);
    this.currentType = new Stack<ObjcType>();
    this.locals = new Stack<HashMap<String, ObjcType>>();
    this.locals.push(Maps.<String, ObjcType>newHashMap());
  }

  public void initRepo(ObjcTypeRepository repo) {
    this.repo = repo;
  }

  public void init(Set<ObjcType> imports) {
    this.typeConverter = new TypeConverter(this, imports);
  }

  /**
   * @return the typeConverter
   */
  public TypeConverter getTypeConverter() {
    return typeConverter;
  }

  public MethodConverter getMethodConverter() {
    return methodConverter;
  }

  /**
   * @return the statementConverter
   */
  public StatementConverter getStatementConverter() {
    return statementConverter;
  }

  /**
   * @return the expressionConverter
   */
  public ExpressionConverter getExpressionConverter() {
    return expressionConverter;
  }

  /**
   * @param method
   */
  public void setCurentMethod(ObjcMethod method) {
    this.currentMethod = method;
    if (method == null) {
      locals.pop();
    } else {
      locals.push(Maps.<String, ObjcType>newHashMap());
    }
  }

  public ObjcMethod getCurrentMethod() {
    return currentMethod;
  }

  public void startBlock() {
    locals.push(Maps.<String, ObjcType>newHashMap());
  }

  public void endBlock() {
    locals.pop();
  }

  public void registerLocal(ObjcVariable var) {
    for (ObjcVariableDeclarator decl : var.getVars()) {
      locals.peek().put(decl.getName(), var.getType());
    }
  }

  public void registerLocal(ObjcMethodParam param) {
    locals.peek().put(param.getName(), param.getType());
  }

  public boolean isLocalDeclared(String name) {
    return typeOf(name) != null;
  }

  public ObjcType typeOf(String name) {
    if ("self".equals(name))
      return getCurrentType();

    for (int i = locals.size() - 1; i >= 0; i--) {
      Map<String, ObjcType> block = locals.get(i);
      if (block.containsKey(name))
        return block.get(name);
    }

    return null;
  }

  public void setCurrentType(ObjcType type) {
    if (type == null) {
      this.currentType.pop();
      this.locals.pop();
    } else {
      this.currentType.push(type);
      this.locals.push(Maps.<String, ObjcType>newHashMap());
    }
  }

  public ObjcType getCurrentType() {
    return currentType.peek();
  }

  public ObjcTypeRepository getTypeRepo() {
    return repo;
  }

  public String prefix(String name) {
    return config.getPrefix() != null ? config.getPrefix() + name : name;
  }
}
