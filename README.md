Lamma
=======

Lamma schedule generator is a professional schedule generation library for financial instruments schedules like mortgage repayment schedule, fixed income coupon payment, equity derivative fixing date generation etc.

Get Started
-----------

Say we want to generate the schedule for following Fixed Coupon Note. Schedule related terms are:
```
Issue Date      : 2014-03-01
Expiry Date     : 2017-03-31        // last period will be longer
Tenor           : 37m
Frequency       : Semi-annual (6m)
Coupon Date     : Last day of month, modified following (last working day of the month)
Settlement Date : Coupon Date + 2 working days
```

Schedule generation code looks like this:

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

Done!

|    Period |  From Date |    To Date | CouponDate | SettlementDate |
| --------: | ---------: | ---------: | ---------: | -------------: |
|         1 | 2014-03-01 | 2014-08-31 | 2014-08-29 |     2014-09-02 |
|         2 | 2014-09-01 | 2015-02-28 | 2015-02-27 |     2015-03-03 |
|         3 | 2015-03-01 | 2015-08-31 | 2015-08-31 |     2015-09-02 |
|         4 | 2015-09-01 | 2016-02-29 | 2016-02-29 |     2016-03-02 |
|         5 | 2016-03-01 | 2016-08-31 | 2016-08-31 |     2016-09-02 |
|         6 | 2016-09-01 | 2017-03-31 | 2017-03-31 |     2017-04-04 |

Coming soon
-----------
More samples and detailed documentations will come soon with: http://lamma.io

License
-------
Apache 2.0 http://www.apache.org/licenses/LICENSE-2.0.txt
