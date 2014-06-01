package io.lamma

/**
 * build period based on schedule start / end date and period end days
 */
trait PeriodBuilder {

  /**
   * @param start schedule start date
   * @param end schedule end date
   * @param periodEnds period end days generated from Recurrence pattern
   * @return
   */
  def build(start: Date, end: Date, periodEnds: List[Date]): List[Period]

}

