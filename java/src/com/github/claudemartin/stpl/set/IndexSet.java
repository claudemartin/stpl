package com.github.claudemartin.stpl.set;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.TreeMap;

/** Set-theoretic definition of natural numbers as nested sets starting at ordinal 2. This is used for sequences where an index is needed
 * that can be distinguished from {@link ZFSet}.
 *
 * @author Claude Martin */
public class IndexSet implements ValueSet {

	private final long value;

	private static final TreeMap<Long, IndexSet> CACHE = new TreeMap<>();

	public static final IndexSet TWO = of(2L);
	public static final IndexSet THREE = of(3L);

	public static IndexSet of(final long value) {
		if (value < 2)
			throw new IllegalArgumentException("Indizes have to start at 2.");
		return CACHE.computeIfAbsent(value, IndexSet::new);
	}

	private IndexSet(final long value) {
		this.value = value;
	}

	@Override
	public Iterator<StplSet> iterator() {
		return new Iterator<StplSet>() {
			long next = IndexSet.this.value - 1;

			@Override
			public boolean hasNext() {
				return this.next > 0;
			}

			@Override
			public StplSet next() {
				final long _next = this.next;
				if (_next < 0)
					throw new NoSuchElementException();
				this.next = -1;
				if (_next == 1) { // 1 := {{}}
					return GeneralSet.containing(GeneralSet.EMPTY);
				}
				return of(_next);
			}

		};
	}

	/**
	 * The value as an integer. The value is a natural number equal to or greater
	 * than 2.
	 */
	@Override
	public long toLong() {
		return this.value;
	}

	@Override
	public boolean contains(final StplSet set) {
		if (set instanceof IndexSet) {
			return ((IndexSet) set).value == this.value -1;
		}
		for (final StplSet member : this) {
			if (member.equals(set))
				return true;
		}
		return false;
	}

	@Override
	public long size() {
		return 1L;
	}

	/**
	 * Predecessor of this ordinal. This will return <i>empty</i>, if the new value
	 * is below 2.
	 */
	@Override
	public Optional<IndexSet> predecessor() {
		if (this.value == 2)
			return Optional.empty();
		return Optional.of(of(this.value - 1));
	}

	@Override
	public IndexSet successor() {
		return of(1 + this.value);
	}

	@Override
	public boolean equals(final StplSet other) {
		if (other == null)
			return false;
		if (this == other)
			return true;
		if (other instanceof ZFSet) {
			return false;
		}
		if (other instanceof IndexSet) {
			return this.value == ((IndexSet) other).value;
		}
		// the other could be any kind of StplSet that happens to be equal.
		return StplSet.equal(this, other);

	}

	@Override
	public int hashCode() {
		return (int) (Long.hashCode(this.value) ^ -1L);
	}

	@Override
	public int compareTo(final StplSet other) {
		requireNonNull(other, "other");
		if (other instanceof IndexSet) {
			return Long.compare(this.toLong(), ((IndexSet) other).toLong());
		}
		return StplSet.compare(this, other);
	}

	@Override
	public String toString() {
		return StplSet.toString(this);
	}

}
