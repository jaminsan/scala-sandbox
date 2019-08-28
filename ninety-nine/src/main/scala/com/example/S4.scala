package com.example

object S4 {

  /**
    * リストの長さを取得する
    */
  def length[A](l: List[A]): Int = {
    def lengthR(count: Int, l: List[A]): Int = l match {
      // case Nil を入れてしまえばあえて case _ で 0 を返すケースを入れずに済む
      case Nil => count
      case _ :: tail => lengthR(count + 1, tail)
    }
    lengthR(0, l)
  }

  def main(args: Array[String]): Unit = {
    val l = List(1, 1, 2, 3, 5, 8)
    assert(length(l) == 5)
  }
}
