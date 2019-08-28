package com.example

object S9 {

  /**
    * 重複している要素ごとにリストにまとめる
    */
  def pack[A](ls: List[A]): List[List[A]] = {
    if (ls.isEmpty) {
      List(List())
    } else {
      val (packed, next) = ls span {
        _ == ls.head
      }
      if (next == Nil) {
        List(packed)
      } else {
        packed :: pack(next)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    assert(
      pack(List(1, 2, 2, 2, 3, 3, 4, 5, 4, 1, 1, 1)) ==
        List(List(1), List(2, 2, 2), List(3, 3), List(4), List(5), List(4), List(1, 1, 1))
    )
  }
}