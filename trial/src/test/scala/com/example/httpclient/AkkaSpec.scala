package com.example.httpclient

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.SystemMaterializer
import org.scalatest.FlatSpec

import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContextExecutor}
import scala.language.postfixOps

class AkkaSpec extends FlatSpec {

  implicit val system:           ActorSystem              = ActorSystem()
  implicit val materializer:     SystemMaterializer       = SystemMaterializer(system)
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  "singleRequest" should "receive response xml" in {

    val future =
      for {
        response <- Http().singleRequest(HttpRequest(uri = rssUrl))
        body     <- Unmarshal(response.entity).to[String]
      } yield toXml(body)

    val res = Await.result(future, 10 seconds)

    assert((res \ "channel" \ "link").text == link)
  }

}
