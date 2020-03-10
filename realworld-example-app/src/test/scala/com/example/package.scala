package com

import scalikejdbc.{GlobalSettings, LoggingSQLAndTimeSettings}

import scala.concurrent.duration.{Duration, SECONDS}
import scala.concurrent.{Await, ExecutionContext, Future}

package object example {

  GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(
    enabled                    = true,
    singleLineMode             = true,
    printUnprocessedStackTrace = false,
    stackTraceDepth            = 15,
    logLevel                   = Symbol("debug"),
    warningEnabled             = false,
    warningThresholdMillis     = 100000L,
    warningLogLevel            = Symbol("warn")
  )

  implicit class FutureOps[A](self: Future[A]) {

    def await(durationSeconds: Int = 10)(implicit ec: ExecutionContext): A =
      Await.result(self, Duration(durationSeconds, SECONDS))
  }
}
