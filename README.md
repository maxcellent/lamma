Lamma
=======

Lamma schedule generator is a professional schedule generation library for financial instruments schedules like mortgage repayment schedule, fixed income coupon payment, equity derivative fixing date generation etc.

### Install

```
libraryDependencies += "io.lamma" %% "lamma" % "1.0"
```
or if you are using maven
```
<dependency>
    <groupId>io.lamma</groupId>
    <artifactId>lamma_2.10</artifactId>
    <version>1.0</version>
</dependency>
```

### Get Started

#### Say we want to generate the schedule for a Fixed Coupon Note.

Schedule related terms are:
```
Issue Date      : 2014-03-01
Expiry Date     : 2017-03-31        // last period will be longer
Tenor           : 37m
Frequency       : Semi-annual (6m)
Coupon Date     : Last day of month, modified following (last working day of the month)
Settlement Date : Coupon Date + 2 working days
```

#### Schedule generation code looks like this:

```scala
import io.lamma._

val cal = WeekendCalendar // let's replace this with a real business calendar in production
val couponDate = DateDef("CouponDate", relativeTo = PeriodEnd, selector = ModifiedFollowing(cal))
val settlementDate = DateDef("SettlementDate", relativeTo = OtherDate("CouponDate"), shifter = ShiftWorkingDays(2, cal))

Lamma.schedule(
      start = Date(2014, 3, 1),
      end = Date(2017, 3, 31),
      pattern = Months(6, LastDayOfMonth),
      periodBuilder = StubRulePeriodBuilder(endRule = LongEnd(270)),
      dateDefs = couponDate :: settlementDate :: Nil
    )
```

#### Done! 

|    Period |  From Date |    To Date | CouponDate | SettlementDate |
| --------: | ---------: | ---------: | ---------: | -------------: |
|         1 | 2014-03-01 | 2014-08-31 |_2014-08-29_|    _2014-09-02_|
|         2 | 2014-09-01 | 2015-02-28 |_2015-02-27_|    _2015-03-03_|
|         3 | 2015-03-01 | 2015-08-31 | 2015-08-31 |     2015-09-02 |
|         4 | 2015-09-01 |_2016-02-29_|_2016-02-29_|     2016-03-02 |
|         5 | 2016-03-01 | 2016-08-31 | 2016-08-31 |     2016-09-02 |
|         6 | 2016-09-01 | 2017-03-31 | 2017-03-31 |    _2017-04-04_|

#### What do you get?
1. in Period 1 and 2, modified following convention is applied based on holiday calendar
2. in Period 4, leap year is handled properly
3. despite of 6m frequency. Last stub month (2017-03) is merged automatially to the last period.
4. working days are considered when generating settlement date

Coming soon
-----------
More samples and detailed documentations will come soon with: http://lamma.io

License
-------
Apache 2.0 http://www.apache.org/licenses/LICENSE-2.0.txt
