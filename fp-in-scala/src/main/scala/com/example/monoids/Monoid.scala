package com.example.monoids

trait Monoid[A] {
  def op(x: A, y: A): A
  def zero: A

  // op(op(x, y), z) == op(x, op(y, z))

  // op(x, zero) == x
  // op(zero, x) == x
}

object Monoid {

  val stringMonoid: Monoid[String] = new Monoid[String] {
    def op(x: String, y: String) = x + y
    def zero = ""
  }

  def listMonoid[A]: Monoid[List[A]] = new Monoid[List[A]] {
    def op(x: List[A], y: List[A]) = x ++ y
    def zero = Nil
  }

  def intAddition: Monoid[Int] = new Monoid[Int] {
    def op(x: Int, y: Int) = x + y
    def zero = 0
  }

  def intMultiplication: Monoid[Int] = new Monoid[Int] {
    def op(x: Int, y: Int) = x * y
    def zero = 1
  }

  def booleanOr: Monoid[Boolean] = new Monoid[Boolean] {
    def op(x: Boolean, y: Boolean) = x || y
    def zero: Boolean = false
  }

  def booleanAnd: Monoid[Boolean] = new Monoid[Boolean] {
    def op(x: Boolean, y: Boolean) = x && y
    def zero = true
  }
  
  def optionMonoid[A]: Monoid[Option[A]] = new Monoid[Option[A]] {
    def op(x: Option[A], y: Option[A]) = x orElse y
    def zero = None
  }

  // 単純な法則だけど満たすように考えるのは意外と難しいなぁ、、、
  // compose と andThen で何が違うの？って思ってしまう
  def endoMonoid[A]: Monoid[A => A] = new Monoid[A => A] {
    def op(x: A => A, y: A => A): A => A = x compose y
    def zero: A => A = (a: A) => a
  }

  // foldMap って map しながら fold するという意味か
  def foldMap[A, B](as: List[A], m: Monoid[B])(f: A => B): B =
    as.foldLeft(m.zero)((b ,a) => m.op(b, f(a)))
}

// map は構造を維持する

// モナドの結合律はクライスリ合成で証明できる