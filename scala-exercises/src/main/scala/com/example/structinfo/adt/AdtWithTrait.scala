package com.example.structinfo.adt

sealed trait AdtWithTrait {

  def name: String
}

object AdtWithTrait {

  case class Foo(name: String, paramFoo: String) extends AdtWithTrait
  case class Bar(name: String, paramBar: Double) extends AdtWithTrait
}