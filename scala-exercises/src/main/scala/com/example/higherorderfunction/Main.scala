package com.example.higherorderfunction

object Main {
  def id(x: Int): Int = x

  def sumInts(a: Int, b: Int) = sum(a, b)(id)

  def sum(a: Int, b: Int)(f: Int => Int): Int =
    if (a > b) 0
    else f(a) + sum(a + 1, b)(f)

  // sum をカリー化することでブロックで関数を渡せる
  sum(1, 0) { number =>
    number * 2
  }
}