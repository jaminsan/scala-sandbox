package com.example.datastructures

import scala.annotation.tailrec

sealed trait List[+A]

case object Nil extends List[Nothing]

case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {
  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x, xs) => x * product(xs)
  }

  def length[A](as: List[A]): Int =
    foldRight(as, 0)((_, acc) => acc + 1)

  def tail[A](l: List[A]): List[A] = l match {
    case Nil => Nil
    case Cons(_, t) => t
  }

  def setHead[A](l: List[A], a: A): List[A] = l match {
    case Nil => sys.error("try setting element to empty list")
    case Cons(_, t) => Cons(a, t)
  }

  def drop[A](l: List[A], n: Int): List[A] = {
    def dropR(l: List[A], c: Int): List[A] =
      (c, l) match {
        case (0, _l) => _l
        case (_, Nil) => sys.error("drop on empty list, length of list may lower than n")
        case (_c, Cons(_, t)) => dropR(t, _c - 1)
      }

    dropR(l, n)
  }

  def dropWhile[A](l: List[A], f: A => Boolean): List[A] =
    l match {
      case Nil => Nil
      case Cons(h, t) if f(h) => dropWhile(t, f)
      case _ => l
    }

  def init[A](l: List[A]): List[A] = l match {
    case Nil => Nil
    case Cons(_, Nil) => Nil
    case Cons(h, t) => Cons(h, init(t))
  }

  def foldRight[A, B](as: List[A], z: B)(f: (A, B) => B): B =
    as match {
      case Nil => z
      case Cons(x, xs) => f(x, foldRight(xs, z)(f))
    }

  @tailrec
  def foldLeft[A, B](as: List[A], z: B)(f: (B, A) => B): B =
    as match {
      case Nil => z
      case Cons(h, t) => foldLeft(t, f(z, h))(f)
    }

  def sum2(as: List[Int]): Int =
    foldLeft(as, 0)(_ + _)

  def product2(as: List[Double]): Double =
    foldLeft(as, 1.0)(_ * _)

  def length2[A](as: List[A]): Int =
    foldLeft(as, 0)((acc, _) => acc + 1)

  def reverse[A](as: List[A]): List[A] =
    foldLeft(as, List[A]())((acc, h) => Cons(h, acc))

  def append[A](as: List[A], e: A): List[A] =
    foldRight(as, List(e))(Cons(_, _))

  def append[A](l: List[A], r: List[A]): List[A] =
    foldRight(l, r)(Cons(_, _))

  def concat[A](ls: List[List[A]]): List[A] =
    foldRight(ls, List[A]())(append)

  def add1(ints: List[Int]): List[Int] =
    foldRight(ints, Nil: List[Int])((n, acc) => Cons(n + 1, acc))

  def double2Sting(ds: List[Double]): List[String] =
    foldRight(ds, Nil: List[String])((h, acc) => Cons(h.toString, acc))

  def map[A, B](as: List[A])(f: A => B): List[B] =
    foldRight(as, Nil: List[B])((h, acc) => Cons(f(h), acc))

  def filter[A](as: List[A])(f: A => Boolean): List[A] =
    foldRight(as, Nil: List[A])((h, acc) => if (f(h)) Cons(h, acc) else acc)

  def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] =
    concat(map(as)(f))

  def filterViaFlatMap[A](as: List[A])(f: A => Boolean): List[A] =
    flatMap(as)(n => if (f(n)) List(n) else Nil)

  def zip[A, B](l: List[A], r: List[B]): List[(A, B)] =
    (l, r) match {
      case (Nil, _) => Nil
      case (_, Nil) => Nil
      case (Cons(lh, lt), Cons(rh, rt)) => Cons((lh, rh), zip(lt, rt))
    }

  //  def zipWith[A, B, C](l: List[A], r: List[B])(f: ((A, B)) => C): List[C] =
  //    map(zip(l, r))(f)

  def zipWith[A, B, C](l: List[A], r: List[B])(f: (A, B) => C): List[C] =
    (l, r) match {
      case (Nil, _) => Nil
      case (_, Nil) => Nil
      case (Cons(lh, lt), Cons(rh, rt)) => Cons(f(lh, rh), zipWith(lt, rt)(f))
    }

  def zipThenAdd(l: List[Int], r: List[Int]): List[Int] =
    zipWith(l, r)((a, b) => a + b)

  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
}
