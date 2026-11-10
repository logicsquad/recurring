/**
 * <p>
 * Provides:
 * </p>
 *
 * <ul>
 * <li>{@link net.logicsquad.recurring.Schedule Schedule} interface (and a basic implementation) to represent a schedule of recurring
 * events.</li>
 * <li>{@link net.logicsquad.recurring.ScheduleElement ScheduleElement} class that represents an event and a description of its
 * occurrence.</li>
 * <li>{@link net.logicsquad.recurring.TemporalExpression TemporalExpression} interface representing an expression of some type of
 * recurrence.</li>
 * <li>Several implementations of basic {@link net.logicsquad.recurring.TemporalExpression TemporalExpression}s
 * ({@link net.logicsquad.recurring.DayInMonth DayInMonth}, {@link net.logicsquad.recurring.DayInWeek DayInWeek},
 * {@link net.logicsquad.recurring.RangeEachYear RangeEachYear})</li>
 * <li>Several implementations of {@link net.logicsquad.recurring.TemporalExpression TemporalExpression} that act as set operations for
 * combining basic {@link net.logicsquad.recurring.TemporalExpression TemporalExpression}s ({@link net.logicsquad.recurring.Union Union},
 * {@link net.logicsquad.recurring.Intersection Intersection}, {@link net.logicsquad.recurring.Difference Difference})</li>
 * </ul>
 *
 * <p>
 * {@link net.logicsquad.recurring.TemporalExpression TemporalExpression} has a single method, so a lambda can be an expression too.
 * Expressions are equal if they're built the same way from equal parts, not if they match the same dates.
 * </p>
 *
 * <p>
 * Everything in this package is immutable, and so safe to share between threads, as long as the events and any expressions of your own
 * are too.
 * </p>
 *
 * @author paulh
 */
package net.logicsquad.recurring;
