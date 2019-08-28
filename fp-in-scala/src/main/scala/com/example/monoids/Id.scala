package com.example.monoids

case class Id[A](value: A) {

  def map[B](f: A => B): Id[B] =
    Id(f(value))

  def flatMap[B](f: A => Id[B]): Id[B] =
    f(value)
}

object Id {

  val idMonad: Monad[Id] = new Monad[Id] {
    def unit[A](a: => A): Id[A] = Id(a)
    override def flatMap[A, B](ma: Id[A])(f: A => Id[B]): Id[B] = ma flatMap f
  }
}

// 「モナドは、変数を定義してバインドするためのコンテキストを提供し、変数置換を実行する」と言える