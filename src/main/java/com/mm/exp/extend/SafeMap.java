package com.mm.exp.extend;

import java.util.Map;

/**
 * Type-safe version of the java.extend.Map interface. Four type-unsafe methods have been replaced by
 * safe versions. Since this interface extends Map, the type-unsafe methods can't be removed, so
 * they have been deprecated and replaced with type-safe versions. Since they are marked deprecated, 
 * a good IDE will be able to alert you to replace them with the type-safe versions. Also, in some
 * implementations, the type-unsafe method will fail fast (with a ClassCastException) if an object 
 * of an invalid type is supplied, instead of returning null or false.  
 * <p>
 * Here is a table showing the unsafe methods and their typesafe equivalents
 * <pre>
 * <strong>Unsafe</strong>                        <strong>Typesafe</strong>
 * ------                        --------
 * V get(Object)                 V find(K)
 * boolean containsKey(Object)   boolean safeHasKey(K)
 * boolean containsValue(Object) boolean safeHasValue(V)
 * V remove(Object)              V safeRemove(K) 
 * V getOrDefault(Object, V)     V safeGetOrDefault(K, V)
 * </pre>
 */
@SuppressWarnings({ "deprecation", "DeprecatedIsStillUsed", "unused" })
public interface SafeMap<K, V> extends Map<K, V> {

	/**
	 * Typesafe version of {@code get(Object)}.
	 * @see Map#get(Object)
	 */
	default V find(K key) { return get(key); }

	/**
	 * Typesafe version of {@code containsKey(Object)}.
	 * @see Map#containsKey(Object)
	 */
	default boolean safeHasKey(K key) { return containsKey(key); }

	/**
	 * Typesafe version of {@code containsValue(Object)}.
	 * @see Map#containsValue(Object)
	 */
	default boolean safeHasValue(V value) { return containsValue(value); }

	/**
	 * Typesafe version of {@code remove(Object)}.
	 * @see Map#remove(Object)
	 */
	default V safeRemove(K key) { return remove(key); }

	/**
	 * Typesafe version of {@code getOrDefault(Object)}.
	 * @see Map#getOrDefault(Object, V)
	 */
	default V safeGetOrDefault(K key, V defaultValue) { return getOrDefault(key, defaultValue); }

	/**
	 * Deprecated due to lack of type safety. Use {@link #find(K)} instead.
	 * <p>
	 * {@inheritDoc}
	 * <p>
	 * Some implementations are written to fail fast. If you are using a fast-fail implementation, and you call it 
	 * with a key that is not of type K, it will throw a ClassCastException instead of returning null.
	 * If you are not using a fail-fast implementation, your IDE will will warn you if you call this method, if you
	 * declared your map to be a SafeMap.
	 * <p>
	 * @deprecated Use #find() instead
	 *
	 * @see #find(K)
	 */
	@Deprecated
	@Override
	V get(Object key);

	/**
	 * Deprecated due to lack of type safety. Use {@link #safeHasKey(K)} instead.
	 * <p>
	 * {@inheritDoc}
	 * <p>
	 * Some implementations are written to fail fast. If you are using a fast-fail implementation, and you call it 
	 * with a key that is not of type K, it will throw a ClassCastException instead of returning false.
	 * If you are not using a fail-fast implementation, your IDE will will still warn you if you call this method, if you
	 * declared your map to be a SafeMap.
	 * <p>
	 * @deprecated Use #safeHasKey() instead
	 *
	 * @see #safeHasKey(K)
	 */
	@Deprecated
	@Override
	boolean containsKey(Object key);

	/**
	 * Deprecated due to lack of type safety. Use {@link #safeHasValue(V)} instead.
	 * <p>
	 * {@inheritDoc}
	 * <p>
	 * Some implementations are written to fail fast. If you are using a fast-fail implementation, and you call it 
	 * with a value that is not of type V, it will throw a ClassCastException instead of returning false.
	 * If you are not using a fail-fast implementation, your IDE will will still warn you if you call this method, if you
	 * declared your map to be a SafeMap.
	 * <p>
	 * @deprecated Use #safeHasValue() instead
	 *
	 * @see #safeHasValue(Object)
	 */
	@Deprecated
	@Override
	boolean containsValue(Object value);

	/**
	 * Deprecated due to lack of type safety. Use {@link #safeRemove(K)} instead.
	 * <p>
	 * {@inheritDoc}
	 * <p>
	 * Some implementations are written to fail fast. If you are using a fast-fail implementation, and you call it 
	 * with a key that is not of type K, it will throw a ClassCastException instead of returning false.
	 * If you are not using a fail-fast implementation, your IDE will will still warn you if you call this method, if you
	 * declared your map to be a SafeMap.
	 * <p>
	 * @deprecated Use #safeRemove() instead
	 *
	 * @see #safeHasKey(Object)
	 */
	@Deprecated
	@Override
	V remove(Object key);

	/**
	 * Deprecated due to lack of type safety. Use {@link #safeGetOrDefault(K, V)} instead.
	 * <p>
	 * {@inheritDoc}
	 * <p>
	 * Some implementations are written to fail fast. If you are using a fast-fail implementation, and you call it 
	 * with a key that is not of type K, it will throw a ClassCastException instead of returning {@code defaultValue}.
	 * If you are not using a fail-fast implementation, your IDE will will still warn you if you call this method, if you
	 * declared your map to be a SafeMap.
	 * <p>
	 * @deprecated Use #safeGetOrDefault() instead
	 *
	 * @see #safeGetOrDefault(K, V)
	 */
	@Deprecated
	@Override
	V getOrDefault(Object key, V defaultValue);
}
