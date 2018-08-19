package com.mm.exp.extend;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"UtilityClassCanBeEnum", "WeakerAccess"})
public final class MapUtil {
	private MapUtil() { }

	/**
	 * Wraps an existing Map in a SafeMap. This method does NOT fail fast if the wrong method is called. It just relies
	 * on the developer's IDE to catch any use of deprecated methods.
	 * @param map The Map to wrap
	 * @param <K> The Key type
	 * @param <V> The Value Type
	 * @return A SafeMap that wraps the provided map instance.
	 */
	static <K, V> SafeMap<K, V> wrap(Map<K, V> map) {
		return new WrappedMap<>(map);
	}

	/**
	 * Wraps an existing Map in a fast-fail SafeMap. Any calls to unsafe methods will fail fast. It also relies
	 * on the developer's IDE to catch any use of deprecated methods. 
	 * @param map The Map to wrap
	 * @param keyClass The Key class instance
	 * @param valueClass The value class instance
	 * @param <K> The Key Class
	 * @param <V> The Value Class
	 * @return A fast-fail SafeMap that wraps the provided map instance.
	 */
	static <K, V> SafeMap<K, V> wrapFailFast(Map<K,V> map, Class<K> keyClass, Class<V> valueClass) {
		return new SafeWrappedMap<>(map, keyClass, valueClass);
	}

	/**
	 * Wrapped Map that does not fail fast. It relies solely on the developer's IDE to catch illegal calls.
	 * @param <K> The Key type
	 * @param <V> The Value type.
	 */
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

//		/**
//		 * Deprecated due to lack of type safety. Use {@link #safeHasKey(K)} instead.
//		 * <p>
//		 * {@inheritDoc}
//		 * <p>
//		 * This implementation is written to fail Fast. If you call it with a key that is not of type K, it will throw a 
//		 * ClassCastException instead of returning null, which is what a Map implementation would do.
//		 * <p>
//		 * @deprecated Use #safeHasKey() instead
//		 * 
//		 * @see #safeHasKey(Object) 
//		 */
////		@Deprecated
//		@Override
//		public boolean containsKey(Object key) {
//			//noinspection unchecked
//			K k = (K) key;
//			return map.containsKey(k);
//		}


		@Override
		public boolean containsKey(final Object key) {
			return map.containsKey(key);
		}

		@Override
		public boolean containsValue(Object value) {
			//noinspection unchecked
			return map.containsValue(value);
		}

		@Override
		public V get(Object key) {
			return map.get(key);
		}

		@Override
		public V put(K key, V value) {
			return map.put(key, value);
		}

		@Override
		public V remove(Object key) {
			return map.remove(key);
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
			return map.getOrDefault(key, defaultValue);
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
		 * <p>
		 * {@inheritDoc}
		 * <p>
		 * This implementation is written to fail fast. If you call it with a key that is not of type K, it will throw a 
		 * ClassCastException instead of returning null, which is what a Map implementation would do.
		 * <p>
		 * @deprecated Use #safeHasKey() instead
		 *
		 * @param key The key
		 * @return The mapped object, if the the key is of the correct type. Throws a ClassCastException otherwise
		 *
		 * @see #safeHasKey(Object)
		 */
		@Deprecated
		@Override
		public boolean containsKey(Object key) {
			//noinspection unchecked
			return map.containsKey(keyClass.cast(key));
		}

		/**
		 * {@inheritDoc}
		 * <p>
		 * This implementation is written to fail fast. If you call it with a value that is not of type V, it will throw a 
		 * ClassCastException instead of returning false, which is what a Map implementation would do.
		 * <p>
		 * @deprecated Use #safeHasValue(V) instead
		 *
		 * @param value
		 * @return
		 */
		@Deprecated
		@Override
		public boolean containsValue(Object value) {
			//noinspection unchecked
			return map.containsValue(valueClass.cast(value));
		}

		@Override
		public V get(Object key) {
			return map.get(keyClass.cast(key));
		}

		@Override
		public V put(K key, V value) {
			return map.put(key, value);
		}

		@Override
		public V remove(Object key) {
			return map.remove(keyClass.cast(key));
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
