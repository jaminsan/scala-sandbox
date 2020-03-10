package com.example.service.article

import java.util.UUID

import com.example.service.article.Slugifier._
import com.github.slugify.Slugify

class Slugifier {

  def slugify(title: String): String = {
    val slugifiedTitle = slugifier.slugify(title)
    val randomPart     = UUID.randomUUID()
    s"$slugifiedTitle-$randomPart"
  }
}

object Slugifier {

  lazy val slugifier = new Slugify()
}
