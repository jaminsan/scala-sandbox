package com.example

object S6 {

  /**
    * 回文かどうかを判定する
    */
  def isPalindrome[A](l: List[A]): Boolean =
    l == S5.reverse(l)

  def main(args: Array[String]): Unit = {
    assert(isPalindrome(List(1, 2, 3, 2, 1)))
    assert(isPalindrome(List(1, 2, 1, 1, 2, 1)))
    assert(isPalindrome(List(1, 2, 3, 2, 2, 1)) == false)
  }
}
