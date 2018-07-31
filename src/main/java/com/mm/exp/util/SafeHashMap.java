/*
 * Copyright © [2018] Common Securitization Solutions, LLC. All rights reserved.
 * This software contains confidential information and trade secrets of Common Securitization
 * Solutions, LLC. Use, disclosure, or reproduction is prohibited without the prior written
 * consent of Common Securitization Solutions, LLC.
 */

package com.mm.exp.util;

import java.util.HashMap;

@SuppressWarnings("unchecked")
public class SafeHashMap<K, V> extends HashMap<K, V> implements SafeMap<K, V> {
	@Override
	public V get(Object key) { return super.get((K)key); }

	@Override
	public boolean containsKey(Object key) {
		K k = (K) key;
		return super.containsKey(k); }

	@Override
	public V remove(Object key) { return super.remove((K)key); }

	@Override
	public boolean containsValue(Object value) { return super.containsValue((K)value); }

	@Override
	public V getOrDefault(Object key, V defaultValue) { return super.getOrDefault((K)key, defaultValue); }
}
