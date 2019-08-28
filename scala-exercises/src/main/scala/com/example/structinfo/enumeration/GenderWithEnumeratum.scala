package com.example.structinfo.enumeration

import com.example.structinfo.enumeration.GenderWithEnumeratum.{Mens, Unisex, Womens}
import enumeratum._

sealed trait GenderWithEnumeratum extends EnumEntry

object GenderWithEnumeratum extends Enum[GenderWithEnumeratum] {

  // macro で values の実装が作られる
  val values = findValues

  object Mens extends GenderWithEnumeratum
  object Womens extends GenderWithEnumeratum
  object Unisex extends GenderWithEnumeratum

}

object GenderWithEnumeratumApp {

  def main(args: Array[String]): Unit = {
    assert(GenderWithEnumeratum.values == List(Mens, Womens, Unisex))
    assert(GenderWithEnumeratum.indexOf(Unisex) == 2)
  }
}