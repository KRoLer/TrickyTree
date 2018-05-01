package com.nikitin.tree

import com.nikitin.list.{EmptyList, LinkedList, List}

import scala.annotation.tailrec
import scala.util.Random

case class Leaf(weight: Int, value: Int)

case class Node(leafs: List[Leaf], nodes: List[Node]) {

  def addNode(node: Node): Node = this.copy(leafs,  nodes.add(node))
  def addLeaf(leaf: Leaf): Node = this.copy(leafs.add(leaf), nodes)
  def addLeaf(leaf: List[Leaf]): Node = this.copy(leafs.add(leaf), nodes)

  def balance(implicit limit:Int): Node = {
    implicit val entryComparator = (a: Leaf, b: Leaf) => if(a.weight > b.weight) 1 else if (a.weight < b.weight) -1 else 0

    val sortedLeafs = leafs.sorted
    adjustLeafsForNode(sortedLeafs) match {
      case (leaveHere: LinkedList[Leaf], EmptyList) => Node(leaveHere, nodes)
      case (leaveHere: LinkedList[Leaf], toMove: LinkedList[Leaf]) => {
        if(!nodes.isEmpty) {
          val updatedNode = nodes.head.addLeaf(toMove).balance
          Node(leaveHere, nodes.tail.add(updatedNode))
        } else {
          Node(leaveHere, nodes)
        }
      }
      case (EmptyList, toMove: LinkedList[Leaf]) => {
        if(!nodes.isEmpty) {
          val updatedNode = nodes.head.addLeaf(toMove).balance
          Node(EmptyList, nodes.tail.add(updatedNode))
        } else {
          Node(EmptyList, nodes)
        }
      }
    }
  }

  private def adjustLeafsForNode(leafs: List[Leaf])(implicit limit: Int):(List[Leaf], List[Leaf]) = {

    @tailrec
    def loop(list: List[Leaf], leave: List[Leaf], move: List[Leaf], space: Int): (List[Leaf], List[Leaf]) = list match {
      case LinkedList(h:Leaf,t) if (space - h.weight >= 0) => loop(t, leave.add(h), move, space - h.weight)
      case LinkedList(h:Leaf,t) => loop(t, leave, move.add(h), space)
      case _ => (leave, move)
    }

    loop(leafs, EmptyList, EmptyList, limit)
  }

  override def toString: String = Random.nextInt(4) match {
    case 0 => s"${Console.RED} Node([$leafs], [$nodes${Console.RED}])"
    case 1 => s"${Console.BLUE} Node([$leafs], [$nodes${Console.BLUE}])"
    case 2 => s"${Console.YELLOW} Node([$leafs], [$nodes${Console.YELLOW}])"
    case 3 => s"${Console.GREEN} Node([$leafs], [$nodes${Console.GREEN}])"
    case 4 => s"${Console.CYAN} Node([$leafs], [$nodes${Console.CYAN}])"
  }
}

object Node {
  def apply(): Node = new Node(EmptyList, EmptyList)
}