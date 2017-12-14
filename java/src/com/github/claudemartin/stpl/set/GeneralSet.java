package com.github.claudemartin.stpl.set;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

public class GeneralSet implements StplSet {

	public static GeneralSet EMPTY = new GeneralSet();

	public static GeneralSet containing(StplSet... members) {
		return of(Arrays.asList(members));
	}
	
	public static GeneralSet of(Collection<StplSet> members) {
		if (members.size() == 0)
			return EMPTY;
		return new GeneralSet(members);
	}

	private final StplSet[] members;

	private GeneralSet() {
		members = new StplSet[0];
	}

	private GeneralSet(Collection<StplSet> members) {
		this.members = makeMembersArray(members);
	}

	private StplSet[] makeMembersArray(Collection<StplSet> c) {
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
	public boolean contains(StplSet set) {
		requireNonNull(set, "set");
		return 0 <= Arrays.binarySearch(members, set, StplSet::compare);
	}

	@Override
	public long size() {
		return members.length;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof StplSet) {
			if (obj instanceof GeneralSet) {
				GeneralSet other = (GeneralSet) obj;
				return Arrays.equals(this.members, other.members);
			} else {
				StplSet other = (StplSet) obj;
				return StplSet.compare(this, other) == 0;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(members);
	}

	@Override
	public int compareTo(StplSet o) {
		return StplSet.compare(this, o);
	}

}
