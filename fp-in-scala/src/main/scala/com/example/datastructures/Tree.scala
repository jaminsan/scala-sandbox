package com.example.datastructures

sealed trait Tree[+A]

case class Leaf[A](value: A) extends Tree[A]

case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree {
  def size[A](t: Tree[A]): Int =
    t match {
      case _: Leaf[A] => 1
      case Branch(l, r) => 1 + size(l) + size(r)
    }

  def maximum(t: Tree[Int]): Int = {
    def maximumR(t: Tree[Int], m: Int): Int =
      t match {
        case Leaf(v) => m max v
        case Branch(l, r) => maximumR(l, m) max maximumR(r, m)
      }

    maximumR(t, Int.MinValue)
  }

  def depth[A](t: Tree[A]): Int =
    t match {
      case Branch(_: Leaf[A], r: Branch[A]) => 1 + depth(r)
      case Branch(l: Branch[A], _: Leaf[A]) => 1 + depth(l)
      case Branch(l: Branch[A], r: Branch[A]) => 1 + (depth(l) max depth(r))
      case Branch(l: Leaf[A], _: Leaf[A]) => 1 + depth(l)
      case Leaf(_) => 1
    }
}