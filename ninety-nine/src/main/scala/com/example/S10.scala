package com.example

object S10 {

  /**
    * 連続している同じ要素とその数をタプルとして持ったリストを返す
    */
  def encode[A](ls: List[A]): List[(Int, A)] = S9.pack(ls).map { packed => (packed.length, packed.head) }

  def main(args: Array[String]): Unit = {
    assert(
      encode(List(1, 2, 2, 2, 3, 3, 4, 5, 4, 1, 1, 1)) ==
        List((1, 1), (3, 2), (2, 3), (1, 4), (1, 5), (1, 4), (3, 1))
    )
  }
}