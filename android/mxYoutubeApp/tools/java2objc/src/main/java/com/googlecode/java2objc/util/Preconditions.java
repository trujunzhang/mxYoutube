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
package com.googlecode.java2objc.util;

/**
 * Utility class to assert preconditions for methods
 * 
 * @author Inderjeet Singh
 */
public final class Preconditions {
  public static void assertNotNull(Object target) {
    assertTrue(target != null);
  }

  public static void assertNull(Object target) {
    assertTrue(target == null);
  }
  
  public static void assertTrue(boolean condition) {
    if (!condition) {
      throw new IllegalArgumentException();
    }
  }
  
  public static void assertFalse(boolean condition) {
    assertTrue(!condition);
  }
  
  public static void assertTrue(boolean condition, String msg) {
    if (!condition) {
      throw new IllegalArgumentException(msg);
    }
  }

  public static void assertEquals(String expected, String actual) {
    assertTrue(expected.equals(actual));
  }
  
  public static void assertEquals(int expected, int actual) {
    assertTrue(expected == actual);
  }
  
  public static void fail(String msg) {
    assertTrue(false, msg);
  }
}
