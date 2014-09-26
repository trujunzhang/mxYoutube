package com.googlecode.java2objc.util;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

public class OrderedProperties extends Properties {

  private static final long serialVersionUID = 8614070113366013286L;

  private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();

  public Enumeration<Object> keys() {
    return Collections.<Object> enumeration(keys);
  }

  @Override
  public Set<Object> keySet() {
    return keys;
  }

  public Object put(Object key, Object value) {
    keys.add(key);
    return super.put(key, value);
  }
}
