package com.example.logging
import org.scalatest.FlatSpec
import wvlet.log.{LazyLogger, LogLevel, LogRotationHandler, LogSupport, Logger}

class AirframeLogSpec extends FlatSpec {

  Logger.setDefaultLogLevel(LogLevel.TRACE)

  "log method" should "output log message" in new LogSupport {

    trace("trace")
    debug("debug")
    info("info")
    warn("warn", new Exception("warn"))
    error("error", new Exception("error"))
  }

  "logger" should "output log message" in new LazyLogger {

    logger.trace("trace")
    logger.debug("debug")
    logger.info("info")
    logger.warn("warn", new Exception("warn"))
    logger.error("error", new Exception("error"))
  }

  "logger" should "output log message to file" in new LazyLogger {

    logger.resetHandler(new LogRotationHandler(
      fileName = "test.log",
      maxNumberOfFiles = 100,
      maxSizeInBytes = 100 * 1024 * 1024
    ))

    logger.trace("trace")
    logger.debug("debug")
    logger.info("info")
    logger.warn("warn", new Exception("warn"))
    logger.error("error", new Exception("error"))
  }
}
