package com.github.claudemartin.stpl.instructions;

import com.github.claudemartin.stpl.set.IndexSet;
import com.github.claudemartin.stpl.set.StplSet;
import com.github.claudemartin.stpl.set.ValueSet;

public enum Parameter {
	/** An address on the heap, given as an {@link IndexSet}. */
	ADDRESS,

	/** A literal set, given as an {@link StplSet}. This does not necessarily have to be a {@link ValueSet} */
	LITERAL,

	/** A literal ordinal value, given as an {@link ValueSet}. */
	ORDINAL;
}
