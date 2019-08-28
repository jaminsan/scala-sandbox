package com.example

object S5 {

  /**
    * リストの要素を反転させる
    */
  def reverse[A](l: List[A]): List[A] = {
    l.foldLeft(List[A]())((x, y) => y :: x)
  }

  def main(args: Array[String]): Unit = {
    val l = List(1, 1, 2, 3, 5, 8)
    assert(reverse(l) == List(8, 5, 3, 2, 1, 1))
  }
}
