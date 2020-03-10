package com.example.config

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContext

object FutureContext {

  val system = ActorSystem()

  implicit val default: ExecutionContext = system.dispatchers.lookup("contexts.default")
  implicit val blockingIO: ExecutionContext = system.dispatchers.lookup("contexts.blocking-io")
}
