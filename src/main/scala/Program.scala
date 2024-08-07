package com.jfast

import program.CommandLineArguments

import org.slf4j.LoggerFactory
import picocli.CommandLine

object Program {
  private val logger = LoggerFactory.getLogger(getClass)
    def main(args: Array[String]): Unit = {
      val resultStatusCode = new CommandLine(new CommandLineArguments).execute(args*)
      ()
    }
}
