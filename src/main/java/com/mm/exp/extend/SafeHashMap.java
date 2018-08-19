package com.mm.exp.extend;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked", "WeakerAccess"})
public class SafeHashMap<K, V> extends HashMap<K, V> implements SafeMap<K, V> {
	private final Class<K> keyClass;
	private final Class<V> valueClass;

	@Override
	public V get(Object key) { return super.get(keyClass.cast(key)); }

	@Override
	public boolean containsKey(Object key) {
    return super.containsKey(keyClass.cast(key));
	}

	@Override
	public V remove(Object key) { return super.remove(keyClass.cast(key)); }

	@Override
	public boolean containsValue(Object value) { return super.containsValue(valueClass.cast(value)); }

	@Override
	public V getOrDefault(Object key, V defaultValue) { return super.getOrDefault(keyClass.cast(key), defaultValue); }

  @SuppressWarnings("unused")
  public SafeHashMap(final int initialCapacity, final float loadFactor, final Class<K> keyClass, final Class<V> valueClass) {
		super(initialCapacity, loadFactor);
		this.keyClass = keyClass;
		this.valueClass = valueClass;
	}

  @SuppressWarnings("unused")
  public SafeHashMap(final int initialCapacity, final Class<K> keyClass, final Class<V> valueClass) {
		super(initialCapacity);
		this.keyClass = keyClass;
		this.valueClass = valueClass;
	}

	public SafeHashMap(final Class<K> keyClass, final Class<V> valueClass) {
	  super();
		this.keyClass = keyClass;
		this.valueClass = valueClass;
	}

	@SuppressWarnings("unused")
  public SafeHashMap(final Map<? extends K, ? extends V> m, final Class<K> keyClass, final Class<V> valueClass) {
		super();
		this.keyClass = keyClass;
		this.valueClass = valueClass;
		putAll(m);
	}

  @Override
  public SafeHashMap<K, V> clone() {
    throw new AssertionError("SafeHashMap is not Cloneable");
  }
}
