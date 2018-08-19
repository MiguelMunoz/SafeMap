package com.mm.exp;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

/**
 * <p>Created by IntelliJ IDEA.
 * <p>Date: 8/16/18
 * <p>Time: 11:16 AM
 *
 * @author Miguel Mu\u00f1oz
 */
@SuppressWarnings({"StringConcatenation", "WeakerAccess"})
public class Range<X extends Number> {
  private final X min;
  private final X max;
  
  public Range(X min, X max) {
    if (max.doubleValue() > min.doubleValue()) {
      this.min = min;
      this.max = max;
    } else {
      this.max = min;
      this.min = max;
    }
  }

  /**
   * Private constructor allows member methods to skip checking the order of the parameters.
   * @param min Minimum value
   * @param max Maximum value
   * @param ignored Not used. Only there for constructor choice.
   */
  private Range(X min, X max, @SuppressWarnings("unused") boolean ignored) {
    this.min = min;
    this.max = max;
  }
  
  public X getMin() { return min; }
  public X getMax() { return max; }
  
//  public static Range<Integer> getRange(Integer[] data) {
//    if (data.length == 0) {
//      throw new IllegalStateException("Empty Array");
//    }
//    
//    int min = data[0];
//    int max = min;
//    for (int i: data) {
//      if (i > max) {
//        max = i;
//      } else if (i < min) {
//        min = i;
//      }
//    }
//    return new Range<>(min, max, true);
//  }
  
  public static <N extends Number & Comparable<N>> Range<N> getRange(N[] data) {
    return getRange(Arrays.asList(data));
  }

  public static Range<Integer> getIntegerRange(Iterable<Integer> data) {
    return getRange(((x, y) -> x > y), (x, y) -> x < y, data);
  }

  public static Range<Double> getDoubleRange(Iterable<Double> data) {
    return getRange(((x, y) -> x > y), (x, y) -> x < y, data);
  }
  
  public static Range<Long> getLongRange(Iterable<Long> data) {
    return getRange(((x, y) -> x > y), (x, y) -> x < y, data);
  }

  public static Range<Float> getFloatRange(Iterable<Float> data) {
    return getRange(((x, y) -> x > y), (x, y) -> x < y, data);
  }

  public static Range<Short> getShortRange(Iterable<Short> data) {
    return getRange(((x, y) -> x > y), (x, y) -> x < y, data);
  }

  public static Range<Byte> getByteRange(Iterable<Byte> data) {
    return getRange(((x, y) -> x > y), (x, y) -> x < y, data);
  }

  public static Range<Integer> getIntegerRange(int[] data) {
    return getRange(((x, y) -> x > y), (x, y) -> x < y, getIterator(data));
  }
  
  public static <N extends Number & Comparable<N>> Range<N> getRange(Iterable<N> data) {
    return getRange(((x, y) -> x.compareTo(y) > 0), (x, y) -> x.compareTo(y) < 0, data);
  }
  
  public static <N extends Number & Comparable<N>> Range<N> getTheRange(Iterable<N> data) {
    Iterator<N> iterator = data.iterator();
    if (!iterator.hasNext()) {
      throw new IllegalStateException("Empty Iterable");
    }
    N min = iterator.next();
    N max = min;
    while (iterator.hasNext()) {
      N value = iterator.next();
      if (value.compareTo(max) > 0) {
        max = value;
      } else if (value.compareTo(min) < 0) {
        min = value;
      }
    }
    return new Range<>(min, max, true);
  }

  private static Iterator<Integer> getIterator(final int[] data) {
    return new Iterator<Integer>() {
      private int index = 0;
      @Override
      public boolean hasNext() {
        return index >= data.length;
      }

      @Override
      public Integer next() {
        if (hasNext()) {
          return data[index++];
        }
        throw new NoSuchElementException("Index " + index);
      }
    };
  }

  private static <N extends Number> Range<N> getRange(
      final BiFunction<N, N, Boolean> isGreater,
      final BiFunction<N, N, Boolean> isLess,
      final Iterable<N> data)
  {
    return getRange(isGreater, isLess, data.iterator());
  }

    private static <N extends Number> Range<N> getRange(
      final BiFunction<N, N, Boolean> isGreater, 
      final BiFunction<N, N, Boolean> isLess, 
      final Iterator<N> iterator)
  {
    if (!iterator.hasNext()) {
      throw new IllegalStateException("Empty DataSet");
    }
    // Initialize both min and max to the first value.
    N min = iterator.next();
    N max = min;

    while (iterator.hasNext()) {
      N value = iterator.next();
      if (isGreater.apply(value, max)) {
        max = value;
      } else if (isLess.apply(value, min)) {
        min = value;
      }
    }
    return new Range<>(min, max, true);
  }
  
  private static <T> Iterator<T> arrayIterator(T[] array) {
    return new Iterator<T>() {
      private int index = 0;
      @Override
      public boolean hasNext() {
        return index >= array.length;
      }

      @Override
      public T next() {
        if (hasNext()) {
          return array[index++];
        }
        throw new NoSuchElementException("Index " + index);
      }
    };
  }

  @Override
  public String toString() {
    //noinspection HardCodedStringLiteral
    return String.format("{%s - %s}", min, max);
  }
  
  public static <N extends Number & Comparable<N>> N getMax(Iterable<N> collection) {
    Iterator<N> iterator = collection.iterator();
    if (!iterator.hasNext()) {
      throw new IllegalStateException("Empty Iterable");
    }
    N max = iterator.next();
    while (iterator.hasNext()) {
      N value = iterator.next();
      if (value.compareTo(max) > 0) {
        max = value;
      }
    }
    return max;
  }
  
  public static <N extends Number & Comparable<N>> N getMin(Iterable<N> collection) {
    Iterator<N> iterator = collection.iterator();
    if (!iterator.hasNext()) {
      throw new IllegalStateException("Empty Iterable");
    }
    N min = iterator.next();
    while (iterator.hasNext()) {
      N value = iterator.next();
      if (value.compareTo(min) < 0) {
        min = value;
      }
    }
    return min;
  }
}
