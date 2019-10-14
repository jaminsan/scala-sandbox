package com.example

import scopt.OParser

object Main {

  def main(args: Array[String]): Unit = {
    val builder = OParser.builder[Config]
    val parser = {
      import builder._

      OParser.sequence(
        programName("test"),
        head("test", "0.0.1"),
        opt[String]('n', "name")
          .action((name, config) => config.copy(name = name))
          .text("name is an string property")
      )
    }
    OParser.parse(parser, args, Config()) match {
      case Some(config) =>
        println(s"Hello, ${config.name}!")
      case _ =>
        sys.exit(1)
    }
    ()
  }

}
