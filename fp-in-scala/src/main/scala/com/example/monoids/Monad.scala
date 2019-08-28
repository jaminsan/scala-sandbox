package com.example.monoids

import language.higherKinds

trait Monad[F[_]] extends Functor[F] {

  // unit は compose の単位元
  def unit[A](a: => A): F[A]

  // 型合わせするだけでかけちゃうけど、スタックセーフかどうかみたいな奴はイマイチ想像しづらい気がする
  def compose[A, B, C](f: A => F[B], g: B => F[C]): A => F[C] =
//    a => flatMap(f(a))(g)
  a => join(map(f(a))(g))

  def map[A, B](ma: F[A])(f: A => B): F[B] =
    flatMap(ma)(a => unit(f(a)))

  def map2[A, B, C](ma: F[A], mb: F[B])(f: (A, B) => C): F[C] =
    flatMap(ma)(a => map(mb)(b => f(a, b)))

  // A の型を Unit として A => F[C] の A に Unit を入れて kleisli を実行している
  // (()) は引数に Unit を渡している、ウケる
  def flatMap[A, B](ma: F[A])(f: A => F[B]): F[B] =
//    compose((_:Unit) => ma, f)(())
  join(map(ma)(f))

  def join[A](mma: F[F[A]]): F[A] =
    flatMap(mma)(ma => ma)
}

// Monad のインスタンスは以下のいずれかの実装を提供しなければいけない
// ずっと flatMap と unit だと思ってた、、、
// 入れ替えることによる違いとかあるのか？
// ・unit、flatMap
// ・unit、compose
// ・unit、map、join

// モナドとは、結合律と同一律を満たす最小限のモナドコンビネータの集まりのいずれかを実装したものである
// （これだけでは、モナドが何を意味するのかについて明白ではない）