package com.example.dbclient

case class Item(
  name:   String,
  price: Int,
  gender: Gender,
  // size
  // color
  // category
)

case class Color(
  code: String,
  name: String
                )

// client library としての使いかただけではなく
// scala の場合は Service とかの型にも影響してくるから Controller からの通しで考えないとだな抽象化について
// transactional な実装したい時の合成の仕方も結構違うだろうし
// sql 的なところだけではない気がしていて一回の勉強会では思いかも？一回めは使い方、2回めは抽象化

// delivery time
// feature
// made in
// seen tag
// material

// cart
// order
// payment
// invoice

// production
// stock
// line item
// assemble
// inventory

// shipping

sealed trait Gender

object Gender {
  case object Unisex extends Gender
  case object Womens extends Gender
  case object Mens extends Gender
}
