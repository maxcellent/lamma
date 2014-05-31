package io.lamma

/**
 * relative to which anchor date?
 */
sealed trait Anchor

object Anchor {
  case object PeriodStart extends Anchor

  case object PeriodEnd extends Anchor

  case class OtherDateDef(name: String) extends Anchor
}
