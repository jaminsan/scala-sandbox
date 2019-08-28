package com.example

object S8 {

  /**
    * 連続した要素を削除した新しいリストを返す
    */
  def compress[A](ls: List[A]): List[A] = {
    def compressR(acc: List[A], current: A, l: List[A]): List[A] = l match {
      case Nil => acc.reverse
      case h :: tail if h != current => compressR(h :: acc, h, tail)
      case h :: tail if h == current => compressR(acc, h, tail)
    }

    compressR(List[A](ls.head), ls.head, ls.tail)
  }

  def main(args: Array[String]): Unit = {
    assert(
      compress(List(1, 2, 2, 2, 3, 3, 4, 5, 4, 1, 1, 1)) == List(1, 2, 3, 4, 5, 4, 1)
    )
    assert(
      compress(List(1)) == List(1)
    )
    // これが落ちてしまう
    assert(
      compress(List()) == List()
    )
  }
}