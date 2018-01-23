package com.github.claudemartin.stpl.instructions;

import static com.github.claudemartin.stpl.instructions.Parameter.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.claudemartin.stpl.set.StplSet;
import com.github.claudemartin.stpl.set.ValueSet;

/** The instruction set. The position ({@link #ordinal()}) defines which number is used to represent the instruction.
 *
 * @author Claude Martin */
public enum Instruction {
	/** Overwrite set at TO with the set of FROM.
	 *
	 * <p>
	 * MOVE(TO, FROM); */
	MOVE(ADDRESS, ADDRESS),
	/** Write some {@link ValueSet} to given DESTINATION address.
	 * <p>
	 * STORE_VALUE(DESTINATION, VALUE); */
	STORE_VALUE(ADDRESS, ORDINAL),
	/** Write some {@link StplSet} to given DESTINATION address.
	 * <p>
	 * STORE_VALUE(DESTINATION, LITERAL); */
	STORE_LITERAL(ADDRESS, LITERAL),

	/** Increase the value of the {@link ValueSet} at given ADDRESS by one.
	 * <p>
	 * INCREMENT(ADDRESS); */
	INCREMENT(ADDRESS),

	/** Decrease the value of the {@link ValueSet} at given ADDRESS by one.
	 * <p>
	 * DECREMENT(ADDRESS); */
	DECREMENT(ADDRESS),

	/** Write UNION of SET1 and SET2 to DESTINATION.
	 *
	 * <p>
	 * UNION(DESTINATION, SET1, SET2); */
	UNION(ADDRESS, ADDRESS, ADDRESS),
	/** Write intersection of SET1 and SET2 to DESTINATION.
	 *
	 * <p>
	 * INTERSECT(DESTINATION, SET1, SET2); */
	INTERSECT(ADDRESS, ADDRESS, ADDRESS),

	/** Write sum of SET1 and SET2 to DESTINATION. Both SET1 and SET2 must contain valid {@link ValueSet}s.
	 * <p>
	 * SUM(DESTINATION, SET1, SET2); */
	SUM(ADDRESS, ADDRESS, ADDRESS),
	/** Write product of SET1 and SET2 to DESTINATION. Both SET1 and SET2 must contain valid {@link ValueSet}s.
	 * <p>
	 * PROD(DESTINATION, SET1, SET2); */
	PROD(ADDRESS, ADDRESS, ADDRESS),
	/** Write division result of SET1 divided by SET2 to DESTINATION. Both SET1 and SET2 must contain valid {@link ValueSet}s. This is integer
	 * division and the result is also a {@link ValueSet}.
	 * <p>
	 * DIV(DESTINATION, SET1, SET2); */
	DIV(ADDRESS, ADDRESS, ADDRESS),

	/** Read user input as a single Unicode character and store it at given ADDRESS.
	 * <p>
	 * READ_CHAR(ADDRESS); */
	READ_CHAR(ADDRESS),
	/** Read user input as string and store it at given ADDRESS. String will be terminated by ∅.
	 * <p>
	 * READ_STRING(ADDRESS); */
	READ_STRING(ADDRESS),
	/** Print the Unicode characer at given ADDRESS.
	 * <p>
	 * PRINT_CHAR(ADDRESS); */
	PRINT_CHAR(ADDRESS),

	/** Print the string at given ADDRESS. String has to be terminated by ∅.
	 * <p>
	 * PRINT_STRING(ADDRESS); */
	PRINT_STRING(ADDRESS),

	/** Jump forward as many instuctions as stored in DISTANCE address.
	 *
	 * <p>
	 * JUMP(DISTANCE); */
	JUMP(ADDRESS),
	/** Jump forward as many instuctions as given by DISTANCE value.
	 *
	 * <p>
	 * JUMP(DISTANCE); */
	JUMP_BY(ORDINAL),
	/** Jump forward as many instuctions as given in DISTANCE address, if CONDITION is not ∅.
	 *
	 * <p>
	 * JUMP(CONDITION, DISTANCE); */
	JUMP_IF(ADDRESS, ADDRESS),

	/** Jump back as many instuctions as stored in DISTANCE address.
	 *
	 * <p>
	 * JUMP_BACK(DISTANCE); */
	JUMP_BACK(ADDRESS),
	/** Jump back as many instuctions as given by DISTANCE value.
	 *
	 * <p>
	 * JUMP_BACK(DISTANCE); */
	JUMP_BACK_BY(ORDINAL),
	/** Jump back as many instuctions as given in DISTANCE address, if CONDITION is not ∅.
	 *
	 * <p>
	 * JUMP_BACK(CONDITION, DISTANCE); */
	JUMP_BACK_IF(ADDRESS, ADDRESS),

	/** Quit program and return given VALUE.
	 *
	 * <p>
	 * EXIT(VALUE); */
	EXIT(ORDINAL);

	private final List<Parameter> parameters;

	Instruction(final Parameter... parameters) {
		this.parameters = Collections.unmodifiableList(Arrays.asList(parameters));
	}

	public List<Parameter> getParameters() {
		return this.parameters;
	}
}
