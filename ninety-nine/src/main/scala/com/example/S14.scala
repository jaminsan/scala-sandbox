package com.example

object S14 {

  /**
    * リストの各要素を複製した新しいリストを返す
    */
  def duplicate[A](ls: List[A]): List[A] = ls.flatMap(n => List(n, n))

  def main(args: Array[String]): Unit = {
    assert(
      duplicate(List(1, 2, 3, 4, 5)) ==
        List(1, 1, 2, 2, 3, 3, 4, 4, 5, 5)
      )
  }
}