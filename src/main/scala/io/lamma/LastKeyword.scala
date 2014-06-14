package io.lamma

import io.lamma.Locator.Last

case class LastKeyword() {
  def day = new Locator(Last)

  def apply(dow: DayOfWeek) = Locator(Last, Some(dow))
}
