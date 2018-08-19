package com.mm.exp.extend;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@SuppressWarnings({"HardCodedStringLiteral", "MagicNumber"})
public class TestSafeMap {
	
	@Test
	public void testAsHashMap() {
		Map<String, Integer> safeMap = getSafeMap();
		
		Integer z = 0;
		boolean hasKey = safeMap.containsKey(z);
		assertFalse(hasKey);
	}
	
	private SafeMap<String, Integer> getSafeMap() {
		return getSafeMap(new HashMap<>());
	}

	private SafeMap<String, Integer> getSafeMap(Map<String, Integer> wrappedMap) {
		SafeMap<String, Integer> safeMap = MapUtil.wrap(wrappedMap);
		//noinspection deprecation,ResultOfMethodCallIgnored
		safeMap.containsKey("X"); // verify your IDE treats this as deprecated.
		for (int ii=0; ii<5; ++ii) {
			safeMap.put(String.valueOf(ii), ii);
		}
		return safeMap;
	}

	@Test
	public void testAsFastFailSafeHashMap() {
		Map<String, Integer> safeMap = getFastFailSafeHashMap();

		//noinspection TooBroadScope
		Integer z = 0;
		try {
			boolean hasKey = safeMap.containsKey(z);
			assertFalse(hasKey);
			fail("containsKey");
		} catch (ClassCastException cce) {
			assertThat(cce.getMessage(), CoreMatchers.containsString(Integer.class.getName()));
		}
	}

	private SafeMap<String, Integer> getFastFailSafeHashMap() {
		SafeMap<String, Integer> safeMap = new SafeHashMap<>(String.class, Integer.class);
		//noinspection deprecation
		boolean hasKey = safeMap.containsKey("X"); // verify your IDE treats this as deprecated.
		assertFalse(hasKey);
		for (int ii=0; ii<5; ++ii) {
			safeMap.put(String.valueOf(ii), ii);
		}
		return safeMap;
	}

  // This one should work.
	@Test
	public void testAsSaferHashMap() {
		Map<String, Integer> safeMap = getSaferHashMap();

		//noinspection TooBroadScope
		Integer z = 0;
		try {
			//noinspection ResultOfMethodCallIgnored
			safeMap.containsKey(z);
			fail("containsKey");
		} catch (ClassCastException cce) {
			assertTrue(cce.getMessage().contains(Integer.class.getName()));
		}
	}

	private SafeMap<String, Integer> getSaferHashMap() {
		SafeMap<String, Integer> safeMap = MapUtil.wrapFailFast(new HashMap<>(), String.class, Integer.class);
		//noinspection deprecation
		boolean hasKey = safeMap.containsKey("X"); // verify your IDE treats this as deprecated.
		assertFalse(hasKey);
		for (int ii=0; ii<5; ++ii) {
			safeMap.put(String.valueOf(ii), ii);
		}
		return safeMap;
	}
	
	@Test
	public void testAddedMethods() {
		SafeMap<String, Integer> safeMap = getSafeMap();
		assertEquals(2, safeMap.find("2").intValue());
		assertNull(safeMap.find("X"));
		assertTrue(safeMap.safeHasKey("4"));
		assertFalse(safeMap.safeHasKey("X"));
		assertTrue(safeMap.safeHasValue(4));
		assertFalse(safeMap.safeHasValue(12));
		assertEquals(3, safeMap.safeGetOrDefault("3", 10).intValue());
		assertEquals(10, safeMap.safeGetOrDefault("X", 10).intValue());
		
		int adjustedSize = safeMap.size() - 1;
		assertTrue(safeMap.safeHasKey("1"));
		assertEquals(1, safeMap.find("1").intValue());
		safeMap.safeRemove("1");
		assertEquals(adjustedSize, safeMap.size());
		assertFalse(safeMap.safeHasKey("1"));
		assertNull(safeMap.find("1"));
	}
}
