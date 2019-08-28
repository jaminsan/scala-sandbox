package com.example

object S1 {

  /**
    * リストの最後の要素を取得する
    */
  def last[A](l: List[A]): A = l match {
    case h :: Nil => h
    case _ :: tail => last(tail)
    case _ => throw new NoSuchElementException
  }

  def main(args: Array[String]): Unit = {
    val l = List(1, 1, 2, 3, 5, 8)
    // scalaStyle で println があったら警告出すようにしてる
    // 本番コードに紛れ込んでたら困るから
    assert(last(l) == 8)
  }

}