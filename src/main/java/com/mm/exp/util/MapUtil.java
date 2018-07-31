/*
 * Copyright © [2018] Common Securitization Solutions, LLC. All rights reserved.
 * This software contains confidential information and trade secrets of Common Securitization
 * Solutions, LLC. Use, disclosure, or reproduction is prohibited without the prior written
 * consent of Common Securitization Solutions, LLC.
 */

package com.mm.exp.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("UtilityClassCanBeEnum")
public final class MapUtil {
	private MapUtil() { }

	static <K, V> SafeMap<K, V> wrap(Map<K, V> map) {
		return new WrappedMap<>(map);
	}
	
	static <K, V> SafeMap<K, V> wrap(Map<K,V> map, Class<K> keyClass, Class<V> valueClass) {
		return new SafeWrappedMap<>(map, keyClass, valueClass);
	}

	private static final class WrappedMap<K, V> implements SafeMap<K, V> {
		private final Map<K, V> map;
		private WrappedMap(Map<K, V> theMap) {
			map = theMap;
		}
		
		@Override
		public int size() {
			return map.size();
		}

		@Override
		public boolean isEmpty() {
			return map.isEmpty();
		}

		/**
		 * Deprecated due to lack of type safety. Use {@link #safeHasKey(K)} instead.
		 * <p/>
		 * {@inheritDoc}
		 * <p/>
		 * This implementation is written to fail Fast. If you call it with a key that is not of type K, it will throw a 
		 * ClassCastException instead of returning null, which is what a Map implementation would do.
		 * <p/>
		 * @deprecated Use #safeHasKey() instead
		 * 
		 * @see #safeHasKey(Object) 
		 */
		@Deprecated
		@Override
		public boolean containsKey(Object key) {
			//noinspection unchecked
			K k = (K) key;
			return map.containsKey(k);
		}

		@Override
		public boolean containsValue(Object value) {
			//noinspection unchecked
			return map.containsValue((V)value);
		}

		@Override
		public V get(Object key) {
			//noinspection unchecked
			return find((K)key);
		}

		@Override
		public V put(K key, V value) {
			return map.put(key, value);
		}

		@Override
		public V remove(Object key) {
			//noinspection unchecked
			return safeRemove((K)key);
		}

		@Override
		public void putAll(Map<? extends K, ? extends V> m) {
			map.putAll(m);
		}

		@Override
		public void clear() {
			map.clear();
		}

		@Override
		public Set<K> keySet() {
			return map.keySet();
		}

		@Override
		public Collection<V> values() {
			return map.values();
		}

		@Override
		public Set<Entry<K, V>> entrySet() {
			return map.entrySet();
		}

		@Override
		public V getOrDefault(Object key, V defaultValue) {
			//noinspection unchecked
			return map.getOrDefault((K) key, defaultValue);
		}
	}

	private static final class SafeWrappedMap<K, V> implements SafeMap<K, V> {
		private final Map<K, V> map;
		private final Class<K> keyClass;
		private final Class<V> valueClass;

		private SafeWrappedMap(Map<K, V> theMap, Class<K> kClass, Class<V> vClass) {
			map = theMap;
			keyClass = kClass;
			valueClass = vClass;
		}

		@Override
		public int size() {
			return map.size();
		}

		@Override
		public boolean isEmpty() {
			return map.isEmpty();
		}

		/**
		 * Deprecated due to lack of type safety. Use {@link #safeHasKey(K)} instead.
		 * <p/>
		 * {@inheritDoc}
		 * <p/>
		 * This implementation is written to fail Fast. If you call it with a key that is not of type K, it will throw a 
		 * ClassCastException instead of returning null, which is what a Map implementation would do.
		 * <p/>
		 * @deprecated Use #safeHasKey() instead
		 *
		 * @see #safeHasKey(Object)
		 */
		@Deprecated
		@Override
		public boolean containsKey(Object key) {
			//noinspection unchecked
			return map.containsKey(keyClass.cast(key));
		}

		@Override
		public boolean containsValue(Object value) {
			//noinspection unchecked
			return map.containsValue(valueClass.cast(value));
		}

		@Override
		public V get(Object key) {
			//noinspection unchecked
			return find(keyClass.cast(key));
		}

		@Override
		public V put(K key, V value) {
			return map.put(key, value);
		}

		@Override
		public V remove(Object key) {
			//noinspection unchecked
			return safeRemove(keyClass.cast(key));
		}

		@Override
		public void putAll(Map<? extends K, ? extends V> m) {
			map.putAll(m);
		}

		@Override
		public void clear() {
			map.clear();
		}

		@Override
		public Set<K> keySet() {
			return map.keySet();
		}

		@Override
		public Collection<V> values() {
			return map.values();
		}

		@Override
		public Set<Entry<K, V>> entrySet() {
			return map.entrySet();
		}

		@Override
		public V getOrDefault(Object key, V defaultValue) {
			//noinspection unchecked
			return map.getOrDefault(keyClass.cast(key), defaultValue);
		}
	}
}
