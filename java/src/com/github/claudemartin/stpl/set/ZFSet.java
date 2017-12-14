package com.github.claudemartin.stpl.set;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Set-theoretic definition of natural numbers as described in Zermelo–Fraenkel
 * set theory. each set has an ordinal, which is its value. All natural numbers
 * (including zero) can be represents by such a set.
 */
public class ZFSet implements ValueSet {

	private final long value;

	private static final TreeMap<Long, ZFSet> CACHE = new TreeMap<>();

	public static final ZFSet ZERO = of(0L);
	public static final ZFSet ONE = of(1L);

	public static ZFSet of(long value) {
		return CACHE.computeIfAbsent(value, ZFSet::new);
	}

	private ZFSet(long value) {
		this.value = value;
	}

	@Override
	public Iterator<StplSet> iterator() {
		return new Iterator<StplSet>() {
			long next = 0L;

			@Override
			public boolean hasNext() {
				return next < value;
			}

			@Override
			public ZFSet next() {
				if (!hasNext())
					throw new NoSuchElementException();
				return of(next++);
			}

		};
	}

	/**
	 * The value as an integer. The value is a natural number (including zero). Each
	 * ordinal is the well-ordered set of all smaller ordinals. So it's actually the
	 * size of this set. For example, the ordinal <i>n</i> contains all ordinals
	 * from 0 to (<i>n</i>-1). Count them to get the value.
	 */
	@Override
	public long toLong() {
		return value;
	}

	@Override
	public boolean contains(StplSet set) {
		if (set instanceof ZFSet) {
			// each ordinal contains all smaller ordinals.
			return ((ZFSet) set).value < this.value;
		}
		for (StplSet member : this) {
			if (member.equals(set))
				return true;
		}
		return false;
	}

	@Override
	public long size() {
		// When we construct the natural numbers like Zermelo and Fraenkel so, the size
		// (cardinality) is actually the value.
		return value;
	}

	@Override
	public Optional<ZFSet> predecessor() {
		if (this.value == 0)
			return Optional.empty();
		return Optional.of(of(value - 1));
	}

	@Override
	public ZFSet successor() {
		return of(1 + value);
	}

	@Override
	public boolean equals(StplSet other) {
		if (other == null)
			return false;
		if (this == other)
			return true;
		if (other instanceof ZFSet) {
			return this.value == ((ZFSet) other).value;
		}
		// the other could be any kind of StplSet that happens to be equal.
		return StplSet.equal(this, other);

	}

	@Override
	public int hashCode() {
		return Long.hashCode(value);
	}

	@Override
	public int compareTo(StplSet other) {
		requireNonNull(other, "other");
		if (other instanceof ZFSet)
			return Long.compare(this.toLong(), ((ZFSet) other).toLong());
		return StplSet.compare(this, other);
	}

	@Override
	public String toString() {
		return StplSet.toString(this);
	}
}
