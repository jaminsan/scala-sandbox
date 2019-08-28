package com.example

object S11 {

  /**
    * 連続している同じ要素とその数をタプルとして持ったリストを返す. ただし、連続する要素がない場合は要素のみを持つ.
    */
  def encode[A](ls: List[A]): List[Either[A, (Int, A)]] =
    S10.encode(ls)
      .map(t =>
        if (t._1 == 1) Left(t._2) else Right(t)
      )

  def main(args: Array[String]): Unit = {
    assert(
      encode(List(1, 2, 2, 2, 3, 3, 4, 5, 4, 1, 1, 1)) ==
        List(Left(1), Right((3, 2)), Right((2, 3)), Left(4), Left(5), Left(4), Right((3, 1)))
    )
  }
}