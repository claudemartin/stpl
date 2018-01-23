package com.github.claudemartin.stpl.set;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/** Base class for {@link StplSet}.
 * 
 * @author Claude Martin */
public class GeneralSet implements StplSet {

	public static GeneralSet EMPTY = new GeneralSet();

	public static GeneralSet containing(final StplSet... members) {
		return of(Arrays.asList(members));
	}

	public static GeneralSet of(final Collection<StplSet> members) {
		if (members.size() == 0)
			return EMPTY;
		return new GeneralSet(members);
	}

	private final StplSet[] members;

	private GeneralSet() {
		this.members = new StplSet[0];
	}

	private GeneralSet(final Collection<StplSet> members) {
		this.members = this.makeMembersArray(members);
	}

	private StplSet[] makeMembersArray(final Collection<StplSet> c) {
		if (c.isEmpty())
			return EMPTY.members;

		final TreeSet<StplSet> list = new TreeSet<>(StplSet::compare);
		list.addAll(c);
		return list.toArray(new StplSet[list.size()]);
	}

	@Override
	public Iterator<StplSet> iterator() {
		return Arrays.asList(this.members).iterator();
	}

	@Override
	public boolean contains(final StplSet set) {
		requireNonNull(set, "set");
		return 0 <= Arrays.binarySearch(this.members, set, StplSet::compare);
	}

	@Override
	public long size() {
		return this.members.length;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof StplSet) {
			if (obj instanceof GeneralSet) {
				final GeneralSet other = (GeneralSet) obj;
				return Arrays.equals(this.members, other.members);
			} else {
				final StplSet other = (StplSet) obj;
				return StplSet.compare(this, other) == 0;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(this.members);
	}

	@Override
	public int compareTo(final StplSet o) {
		return StplSet.compare(this, o);
	}

}
