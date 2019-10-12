package com.example.httpclient

import java.net.{HttpURLConnection, URL}

import org.scalatest.FlatSpec

import scala.io.Source
import scala.util.Try

class UrlConnectionSpec extends FlatSpec {

  "openConnection" should "receive xml response" in {

    val res = get(rssUrl)

    assert(res.isSuccess)
    res.foreach { body =>
      assert((toXml(body) \ "channel" \ "link").text == link)
    }
  }

  def get(url: String): Try[String] =
    for {
      con  <- Try(new URL(url).openConnection().asInstanceOf[HttpURLConnection])
      body <- Try(Source.fromInputStream(con.getInputStream).mkString)
    } yield body

}
