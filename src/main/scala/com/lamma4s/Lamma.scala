package com.lamma4s

object Lamma {

  def generate(start: Date,
               end: Date,
               frequency: Frequency,
               startRule: StartRule = NoStartRule,
               endRule: EndRule = NoEndRule,
               patterns: List[Pattern]) = {

    val dates = frequency.generate(start, end)

    val periods = Period.fromDates(dates)

    val withStartRule = startRule.applyRule(start, periods)

    val withEndRule = endRule.applyRule(end, withStartRule)
  }
}
