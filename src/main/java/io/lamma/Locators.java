package io.lamma;

/**
 * utility class to construct Locators with Java<br>
 *
 * The original Scala io.lamma.Locator uses infix DSL, which is not quite Java friendly.
 */
public class Locators {

    interface Builder {
        Locator build();
    }

    public static NthDayBuilder firstDay() {
        return nthDay(1);
    }

    public static NthDayBuilder nthDay(Integer n) {
        if (n == null) {
            throw new IllegalArgumentException("n must not be null");
        }

        return new NthDayBuilder(n);
    }

    public static LastDayBuilder lastDay() {
        return new LastDayBuilder();
    }

    public static NthWeekdayBuilder first(DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) {
            throw new IllegalArgumentException("Day of week must not be null");
        }

        return nth(1, dayOfWeek);
    }

    public static NthWeekdayBuilder nth(Integer n, DayOfWeek dayOfWeek) {
        if (n == null) {
            throw new IllegalArgumentException("n must not be null");
        }
        if (dayOfWeek == null) {
            throw new IllegalArgumentException("Day of week must not be null");
        }

        return new NthWeekdayBuilder(n, dayOfWeek);
    }

    public static LastWeekdayBuilder last(DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) {
            throw new IllegalArgumentException("Day of week must not be null");
        }

        return new LastWeekdayBuilder(dayOfWeek);
    }

    public static class NthDayBuilder implements Builder {
        private final Integer n;

        private Month m;

        NthDayBuilder(Integer n) {
            this.n = n;
        }

        public NthDayBuilder of(Month m) {
            this.m = m;
            return this;
        }

        public Locator build() {
            if (m == null) {
                return Locator$.MODULE$.apply(n);
            } else {
                return Locator$.MODULE$.apply(n, m);
            }
        }

        public Integer getN() {
            return n;
        }

        public Month getM() {
            return m;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NthDayBuilder that = (NthDayBuilder) o;

            if (m != null ? !m.equals(that.m) : that.m != null) return false;
            if (!n.equals(that.n)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = n.hashCode();
            result = 31 * result + (m != null ? m.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "NthDayBuilder{" +
                    "n=" + n +
                    ", m=" + m +
                    '}';
        }
    }

    public static class LastDayBuilder implements Builder {

        private Month month;

        public LastDayBuilder of(Month month) {
            this.month = month;
            return this;
        }

        public Locator build() {
            if (month == null) {
                return Locator$.MODULE$.apply(Locator.Last$.MODULE$);
            } else {
                return Locator$.MODULE$.apply(Locator.Last$.MODULE$, month);
            }
        }

        public Month getMonth() {
            return month;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LastDayBuilder that = (LastDayBuilder) o;

            if (month != null ? !month.equals(that.month) : that.month != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return month != null ? month.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "LastDayBuilder{" +
                    "month=" + month +
                    '}';
        }
    }

    public static class NthWeekdayBuilder implements Builder {

        private final Integer n;

        private final DayOfWeek dayOfWeek;

        private Month month;

        NthWeekdayBuilder(Integer n, DayOfWeek dayOfWeek) {
            this.n = n;
            this.dayOfWeek = dayOfWeek;
        }

        public NthWeekdayBuilder of(Month month) {
            this.month = month;
            return this;
        }

        public Locator build() {
            if (month == null) {
                return Locator$.MODULE$.apply(n, dayOfWeek);
            } else {
                return Locator$.MODULE$.apply(n, dayOfWeek, month);
            }
        }

        public Integer getN() {
            return n;
        }

        public DayOfWeek getDayOfWeek() {
            return dayOfWeek;
        }

        public Month getMonth() {
            return month;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NthWeekdayBuilder that = (NthWeekdayBuilder) o;

            if (!dayOfWeek.equals(that.dayOfWeek)) return false;
            if (month != null ? !month.equals(that.month) : that.month != null) return false;
            if (!n.equals(that.n)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = n.hashCode();
            result = 31 * result + dayOfWeek.hashCode();
            result = 31 * result + (month != null ? month.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "NthWeekdayBuilder{" +
                    "n=" + n +
                    ", dayOfWeek=" + dayOfWeek +
                    ", month=" + month +
                    '}';
        }
    }

    public static class LastWeekdayBuilder implements Builder {
        private final DayOfWeek dayOfWeek;

        private Month month;

        LastWeekdayBuilder(DayOfWeek dayOfWeek) {
            this.dayOfWeek = dayOfWeek;
        }

        public LastWeekdayBuilder of(Month month) {
            this.month = month;
            return this;
        }

        public Locator build() {
            if (month == null) {
                return Locator$.MODULE$.apply(Locator.Last$.MODULE$, dayOfWeek);
            } else {
                return Locator$.MODULE$.apply(Locator.Last$.MODULE$, dayOfWeek, month);
            }
        }

        public DayOfWeek getDayOfWeek() {
            return dayOfWeek;
        }

        public Month getMonth() {
            return month;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            LastWeekdayBuilder that = (LastWeekdayBuilder) o;

            if (!dayOfWeek.equals(that.dayOfWeek)) return false;
            if (month != null ? !month.equals(that.month) : that.month != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = dayOfWeek.hashCode();
            result = 31 * result + (month != null ? month.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "LastWeekdayBuilder{" +
                    "dayOfWeek=" + dayOfWeek +
                    ", month=" + month +
                    '}';
        }
    }
}
