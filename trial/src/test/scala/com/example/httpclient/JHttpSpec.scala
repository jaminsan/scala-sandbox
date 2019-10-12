package com.example.httpclient

import org.scalatest.FlatSpec
import scalaj.http.Http

class JHttpSpec extends FlatSpec {

  "Http" should "receive response xml" in {

    val ret = Http(rssUrl).asString

    assert(ret.is2xx)
    assert((toXml(ret.body) \ "channel" \ "link").text == link)
  }

}
