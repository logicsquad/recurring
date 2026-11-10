package net.logicsquad.recurring;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

/**
 * Unit tests on {@link CompositeTemporalExpression}.
 *
 * @author paulh
 */
public class CompositeTemporalExpressionTest {
	/**
	 * {@link CompositeTemporalExpression} is sealed, so only {@link Union} and {@link Intersection} can extend it.
	 */
	@Test
	public void onlyUnionAndIntersectionCanExtend() {
		assertTrue(CompositeTemporalExpression.class.isSealed());
		assertEquals(new HashSet<>(Arrays.asList(Union.class, Intersection.class)),
				new HashSet<>(Arrays.asList(CompositeTemporalExpression.class.getPermittedSubclasses())));
		return;
	}
}
