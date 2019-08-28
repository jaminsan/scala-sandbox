package com.example

object S7 {

  /**
    * List もしくは一つの要素を任意の個数受け取って結合する
    */
  def flatten[A](ls: List[A]): List[A] = ls flatMap {
    // 型注釈つけるだけで型で case の条件判定できるのか！
    case ms: List[A @unchecked] => flatten(ms)
    case e => List(e)
  }

  def main(args: Array[String]): Unit = {
    assert(flatten(List(List(1, 1), 2, List(3, List(5, 8)))) == List(1, 1, 2, 3, 5, 8))
  }
}
