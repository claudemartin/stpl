package com.github.claudemartin.stpl.set;

import java.util.Optional;

/** A ValueSet represents some ordinal as a set. */
public interface ValueSet extends StplSet {
	/** Value as an integer. */
	long toLong();

	/** Previous value. */
	Optional<? extends ValueSet> predecessor();

	/** Next value. */
	StplSet successor();

}
