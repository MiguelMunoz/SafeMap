/*
 * Copyright © [2018] Common Securitization Solutions, LLC. All rights reserved.
 * This software contains confidential information and trade secrets of Common Securitization
 * Solutions, LLC. Use, disclosure, or reproduction is prohibited without the prior written
 * consent of Common Securitization Solutions, LLC.
 */

package com.mm.exp.util;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class TestSafeMap {
	
  // I don't expect this to work.
	@Test
	public void testAsHashMap() {
		Map<String, Integer> safeMap = getSafeMap();
		
		Integer z = Integer.valueOf(0);
		try {
			safeMap.containsKey(z);
			fail("containsKey");
		} catch (ClassCastException cce) {
			assertEquals(Integer.class.getName(), cce.getMessage());
		}
	}
	
	private SafeMap<String, Integer> getSafeMap() {
		SafeMap<String, Integer> safeMap = MapUtil.wrap(new HashMap<>());
		safeMap.containsKey("X"); // verify your IDE treats this as deprecated.
		for (int ii=0; ii<5; ++ii) {
			safeMap.put(String.valueOf(ii), ii);
		}
		return safeMap;
	}

  // I don't expect this to work.
	@Test
	public void testAsSafeHashMap() {
		Map<String, Integer> safeMap = getSafeHashMap();

		Integer z = Integer.valueOf(0);
		try {
			safeMap.containsKey(z);
			fail("containsKey");
		} catch (ClassCastException cce) {
			assertEquals(Integer.class.getName(), cce.getMessage());
		}
	}

	private SafeMap<String, Integer> getSafeHashMap() {
		SafeMap<String, Integer> safeMap = new SafeHashMap<>();
		safeMap.containsKey("X"); // verify your IDE treats this as deprecated.
		for (int ii=0; ii<5; ++ii) {
			safeMap.put(String.valueOf(ii), ii);
		}
		return safeMap;
	}

  // This one should work.
	@Test
	public void testAsSaferHashMap() {
		Map<String, Integer> safeMap = getSaferHashMap();

		Integer z = Integer.valueOf(0);
		try {
			safeMap.containsKey(z);
			fail("containsKey");
		} catch (ClassCastException cce) {
			assertTrue(cce.getMessage().contains(Integer.class.getName()));
		}
	}

	private SafeMap<String, Integer> getSaferHashMap() {
		SafeMap<String, Integer> safeMap = MapUtil.wrap(new HashMap<>(), String.class, Integer.class);
		safeMap.containsKey("X"); // verify your IDE treats this as deprecated.
		for (int ii=0; ii<5; ++ii) {
			safeMap.put(String.valueOf(ii), ii);
		}
		return safeMap;
	}
}
