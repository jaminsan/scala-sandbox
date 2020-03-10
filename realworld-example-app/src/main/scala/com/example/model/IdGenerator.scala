package com.example.model

import java.time.Instant

import com.example.model.IdGenerator._
import de.huxhorn.sulky.ulid.ULID

case class ModelId(value: String, generatedAt: Instant)

class IdGenerator {

  def newId(): ModelId = {
    val v = ulid.nextValue()
    val t = Instant.ofEpochMilli(v.timestamp())

    ModelId(value = v.toString, generatedAt = t)
  }
}

object IdGenerator {

  private lazy val ulid = new ULID()
}
