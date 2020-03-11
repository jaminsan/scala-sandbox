package com.example.repo.unit

import scalikejdbc.{GlobalSettings, LoggingSQLAndTimeSettings}

trait ScalikeLogging {

  GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(
    enabled        = true,
    singleLineMode = true,
    //    printUnprocessedStackTrace = false,
    //    stackTraceDepth            = 15,
    logLevel               = Symbol("debug"),
    warningEnabled         = false,
    warningThresholdMillis = 100000L,
    warningLogLevel        = Symbol("warn")
  )
}
