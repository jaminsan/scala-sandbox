package com.example.monoids

import scala.language.reflectiveCalls

case class State[S, A](run: S => (A, S)) {

  def map[B](f: A => B): State[S, B] =
    State(s => {
      val (a, s1) = run(s)
      (f(a), s1)
    })

  def flatMap[B](f: A => State[S, B]): State[S, B] =
    State(s => {
      val (a, s1) = run(s)
      f(a).run(s1)
    })
}

object State {

  type IntState[A] = State[Int, A]

  object IntStateMonad extends Monad[IntState] {
    def unit[A](a: => A): IntState[A] = State(s => (a, s))

    override def flatMap[A, B](st: IntState[A])(f: A => IntState[B]): IntState[B] =
      st flatMap f
  }

  //  こういう書き方もできるらしい
  //  type lambda とか聞いたことがあるかも（インラインで宣言された型コンストラクタのことをいうらしい
  //  無名の型を括弧で囲んで # でアクセスしている
  //  Scala の言語仕様では # を使って型メンバーにアクセスできるらしい、、、type projection というみたい
  //  object IntStateMonad2 extends Monad[({type IntState[A] = State[Int, A]})#IntState] {
  //    override def unit[A](a: => A): State[Int, A] = ???
  //  }

  // A の型ごとに毎度インスタンスを作ってられないので汎用的なものを定義
  def stateMonad[S]: Monad[State[S, _]] = new Monad[({type f[x] = State[S, x]})#f] {
    override def unit[A](a: => A): State[S, A] = State(s => (a, s))
    override def flatMap[A, B](ma: State[S, A])(f: A => State[S, B]): State[S, B] = ma flatMap f
  }

  // 最小限のプリミティブによって、あるデータ型を使ってできることが完全に指定される
  // モナド全般に当てはまることとして、全てのモナドで unit と flatMap が提供され、モナドごとに独自の追加プリミティブが提供される

  // モナドの規約は、(flatMap による)行間で何が起きるかを指定するのではなく、何が起きようともそれが結合律と同一律を満たすことを明示するだけ

  // 命令型プログラムを純粋関数方式で記述できる！というのがモナドの利点！（命令型プログラムって、手続き型ってこと？
  // 確かに for 内包表記は変数へのバインド（値と書くべきか）とそれらを使って別の操作をするっていう命令型のコードと酷似しているようにも見える
  // 何が起きるのか、はそのコンテキストによる、ということではないのか？？？
}