package com.example

object S13 {

  /**
    * S10 の結果を decode する
    */
  def encodeDirect[A](ls: List[A]): List[(Int, A)] = {
    if (ls.isEmpty) {
      Nil
    } else {
      val (packed, next) = ls span (_ == ls.head)
      (packed.length, packed.head) :: encodeDirect(next)
    }
  }

  def main(args: Array[String]): Unit = {
    assert(
      encodeDirect(List(1, 2, 2, 2, 3, 3, 4, 5, 4, 1, 1, 1)) ==
        List((1, 1), (3, 2), (2, 3), (1, 4), (1, 5), (1, 4), (3, 1))
    )
  }
}