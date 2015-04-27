/*******************************************************************************
 *  Copyright (c) 2009 Thales Corporate Services S.A.S.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *      Thales Corporate Services S.A.S - initial API and implementation
 *******************************************************************************/
package com.thalesgroup.orchestra.framework.common.util;

/**
 * Key, value object.
 * @author t0076261
 */
public class Couple<K, V> implements Cloneable {
  // Key reference.
  private K _key;
  // Value reference.
  private V _value;

  /**
   * Constructor.
   * @param key_p
   * @param value_p
   */
  public Couple(K key_p, V value_p) {
    _key = key_p;
    _value = value_p;
  }

  /**
   * @see java.lang.Object#clone()
   */
  @SuppressWarnings("unchecked")
  @Override
  public Couple<K, V> clone() {
    try {
      return (Couple<K, V>) super.clone();
    } catch (CloneNotSupportedException exception_p) {
      return null;
    }
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Couple<?, ?> other = (Couple<?, ?>) obj;
    if (_key == null) {
      if (other._key != null)
        return false;
    } else if (!_key.equals(other._key))
      return false;
    if (_value == null) {
      if (other._value != null)
        return false;
    } else if (!_value.equals(other._value))
      return false;
    return true;
  }

  /**
   * Get key.
   * @return K
   */
  public K getKey() {
    return _key;
  }

  /**
   * Get value.
   * @return V
   */
  public V getValue() {
    return _value;
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_key == null) ? 0 : _key.hashCode());
    result = prime * result + ((_value == null) ? 0 : _value.hashCode());
    return result;
  }

  /**
   * Set key with given value.
   * @param key_p key to set. void
   */
  public void setKey(K key_p) {
    _key = key_p;
  }

  /**
   * Set value with given value.
   * @param value_p the value to set
   */
  public void setValue(V value_p) {
    _value = value_p;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @SuppressWarnings("nls")
  @Override
  public String toString() {
    return "Couple [_key=" + _key + ", _value=" + _value + "]";
  }
}