package io.lamma

import io.lamma.Month._
import org.scalatest.{Matchers, FlatSpec}

class MonthSpec extends FlatSpec with Matchers {

  "ordinal" should "work" in {
    January.ordinal should be(1)
    February.ordinal should be(2)
    March.ordinal should be(3)
    April.ordinal should be(4)
    May.ordinal should be(5)
    June.ordinal should be(6)
    July.ordinal should be(7)
    August.ordinal should be(8)
    September.ordinal should be(9)
    October.ordinal should be(10)
    November.ordinal should be(11)
    December.ordinal should be(12)
  }

  "apply" should "work" in {
    Month(1) should be(January)
    Month(2) should be(February)
    Month(3) should be(March)
    Month(4) should be(April)
    Month(5) should be(May)
    Month(6) should be(June)
    Month(7) should be(July)
    Month(8) should be(August)
    Month(9) should be(September)
    Month(10) should be(October)
    Month(11) should be(November)
    Month(12) should be(December)
  }
}
