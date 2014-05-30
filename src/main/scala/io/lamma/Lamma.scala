package io.lamma

object Lamma {

  def generateSchedule(start: Date,
               end: Date,
               frequency: Frequency,
               startRule: StartRule = NoStartRule,
               endRule: EndRule = NoEndRule,
               dateDefs: List[DateDef]) = {
    DateDef.validate(dateDefs)

    val dates = frequency.generate(start, end)

    val periods = {
      val p = Period.fromDates(dates)
      val withStartRule = startRule.applyRule(start, p)
      endRule.applyRule(end, withStartRule)
    }

    Schedule(periods, dateDefs)
  }
}
