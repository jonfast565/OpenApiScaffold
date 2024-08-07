package com.jfast
package program

import converters.openapi.OpenApiSpecificationConverter
import generators.{CSharpGenerator, FSharpGenerator, PythonGenerator}
import models.openapi.ApiSpecification

import com.jfast.utilities.DisableSslValidation
import io.swagger.parser.OpenAPIParser
import io.swagger.v3.parser.core.models.SwaggerParseResult
import picocli.CommandLine.{Command, Option, Parameters}

import java.net.{URI, URL}
import java.nio.file.{Files, Paths, StandardOpenOption}
import java.util.concurrent.Callable
import scala.compiletime.uninitialized

@Command(name = "swagger-scaffold",
  mixinStandardHelpOptions = true,
  version = Array("Swagger Scaffold 1.0"),
  description = Array("Builds a CSharp file for a given Swagger API json file"))
class CommandLineArguments extends Callable[Int] {

  @Parameters(index = "0", description = Array("The URL containing the swagger.json file"),
    defaultValue = "https://localhost:56826/swagger/v1/swagger.json")
  private var url: String = uninitialized

  @Parameters(index = "1", description = Array("The name of the client"), defaultValue = "Firehose")
  private var clientName: String = uninitialized

  @Option(names = Array("-v", "--verbose"), description = Array("Enables verbose output when flag is set"), defaultValue = "false")
  private var verbose: Boolean = uninitialized

  override def call(): Int = {
    try {
      println("--- Scaffold Swagger ---")
      val spec: ApiSpecification = parseOpenApiData
      generateCode(spec)
      println("Done!")
    } catch {
      case e: Exception =>
        System.err.println("ERROR: " + e)
        System.err.println("TRACE: " + e.getStackTrace.mkString(",\n"))
        return -1
    }
    0
  }

  private def generateCode(spec: ApiSpecification): Unit = {
    val generators = Array(
      CSharpGenerator(),
      FSharpGenerator(),
      PythonGenerator()
    )
    for (gen <- generators) {
      val result = gen.generate(spec)
      for (res <- result) {
        val path = Paths.get(res.filePath)
        Files.createDirectories(path.getParent)
        Files.deleteIfExists(path)
        Files.write(path, res.fileContents.getBytes, StandardOpenOption.CREATE)
      }
    }
  }

  private def parseOpenApiData = {
    println("Read OpenAPI specification...")
    val openApiParser = OpenAPIParser()
    DisableSslValidation.disableSslVerification()
    val result: SwaggerParseResult = openApiParser.readLocation(url, null, null)
    val parsedUrl: URL = URI.create(url).toURL
    val reconstructedUrl: String = parsedUrl.getProtocol + "://" + parsedUrl.getAuthority
    val api = result.getOpenAPI
    val info = api.getInfo
    val name = info.getTitle
    val spec = OpenApiSpecificationConverter.buildApiSpecification(name, reconstructedUrl, result)
    spec.setName(clientName)
    if (verbose) {
      spec.print()
    }
    spec
  }
}

