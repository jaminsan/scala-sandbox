package com.example.httpclient

import com.softwaremill.sttp.quick._
import org.scalatest.FlatSpec

class SttpSpec extends FlatSpec {

  "get" should "receive response xml" in {

    val res = sttp.get(uri"$rssUrl").send().body.map(toXml)

    assert(res.isRight)
    res.foreach { xml =>
      assert((xml \ "channel" \ "link").text == link)
    }
  }
}
