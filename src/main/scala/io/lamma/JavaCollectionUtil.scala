package io.lamma

import java.util.{List => JList, Set => JSet}

import scala.collection.JavaConverters._

/**
 * all operations interop between java collection and scala collection are maintained here
 */
private[lamma] object JavaCollectionUtil {

  /**
   * convert java array to scala seq, so that it will have rich collection API
   */
  def asScalaSeq[E](it: Array[E]) = it.toSeq

  // ========= list conversion ======
  def asScala[E](l: JList[E]) = l.asScala.toList

  def asJava[E](l: List[E]) = l.asJava

  // ========= set conversion =======
  def asScala[E](s: JSet[E]) = s.asScala.toSet

  def asJava[E](s: Set[E]) = s.asJava
}
