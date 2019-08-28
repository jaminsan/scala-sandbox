package com.example.structinfo.adt

sealed abstract class AdtWithAbstractClass(name: String)

object AdtWithAbstractClass {

  // abstract class の field として指定した場合はコンストラクタでの初期化が必要
  // def で指定した場合は trait 同様コンストラクタに渡すのは不要
  case class Foo(name: String, paramFoo: String) extends AdtWithAbstractClass(name)
  case class Bar(name: String, paramBar: Double) extends AdtWithAbstractClass(name)
}