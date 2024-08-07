package com.jfast
package utilities

import java.io.{BufferedWriter, FileWriter, IOException}

object GeneralUtilities {
  def stringIsNullOrEmptyOrBlank(s: String): Boolean = s == null || s.isEmpty || s.trim().isEmpty

  @throws[IOException]
  def writeFile(fileName: String, fileContents: String): Unit = {
    val writer = new BufferedWriter(new FileWriter(fileName))
    try {
      writer.write(fileContents)
    } finally {
      writer.close()
    }
  }

  def capitalize(s: String): String = {
    if (s == null || s.isEmpty) return s
    s.substring(0, 1).toUpperCase() + s.substring(1)
  }

  def pascalToSnake(pascalCase: String): String = {
    val regex = "([a-z])([A-Z]+)".r
    val snakeCase = regex.replaceAllIn(pascalCase, "$1_$2").toLowerCase
    snakeCase
  }

  def snakeToPascal(input: String): String = {
    input.split("_").map(_.capitalize).mkString
  }

  def snakeToCamel(input: String): String = {
    val parts = input.split("_")
    val head = parts.headOption.getOrElse("")
    val tail = parts.drop(1).map(_.capitalize).mkString
    head + tail
  }

  def removePropComponentString(propReferenceTypeName: String): String =
    propReferenceTypeName.replace("#/components/schemas/", "").trim()
}
