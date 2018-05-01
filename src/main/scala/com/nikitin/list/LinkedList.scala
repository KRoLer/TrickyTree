package com.nikitin.list

import scala.annotation.tailrec

sealed trait List[+T] {
  def head: T
  def tail: List[T]
  def isEmpty: Boolean
  def add[A >: T] (value:A): List[A]
  def add[A >: T] (list: List[A]):List[A]
  def length: Int
  def splitAt(n: Int): (List[T], List[T])
  def reverse: List[T]
  def sorted[A >: T](implicit compare: (A,A) => Int): List[A]
}

case class LinkedList[+T] (override val head: T, override val tail: List[T]) extends List [T] {
  override def isEmpty: Boolean = false

  override def add[A >: T](value: A):List[A] = LinkedList[A](value, this)

  override def add[A >: T](list: List[A]):List[A] = {
    @tailrec
    def loop(origin: List[A], result: List[A]): List[A] = origin match {
      case LinkedList(head, tail) => loop(tail, result.add(head))
      case EmptyList => result
    }

    loop(this, list)
  }

  override def length: Int = {
    @tailrec
    def loop(node: List[T], length: Int): Int = {
      if(node.isEmpty) length
      else loop(node.tail, length + 1)
    }

    loop(this.tail, 1)
  }

  override def splitAt(n: Int): (List[T], List[T]) = {
    require(n > 0, "Index can't be less than 0")

    @tailrec
    def loop[T](node: List[T], position: Int, left: List[T]): (List[T], List[T]) = {

      if(position <= 1) (left.add(node.head).reverse, node.tail)
      else loop(node.tail, position - 1, left.add(node.head))
    }

    loop(this, n, EmptyList)
  }

  override def reverse: List[T] = {

    @tailrec
    def loop(node: List[T], result:List[T]): List[T] ={
      if(node.isEmpty) result
      else loop(node.tail, result.add(node.head))
    }

    loop(this, EmptyList)
  }

  override def toString: String = s"$head $tail"

  override def sorted[A >: T](implicit compare: (A,A) => Int): List[A] = mergeSort(this)

  private def mergeSort[A >: T](list: List[A])(implicit compare: (A,A) => Int): List[A] = {
    val middle = list.length / 2
    if(middle == 0 ) list
    else {
      def merge(l1: List[A], l2: List[A]): List[A] = (l1, l2) match {
        case (EmptyList, l2) => l2
        case (l1, EmptyList) => l1
        case (LinkedList(h1, t1), LinkedList(h2, t2)) =>
          if(compare(h1, h2) == -1) LinkedList(h1).add(merge(t1, l2))
          else LinkedList(h2).add(merge(l1, t2))
      }
      val (left, right) = list.splitAt(middle)
      merge(mergeSort(left), mergeSort(right))
    }
  }
}

object LinkedList {

  def apply[T](value: T): List[T] = LinkedList[T](value, EmptyList)
  def unapply[T](linkedList: List[T]): Option[(T, List[T])] = linkedList match {
    case EmptyList => None
    case _ => Some((linkedList.head, linkedList.tail))
  }
}

case object EmptyList extends List[Nothing] {
  override def isEmpty: Boolean = true
  override def add[A >: Nothing](value: A):List[A] = LinkedList(value)
  override def add[A >: Nothing](list: List[A]): List[A] = list

  override def head: Nothing = throw new NoSuchElementException("The list is empty.")
  override def tail: List[Nothing] = throw new UnsupportedOperationException("No tail for empty list.")
  override def length: Int = 0
  override def splitAt(n: Int): (List[Nothing], List[Nothing]) = throw new UnsupportedOperationException("Can't split empty list")
  override def toString: String = ""
  override def reverse: List[Nothing] = throw new UnsupportedOperationException("Can't reverse empty list")
  override def sorted[A >: Nothing](implicit compare: (A, A) => Int): List[A] = throw new UnsupportedOperationException("Can't sort empty list.")
}