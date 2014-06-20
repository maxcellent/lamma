package io.lamma

/**
 * relative to which anchor date?
 */
sealed trait Anchor

object Anchor {

  def apply(name: String) = OtherDate(name)

  case object PeriodStart extends Anchor

  case object PeriodEnd extends Anchor

  case class OtherDate(name: String) extends Anchor
}
