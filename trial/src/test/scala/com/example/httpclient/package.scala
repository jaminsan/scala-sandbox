package com.example

import scala.xml.{Elem, XML}

package object httpclient {

  val rssUrl: String = "https://b.hatena.ne.jp/entrylist.rss"

  val link: String = "https://b.hatena.ne.jp/entrylist/all"

  def toXml(s: String): Elem = XML.loadString(s)

}
