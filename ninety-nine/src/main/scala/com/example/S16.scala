package com.example

object S16 {

  /**
    * リストの要素から N の倍数番目の要素を除いたリストを返す
    * N > 1
    */
  def drop[A](n: Int, ls: List[A]): List[A] = {
    def dropR(n: Int, ls: List[A], acc: List[A]): List[A] = ls match {
      case _ if ls.length < n => acc ::: ls
      case _ => dropR(n, ls.takeRight(ls.length - n), acc ::: ls.take(n - 1))
    }

    dropR(n, ls, List[A]())
  }

  def main(args: Array[String]): Unit = {
    assert(
      drop(3, List(1, 2, 3, 4, 5, 6)) ==
        List(1, 2, 4, 5)
    )

    assert(
      drop(3, List(1, 2, 3, 4)) ==
        List(1, 2, 4)
    )
  }
}