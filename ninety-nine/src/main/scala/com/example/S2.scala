package com.example

object S2 {

  /**
    * リストの最後から一つ前の要素を取得する
    */
  def penultimate[A](l: List[A]): A = l match {
    case h :: _ :: Nil => h
    case _ :: tail => penultimate(tail)
    case _ => throw new NoSuchElementException
  }

  def main(args: Array[String]): Unit = {
    val l = List(1, 1, 2, 3, 5, 8)
    assert(penultimate(l) == 5)
  }

}
