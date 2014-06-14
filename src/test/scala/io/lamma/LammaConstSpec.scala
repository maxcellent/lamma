package io.lamma

import org.scalatest.{Matchers, FlatSpec}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import LammaConst._
import io.lamma.Month._

@RunWith(classOf[JUnitRunner])
class LammaConstSpec extends FlatSpec with Matchers {

   "DateOfWeek" should "match lamma version" in {
     MONDAY should be(Monday)
     TUESDAY should be(Tuesday)
     WEDNESDAY should be(Wednesday)
     THURSDAY should be(Thursday)
     FRIDAY should be(Friday)
     SATURDAY should be(Saturday)
     SUNDAY should be(Sunday)
   }

   "Months" should "match lamma version" in {
     JANUARY should be(January)
     FEBRUARY should be(February)
     MARCH should be(March)
     APRIL should be(April)
     MAY should be(May)
     JUNE should be(June)
     JULY should be(July)
     AUGUST should be(August)
     SEPTEMBER should be(September)
     OCTOBER should be(October)
     NOVEMBER should be(November)
     DECEMBER should be(December)
   }
 }
