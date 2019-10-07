package com.example.polymorphic

trait Stack[+A] {
  def push[E >: A](e: E): Stack[E]
  def top:     A
  def pop:     Stack[A]
  def isEmpty: Boolean
}

class NonEmptyStack[+A](private val first: A, private val rest: Stack[A]) extends Stack[A] {
  def push[E >: A](e: E): Stack[E] = new NonEmptyStack[E](e, this)
  def top:     A        = first
  def pop:     Stack[A] = rest
  def isEmpty: Boolean  = false
}

case object EmptyStack extends Stack[Nothing] {
  def push[E >: Nothing](e: E): Stack[E] = new NonEmptyStack[E](e, this)
  def top:     Nothing = throw new IllegalArgumentException("empty stack")
  def pop:     Nothing = throw new IllegalArgumentException("empty stack")
  def isEmpty: Boolean = true
}

object Stack {
  def apply(): Stack[Nothing] = EmptyStack

  def main(args: Array[String]): Unit = {
    // 下限が Zebra で A は Zebra に対して共変である必要がある
    val zebra: Stack[Zebra] = Stack().push(new Zebra {})

    // Mammal は Zebra に対して共変なので Mammal として解釈される
    // （Zebra であれば Mammal を少なくとも満たすはず、という解釈
    val mammal: Stack[Mammal] = zebra.push(new Mammal {})
    println(mammal)

    // Reptile は Zebra に対して共変ではなく、共通の共変型の Animal として解釈される
    val reptile: Stack[Animal] = zebra.push(new Reptile {})
    println(reptile)
  }
}
