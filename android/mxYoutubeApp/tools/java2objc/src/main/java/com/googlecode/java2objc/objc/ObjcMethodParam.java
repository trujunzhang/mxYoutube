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

import com.google.code.java2objc.code.ObjcType;

/**
 * Parameter for an Objective C method
 * 
 * @author Inderjeet Singh
 */
public final class ObjcMethodParam extends ObjcNode {

  private final ObjcType type;
  private final String name;
  private final int arrayCount;

  public ObjcMethodParam(ObjcType type, String name, int arrayCount) {
    this.type = type;
    this.name = name;
    this.arrayCount = arrayCount;
  }

  public ObjcType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public int getArrayCount() {
    return arrayCount;
  }
}
