package com.nikitin

import com.nikitin.list.{EmptyList, LinkedList}
import com.nikitin.tree.{Leaf, Node}

object Main extends App {

case class Entry(value: Int,  weight: Int)
  val list = LinkedList[Entry](Entry(1,10)).add(Entry(4,40)).add(Entry(6,60)).add(Entry(8,60)).add(Entry(1,60))

  implicit val entryComparator = (a:Entry,b:Entry) => if(a.value > b.value) 1 else if (a.value < b.value) -1 else 0
  println(s"List: $list")
  println("Sorted: " + list.sorted)

  val leafList = LinkedList(Leaf(2,20)).add(Leaf(4,40)).add(Leaf(3,30)).add(Leaf(1,10)).add(EmptyList)
  val leafList2 = LinkedList(Leaf(2,20)).add(Leaf(4,40)).add(Leaf(3,30)).add(Leaf(1,10)).add(Leaf(3,10)).add(Leaf(2,10)).add(Leaf(2,10)).add(EmptyList)
  val nodeList = LinkedList(Node()).add(Node()).add(Node().addNode(Node().addNode(Node())))

  val tree = Node(leafList2, nodeList)
  println(tree)

  implicit val W = 3
  val balancedTree = tree.balance

  println(balancedTree)
}
