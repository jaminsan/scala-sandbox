package com.example

//object S17 {
//
//  /**
//    * N 番目以前の要素とそれ以降の要素に分けた二つのリストを返す
//    */
//  def split[A](n: Int, ls: List[A]): (List[A], List[A]) = {
//    def splitR(count: Int, ls: List[A], acc: List[A]): (List[A], List[A]) = (count, ls) match {
//      case (0, _) => (acc.reverse, ls)
//      case (_, h :: Nil) => ((h :: acc).reverse, List[A]())
//      case (_, h :: tail) => splitR(count - 1, tail, h :: acc)
//    }
//
//    splitR(n, ls, List[A]())
//  }
//
//  def main(args: Array[String]): Unit = {
//    assert(
//      split(3, List(1, 2, 3, 4, 5, 6)) == ((List(1, 2, 3), List(4, 5, 6)))
//    )
//
//    assert(
//      split(3, List(1, 2, 3, 4)) == ((List(1, 2, 3), List(4)))
//    )
//
//    assert(
//      split(3, List(1, 2, 3)) == ((List(1, 2, 3), List()))
//    )
//  }
//}