package com.lamma4s

sealed trait StubRule

case class LongStub(max: Int) extends StubRule

case class ShortStub(min: Int) extends StubRule
