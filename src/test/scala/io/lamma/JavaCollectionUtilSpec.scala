package io.lamma

import com.google.common.collect.{Lists, Sets}
import io.lamma.JavaCollectionUtil._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class JavaCollectionUtilSpec extends FlatSpec with Matchers {

  "asScalaSeq" should "convert from java array to scala seq" in {
    val input = Array(1, 2, 3, 4, 5)
    val expected = Seq(1, 2, 3, 4, 5)
    asScalaSeq(input) should be(expected)
  }

  val javaList = Lists.newArrayList(1, 2, 3, 4, 5)
  val scalaList = List(1, 2, 3, 4, 5)

  "asScala" should "convert from java list to scala list" in {
    asScala(javaList) should be(scalaList)
  }

  "asJava" should "convert from scala list to java list" in {
    asJava(scalaList) should be(javaList)
  }

  val javaSet = Sets.newHashSet(1, 2, 3, 4, 5)
  val scalaSet = Set(1, 2, 3, 4, 5)

  "asScala" should "convert from java set to scala set" in {
    asScala(javaSet) should be(scalaSet)
  }

  "asJava" should "convert from scala set to java set" in {
    asJava(scalaSet) should be(javaSet)
  }
}
