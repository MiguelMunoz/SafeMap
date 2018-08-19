package com.mm.exp;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;
//import static org.hamcrest.Matchers.*;

/**
 * <p>Created by IntelliJ IDEA.
 * <p>Date: 8/17/18
 * <p>Time: 11:08 AM
 *
 * @author Miguel Mu\u00f1oz
 */

@SuppressWarnings({"MagicNumber", "HardCodedStringLiteral"})
public class TestRange {
  @Test
  public void test3Items() {
    List<Integer> iList1 = Arrays.asList(-500, 3, 5000);
    List<Integer> iList2 = Arrays.asList(-500, 5000, 3);
    List<Integer> iList3 = Arrays.asList(3, -500, 5000);
    List<Integer> iList4 = Arrays.asList(3, 5000, -500);
    List<Integer> iList5 = Arrays.asList(5000, -500, 3);
    List<Integer> iList6 = Arrays.asList(5000, 3, -500);

    testRange(iList1);
    testRange(iList2);
    testRange(iList3);
    testRange(iList4);
    testRange(iList5);
    testRange(iList6);
  }
  
  @Test
  public void testSingeValue() {
    List<Integer> singleValue = Collections.singletonList(5);
    Range range = Range.getTheRange(singleValue);
    assertEquals(5, range.getMax());
    assertEquals(5, range.getMin());

    range = Range.getRange(singleValue);
    assertEquals(5, range.getMax());
    assertEquals(5, range.getMin());

    int max = Range.getMax(singleValue);
    assertEquals(5, max);
    int min = Range.getMin(singleValue);
    assertEquals(5, min);
  }
 
  @Test(expected = IllegalStateException.class)
  public void testEmptyRange() {
    List<Integer> list = new LinkedList<>();
    
    Range.getTheRange(list);
  }

  @Test(expected = IllegalStateException.class)
  public void testEmptyRange2() {
    List<Integer> list = new LinkedList<>();

    Range.getRange(list);
  }

  @Test(expected = IllegalStateException.class)
  public void testEmptyMax() {
    List<Integer> list = new LinkedList<>();
    Range.getMax(list);
  }

  @Test(expected = IllegalStateException.class)
  public void testEmptyMin() {
    List<Integer> list = new LinkedList<>();
    Range.getMin(list);
  }

  private void testRange(final List<Integer> iList) {
    Range<Integer> result = Range.getTheRange(iList);
    assertEquals(-500, result.getMin().intValue());
    assertEquals(5000, result.getMax().intValue());

    result = Range.getRange(iList);
    assertEquals(-500, result.getMin().intValue());
    assertEquals(5000, result.getMax().intValue());

    int max = Range.getMax(iList);
    assertEquals(5000, max);
    
    int min = Range.getMin(iList);
    assertEquals(-500, min);
    System.out.println(iList);
  }
  
  @Test
  public void testOneItem() {
    List<Integer> iList = new LinkedList<>();
    iList.add(12);
    Range<Integer> range = Range.getTheRange(iList);
    assertEquals(12, range.getMin().intValue());
    assertEquals(12, range.getMax().intValue());

    range = Range.getRange(iList);
    assertEquals(12, range.getMin().intValue());
    assertEquals(12, range.getMax().intValue());
  }

  @Ignore
  @Test
  public void speedTest() {
    List<Integer> intList = new LinkedList<>();
    Random random = new Random(5L);
    for (int i=0; i<10_000_000; ++i) {
      intList.add(random.nextInt());
    }

    for (int ii=0; ii<30; ++ii) {
      long lambdaTest = time(Range::getIntegerRange, intList);
      System.out.printf(" Lambda: %s ms%n", lambdaTest);
      long compareTest = time(Range::getTheRange, intList);
      System.out.printf("Compare: %s ms%n", compareTest);
      long localTest = time(this::getIntRange, intList);
      System.out.printf(" Local: %d ms%n%n", localTest);
    }
  }
  
  private <T extends Number> long time(Function<List<T>, Range<T>> function, List<T> theList) {
    long start = System.currentTimeMillis();
    Range<T> integerRange = function.apply(theList);
    long end = System.currentTimeMillis();
    System.out.printf("Range: %s%n", integerRange);
    return end - start;
  }
  
  private Range<Integer> getIntRange(List<Integer> list) {
    Iterator<Integer> itr = list.iterator();
    int min = itr.next();
    int max = min;
    while (itr.hasNext()) {
      int v = itr.next();
      if (v > max) {
        max = v;
      } else if (v < min) {
        min = v;
      }
    }
    return new Range<>(min, max);
  }
}
