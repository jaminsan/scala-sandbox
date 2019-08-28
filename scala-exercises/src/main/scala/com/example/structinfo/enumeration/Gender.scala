package com.example.structinfo.enumeration

import com.example.structinfo.enumeration.Gender.{Mens, Unisex, Womens}

sealed trait Gender

object Gender {

  object Mens extends Gender
  object Womens extends Gender
  object Unisex extends Gender

  def values =
    List(
      Mens,
      Womens,
      Unisex
    )

  def indexOf(g: Gender): Int = values.indexOf(g)
}

object GenderApp {

  def main(args: Array[String]): Unit = {
    assert(Gender.values == List(Mens, Womens, Unisex))
    assert(Gender.indexOf(Unisex) == 2)

  }
}