package io.lamma;

/**
 * utility class to construct StubRulePeriodBuilder with Java<br>
 *
 * The original Scala version io.lamma.StubRulePeriodBuilder is not quite Java friendly.
 */
public class StubRulePeriodBuilders {

    /**
     * utility methods to construct StartRule and EndRule
     */
    public static class Rules {
        public static final StubRulePeriodBuilder.StartRule NO_START = StubRulePeriodBuilder$.MODULE$.NoStartRule();

        public static final StubRulePeriodBuilder.EndRule NO_END = StubRulePeriodBuilder$.MODULE$.NoEndRule();

        public static StubRulePeriodBuilder.StartRule noStart() {
            return NO_START;
        }

        public static StubRulePeriodBuilder.EndRule noEnd() {
            return NO_END;
        }

        public static StubRulePeriodBuilder.StartRule shortStart() {
            return new StubRulePeriodBuilder.ShortStart(StubRulePeriodBuilder.ShortStart$.MODULE$.apply$default$1());
        }

        public static StubRulePeriodBuilder.StartRule shortStart(int n) {
            return StubRulePeriodBuilder.ShortStart$.MODULE$.apply(n);
        }

        public static StubRulePeriodBuilder.StartRule longStart() {
            return new StubRulePeriodBuilder.LongStart(StubRulePeriodBuilder.LongStart$.MODULE$.apply$default$1());
        }

        public static StubRulePeriodBuilder.StartRule longStart(int n) {
            return StubRulePeriodBuilder.LongStart$.MODULE$.apply(n);
        }

        public static StubRulePeriodBuilder.EndRule shortEnd() {
            return new StubRulePeriodBuilder.ShortEnd(StubRulePeriodBuilder.ShortEnd$.MODULE$.apply$default$1());
        }

        public static StubRulePeriodBuilder.EndRule shortEnd(int n) {
            return StubRulePeriodBuilder.ShortEnd$.MODULE$.apply(n);
        }

        public static StubRulePeriodBuilder.EndRule longEnd() {
            return new StubRulePeriodBuilder.LongEnd(StubRulePeriodBuilder.LongEnd$.MODULE$.apply$default$1());
        }

        public static StubRulePeriodBuilder.EndRule longEnd(int n) {
            return StubRulePeriodBuilder.LongEnd$.MODULE$.apply(n);
        }
    }

    /**
     * @return StubRulePeriodBuilder with no start rule and no end rule
     */
    public static StubRulePeriodBuilder noStartNoEnd() {
        return StubRulePeriodBuilder$.MODULE$.apply(StubRulePeriodBuilder$.MODULE$.apply$default$1(), StubRulePeriodBuilder$.MODULE$.apply$default$2());
    }

    /**
     * @return StubRulePeriodBuilder with user defined start rule but no end rule
     */
    public static StubRulePeriodBuilder of(StubRulePeriodBuilder.StartRule start) {
        return StubRulePeriodBuilder$.MODULE$.apply(start, StubRulePeriodBuilder$.MODULE$.apply$default$2());
    }

    /**
     * @return StubRulePeriodBuilder with no start rule but user defined end rule
     */
    public static StubRulePeriodBuilder of(StubRulePeriodBuilder.EndRule end) {
        return StubRulePeriodBuilder$.MODULE$.apply(StubRulePeriodBuilder$.MODULE$.apply$default$1(), end);
    }

    /**
     * @return StubRulePeriodBuilder with user defined start rule and end rule
     */
    public static StubRulePeriodBuilder of(StubRulePeriodBuilder.StartRule start, StubRulePeriodBuilder.EndRule end) {
        return StubRulePeriodBuilder$.MODULE$.apply(start, end);
    }
}
