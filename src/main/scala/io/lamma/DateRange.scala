package io.lamma

import annotation.tailrec

case class DateRange(from: Date, to: Date) extends Traversable[Date] {
  override def foreach[U](f: Date => U) = DateRange.eachDate(f, from, to)
}

object DateRange {
  @tailrec
  def eachDate[U](f: Date => U, current: Date, to: Date): Unit = {
    if (current <= to) {
      f(current)
      eachDate(f, current + 1, to)
    }
  }
}