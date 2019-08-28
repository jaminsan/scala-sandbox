package com.example

object S15 {

  /**
    * リストの各要素を、指定した数分だけ複製した新しいリストを返す
    */
  def duplicateN[A](n: Int, ls: List[A]): List[A] =
    ls.flatMap(e => List.fill(n)(e))

  def main(args: Array[String]): Unit = {
    assert(
      duplicateN(3, List(1, 2, 3, 4, 5)) ==
        List(1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5)
    )

    assert(
      duplicateN(1, List(1, 2, 3, 4, 5)) ==
        List(1, 2, 3, 4, 5)
    )
  }
}