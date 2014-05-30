package io.lamma

case class DateDef(name: String,
                   relativeTo: Anchor = PeriodEnd,
                   shifter: ShiftRule = NoShift,
                   selector: SelectionRule = SameDay,
                   cal: HolidayCalendar = WeekendHolidayCalendar) {

  /**
   * @param period
   * @param populated a map of already populated dates
   * @return
   */
  def populate(period: Period, populated: Map[String, Date] = Map.empty) = {
    val anchorDate = relativeTo match {
      case PeriodStart => period.from
      case PeriodEnd => period.to
      case OtherDateDef(name) => populated(name)
    }

    val shiftedDate = shifter match {
      case NoShift => anchorDate
      case FutureDay(n) => anchorDate + n
      case PastDay(n) => anchorDate - n
      case FutureBizDay(n) => cal.shiftBizDay(anchorDate, n)
      case PastBizDay(n) => cal.shiftBizDay(anchorDate, -n)
    }

    selector match {
      case SameDay => shiftedDate
      case Forward => cal.forward(shiftedDate)
      case Backward => cal.backward(shiftedDate)
      case ModifiedFollowing => cal.modifiedFollowing(shiftedDate)
      case ModifiedPreceding => cal.modifiedPreceding(shiftedDate)
    }
  }
}

object DateDef {
  def validate(defs: List[DateDef]) {
    val names = defs.map(_.name)
    require(names.size == names.toSet.size, s"Name of DateDef should be unique. Defined names: ${names.mkString(",")}")

    // validate OtherDateDef position
    (Set.empty[String] /: defs) {
      (availableName, dateDef) =>
        dateDef.relativeTo match {
          case OtherDateDef(name) =>
            if (! availableName.contains(name)) {
              if (names.contains(name)) {
                throw new IllegalArgumentException(s"$dateDef should come after anchor date $name")
              } else {
                throw new IllegalArgumentException(s"$dateDef is referencing to an non-exist anchor date $name")
              }
            }
          case _ =>
        }
        availableName + dateDef.name
    }
  }
}

/**
 * relative to which anchor date?
 */
sealed trait Anchor

case object PeriodStart extends Anchor

case object PeriodEnd extends Anchor

case class OtherDateDef(name: String) extends Anchor

/**
 * how we are going to shift based on the position
 */
sealed trait ShiftRule

case object NoShift extends ShiftRule

case class FutureDay(n: Int) extends ShiftRule {
  require(n > 0)
}

case class PastDay(n: Int) extends ShiftRule {
  require(n > 0)
}

case class FutureBizDay(n: Int) extends ShiftRule {
  require(n > 0)
}

case class PastBizDay(n: Int) extends ShiftRule {
  require(n > 0)
}

/**
 * once we shifted, how we are going to select the date to use
 */
sealed trait SelectionRule

case object SameDay extends SelectionRule

/**
 * if current day not a working day,
 * then move forward to the next working day
 */
case object Forward extends SelectionRule

/**
 * if current day not a working day,
 * then move backward to the previous working day
 */
case object Backward extends SelectionRule

/**
 * the next working day unless that working day crosses over a new month
 */
case object ModifiedFollowing extends SelectionRule

/**
 * previous working day unless that working day crosses over a new month
 */
case object ModifiedPreceding extends SelectionRule
