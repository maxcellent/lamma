package io.lamma

import io.lamma.Locator.Last
import io.lamma.Month.February
import org.scalatest.{Matchers, WordSpec}

/**
 * this spec covers io/lamma/package.scala
 */
class GlobalSpec extends WordSpec with Matchers {

  "LocatorImplict" should {
    // weekly locator
    //   on weekday directly (eg, on Tuesday)  =>  Locator(Every, Some(Tuesday))
    //
    // monthly locator
    //   on ordinal day (eg, on 10 th day)  =>  Locator(Ordinal(10))
    //   on last day (eg, on last day)      =>  Locator(Last)
    //   on ordinal weekday (eg, on first Friday) => Locator(Ordinal(1), Some(Friday))
    //   on last weekday (eg, on last Saturday) =>  Locator(Last, Some(Saturday))
    //
    // yearly locator
    //   on ordinal day (eg, on 10 th day)  => Locator(Ordinal(10))
    //   on last day (eg, on last day)      => Locator(Last)
    //   on ordinal weekday (eg, on first Friday) => Locator(First, Some(Friday))
    //   on last weekday (eg, on last Saturday)   => Locator(Last, Saturday)
    //
    //   on ordinal day + month (eg, on 10 th day of June)  =>  Locator(Ordinal(10), Some(June))
    //   on last day + month (eg, on last day of July)         =>  Locator(Last, Some(July))
    //   on ordinal weekday + month (eg, on first Friday of June) => Locator(First, Some(Friday), Some(June))
    //   on last weekday + month (eg, on last Saturday of June)   => Locator(Last, Some(Saturday), Some(June))

    // used in both month and year location
    "work with nth day" in {
      val expected = Locator(10)
      10 th day should be(expected)
    }

    "work with last day" in {
      val expected = Locator(Last)
      lastDay should be(expected)
    }

    "work with nth weekday" in {
      val expected = Locator(2, Friday)
      2 nd Friday should be(expected)
    }

    "work with last weekday" in {
      val expected = Locator(Last, Friday)
      lastFriday should be(expected)
    }

    // used in year location
    "work with nth day of month" in {
      val expected = Locator(10, February)
      10 th day of February should be(expected)
    }

    "work with last day of month" in {
      val expected = Locator(Last, February)
      lastDay of February should be(expected)
    }

    "work with nth weekday of month" in {
      val expected = Locator(3, Friday, February)
      3 rd Friday of February should be(expected)
    }

    "work with last weekday of month" in {
      val expected = Locator(Last, Friday, February)
      lastFriday of February should be(expected)
    }
  }
}
