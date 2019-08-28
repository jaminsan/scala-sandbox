package com.example.defandeval

import scala.collection.mutable

object CallByNameAndCallByValue {

  val cache = mutable.Map.empty[Long, String]

  def findOrUpdate(id: Long, findDataFromStore: => String): String =
    cache.getOrElseUpdate(id, findDataFromStore)

  def main(args: Array[String]): Unit = {
    findOrUpdate(1L, {
      println("これは評価される")
      "value"
    })
    findOrUpdate(1L, {
      println("cache されているのでこれは評価されない")
      "value"
    })
    ()
  }

}
