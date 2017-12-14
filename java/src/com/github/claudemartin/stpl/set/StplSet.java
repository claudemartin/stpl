package com.github.claudemartin.stpl.set;

import static java.util.Objects.requireNonNull;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.function.Consumer;

/**
 * A set for the stpl project can only contain other sets, as there are no other
 * things used. Numbers, tuples and such have to be constructed using sets.
 */
public interface StplSet extends Iterable<StplSet>, Comparable<StplSet> {

	/** Does this set contain the given set as a member? */
	boolean contains(StplSet set);

	/** Does this set contain the given sets as members? */
	default boolean containsAll(Iterable<? extends StplSet> sets) {
		for (StplSet set : sets) {
			if (!this.contains(set))
				return false;
		}
		return true;
	}

	/** Cardinality of this set. */
	long size();

	/** Returns true if and only if the two sets are equal. */
	default boolean equals(StplSet other) {
		if (!(other instanceof StplSet))
			return false;
		if (this == other)
			return true;
		return equal(this, other);
	}

	/** Are two given sets equal. */
	public static boolean equal(StplSet a, StplSet b) {
		if (a.size() != b.size())
			return false;
		return a.containsAll(b);
	}

	public static class JavaSet extends AbstractSet<JavaSet> {
		final StplSet set;

		public JavaSet(StplSet set) {
			this.set = set;
		}

		/**
		 * Members are ordered from <i>low</i> to <i>high</i>:
		 * 
		 * <p>
		 * Empty sets are equal. If A set a has fewer members than set B then A &lt; B.
		 * 
		 * <p>
		 * Examples:
		 * <ul>
		 * <li>{} == {}</li>
		 * <li>{} &lt; {{}}</li>
		 * <li>{{}} &lt; {{{}}}</li>
		 * <li>{{{}}} &lt; {{},{{}}}</li>
		 * <li>{{},{{}}} &lt; {{},{{{}}}}</li>
		 * <li>{{},{{},{{}}}} &lt; {{},{{},{{{}}}}}</li>
		 * </ul>
		 * 
		 */
		@Override
		public Iterator<JavaSet> iterator() {
			Iterator<StplSet> itr = set.iterator();
			return new Iterator<StplSet.JavaSet>() {
				@Override
				public JavaSet next() {
					return itr.next().toJavaSet();
				}

				@Override
				public boolean hasNext() {
					return itr.hasNext();
				}
			};
		}

		@Override
		public int size() {
			long size = set.size();
			if (size > (long) Integer.MAX_VALUE)
				return Integer.MAX_VALUE;
			return (int) size;
		}

	}

	default JavaSet toJavaSet() {
		return new JavaSet(this);
	}

	/** Generates all brackets and commas to represent this as a string. */
	default void generateTokens(Consumer<Character> consumer, int level) {
		// TODO : Use "level" to choose a pair of brackets.
		if (this.isEmpty()) {
			consumer.accept('∅');
			return;
		}
		consumer.accept('{');
		for (StplSet s : this)
			s.generateTokens(consumer, 1 + level);
		consumer.accept('}');
	}

	default boolean isEmpty() {
		return 0 == this.size();
	}

	public static String toString(StplSet set) {
		StringBuilder sb = new StringBuilder();
		set.generateTokens(c -> sb.append(c), 0);
		return sb.toString();
	}

	public static int compare(StplSet a, StplSet b) {
		requireNonNull(a, "a");
		requireNonNull(b, "b");
		if (a == b)
			return 0;
		long sizeA = a.size();
		long sizeB = b.size();
		if (sizeA != sizeB)
			return Long.compare(sizeA, sizeB);
		// The empty set is equal to itself.
		if (sizeA == 0)
			return 0;
		// compare low to high:
		Iterator<StplSet> itA = a.iterator();
		Iterator<StplSet> itB = b.iterator();
		for (int i = 0; i < sizeA; i++) {
			StplSet mA = itA.next();
			StplSet mB = itB.next();
			int comparison = compare(mA, mB);
			if (comparison != 0)
				return comparison;
		}
		return 0;
	}

	/** Returns a set with all elements of <code>this</code> or the given set. */
	public default StplSet union(StplSet set) {
		if (this == set)
			return this;
		if (this.isEmpty())
			return set;
		if (set.isEmpty())
			return this;
		// There's a much better way for doing this:
		// Create an iterator each and then add all members to a simple ArrayList.
		// But always the one with the lower value.

		TreeSet<StplSet> members = new TreeSet<>();
		this.forEach(members::add);
		set.forEach(members::add);
		return GeneralSet.of(members);
	}
	
	/** Returns a set with all elements of <code>this</code>, which are also in the given set. */
	public default StplSet intersect(StplSet set) {
		if (set.isEmpty() || this.isEmpty())
			return this;
		if (this == set)
			return this;
		// There is a better way. See union.
		TreeSet<StplSet> members = new TreeSet<>();
		this.forEach(s -> {
			if (set.contains(s))
				members.add(s);
		});
		return GeneralSet.of(members);
	}

	/** Returns a set with all elements of <code>this</code>, which aren't in the given set. */
	public default StplSet minus(StplSet set) {
		if (set.isEmpty() || this.isEmpty() || this == set)
			return this;
		// There is a better way. See union.
		TreeSet<StplSet> members = new TreeSet<>();
		this.forEach(members::add);
		set.forEach(members::remove);
		return GeneralSet.of(members);
	}

}
