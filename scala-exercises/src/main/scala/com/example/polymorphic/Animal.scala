package com.example.polymorphic

trait Animal

trait Reptile extends Animal {
  def reptile(): Unit = {
    println("reptile")
  }
}

trait Mammal extends Animal

trait Zebra extends Mammal {
  def zebraCount: Int = 1
}

trait Giraffe extends Mammal
