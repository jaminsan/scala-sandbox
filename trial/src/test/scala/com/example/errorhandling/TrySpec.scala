package com.example.errorhandling

import org.scalatest.flatspec.AnyFlatSpec

import scala.util.Try

class TrySpec extends AnyFlatSpec {

  "Try" should "convert exception to Failure object" in {

    val ret =
      Try {
        throw new Exception("test")
      }

    assert(ret.isFailure)
    assert(ret.failed.get.getMessage == "test")
  }

  "Call of flatMap chain" should "be skipped when exception occurred" in {

    def mayBeException(n: Int): Unit =
      if (n == 0) throw new Exception("test") else ()

    var n = 0
    val ret =
      for {
        _ <- Try { n = n + 1; n }
        _ <- Try { mayBeException(0) }
        _ <- Try { n = n + 2; n }
      } yield n

    assert(ret.isFailure)
    assert(n == 1)
  }
}
