package com.example.file

import better.files.Dsl._
import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec

class BetterFilesSpec extends AnyFlatSpec with BeforeAndAfterEach {

  "contentAsString" should "get file content as string" in {

    val ret = file.contentAsString

    assert(ret == text)
  }

  "write" should "not throw exception when file already exists" in {

    val content = "exception"
    file.write(content)

    assert(file.contentAsString == content)
  }

  "overwrite" should "overwrite file content" in {

    val content = "overwrite"
    file.overwrite(content)

    val ret = file.contentAsString

    assert(ret == content)
  }

  override def beforeEach(): Unit = {
    file.write(text)
    ()
  }

  override protected def afterEach(): Unit = {
    file.delete()
    ()
  }

  val wd   = pwd
  val file = wd / fileName
}
