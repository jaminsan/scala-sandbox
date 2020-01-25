package com.example.json4s

import org.json4s.Xml.toJson
import org.json4s._
import org.scalatest.flatspec.AnyFlatSpec

import scala.xml.Elem

class XmlSpec extends AnyFlatSpec {

  "toJson" should "convert xml to json" in {
    implicit lazy val formats: DefaultFormats.type = DefaultFormats

    val channel = ((toJson(rss) \ "rss") \ "channel").extract[Channel]

    assert(channel.title == "GoUpstate.com News Headlines")
    assert(channel.item.size == 2)
  }

  case class Channel(
    title:         String,
    link:          String,
    description:   String,
    lastBuildDate: Option[String],
    docs:          Option[String],
    generator:     Option[String],
    item:          List[Item])

  case class Item(
    title:       String,
    link:        String,
    description: String,
    pubDate:     Option[String],
    guid:        Option[String],
    enclosure:   Option[String])

  val rss: Elem =
    <rss version="2.0">
      <channel>
        <title>GoUpstate.com News Headlines</title>
        <link>http://www.goupstate.com/</link>
        <description>The latest news from GoUpstate.com, a Spartanburg Herald-Journal Web site.</description>
        <lastBuildDate>Sat, 07 Sep 2002 09:42:31 GMT</lastBuildDate>
        <docs>http://blogs.law.harvard.edu/tech/rss</docs>
        <generator>MightyInHouse Content System v2.3</generator>

        <item>
          <title>Venice Film Festival Tries to Quit Sinking</title>
          <link>http://nytimes.com/2004/12/07FEST.html</link>
          <description>Some of the most heated chatter at the Venice Film Festival this week was about the way that the arrival of the stars at the Palazzo del Cinema was being staged.</description>
          <pubDate>Sun, 19 May 2002 15:21:36 GMT</pubDate>
          <guid isPermalink="false">http://inessential.com/2002/09/01.php#a2</guid>
          <enclosure url="http://www.scripting.com/mp3s/weatherReportSuite.mp3" length="12216320" type="audio/mpeg" />
        </item>

        <item>
          <title>Venice Film Festival Tries to Quit Sinking</title>
          <link>http://nytimes.com/2004/12/07FEST.html</link>
          <description>Some of the most heated chatter at the Venice Film Festival this week was about the way that the arrival of the stars at the Palazzo del Cinema was being staged.</description>
          <pubDate>Sun, 19 May 2002 15:21:36 GMT</pubDate>
          <guid isPermalink="false">http://inessential.com/2002/09/01.php#a2</guid>
          <enclosure url="http://www.scripting.com/mp3s/weatherReportSuite.mp3" length="12216320" type="audio/mpeg" />
        </item>
      </channel>
    </rss>
}
