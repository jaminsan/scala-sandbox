package com.example.file

import java.io.{ByteArrayInputStream, InputStream}
import java.nio.file.FileAlreadyExistsException

import org.scalatest.BeforeAndAfterEach
import org.scalatest.flatspec.AnyFlatSpec

class OslibSpec extends AnyFlatSpec with BeforeAndAfterEach {

  "read" should "get file content as string" in {

    val ret: String = os.read(filePath)

    assert(ret == text)
  }

  "write" should "throw exception when file already exists" in {

    assertThrows[FileAlreadyExistsException] {
      os.write(filePath, "exception")
    }
  }

  "over" should "overwrite file content" in {

    val content = "overwrite"
    os.write.over(filePath, toIS(content))

    val ret = os.read(filePath)

    assert(ret == content)
  }

  override def beforeEach(): Unit =
    os.write(filePath, text)

  override protected def afterEach(): Unit =
    os.remove(filePath)

  val textSource = toIS(text)
  val wd         = os.pwd
  val filePath   = wd / fileName

  def toIS(content: String): InputStream =
    new ByteArrayInputStream(content.getBytes)
}
