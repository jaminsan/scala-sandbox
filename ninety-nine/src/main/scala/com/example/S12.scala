package com.example

object S12 {

  /**
    * S10 の結果を decode する
    */
  def decode[A](ls: List[(Int, A)]): List[A] = ls.flatMap { t =>
    (1 to t._1).foldLeft(List[A]())((x, y) => t._2 :: x)
  }

  def main(args: Array[String]): Unit = {
    assert(
      decode(List((1, 1), (3, 2), (2, 3), (1, 4), (1, 5), (1, 4), (3, 1))) ==
        List(1, 2, 2, 2, 3, 3, 4, 5, 4, 1, 1, 1)
    )
  }
}