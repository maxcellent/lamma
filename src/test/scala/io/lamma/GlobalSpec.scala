package io.lamma

import io.lamma.Locator.{Last, Ordinal}
import io.lamma.Month.{February, January}
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

    "work with nth day" in {
      val expected = Locator(Ordinal(10))
      10 th day should be(expected)
    }

//    "work with last day of month" in {
//      val expected = Locator(Last)
//      val actual: Locator = last(day)
//      actual should be(expected)
//    }

    "work with nth weekday" in {
      val expected = Locator(Ordinal(2), Some(Friday))
      2 nd Friday should be(expected)
    }

//    "work with last weekday of month" in {
//      val expected = Locator(Last, Some(Friday))
//      val actual = last Friday
//      actual should be(expected)
//    }

    "work with nth day of month" in {
      val expected = Locator(Ordinal(10), month = Some(February))
      10 th day of February should be(expected)
    }

    "work with nth weekday of month" in {
      val expected = Locator(Ordinal(3), Some(Friday), Some(February))
      3 rd Friday of February should be(expected)
    }
  }
}
