package com.jfast
package generators
import models.openapi.ApiSpecification
import converters.openapi.{DefaultGsonParser, DefaultHandlebarsProxy}
import models.generators.GeneratorResult
import models.generators.csharp.CSharpClasses
import models.generators.simplifiedmodels.SimplifiedPathSpecs

import com.jfast.utilities.CasingType.CSharpCasing

import scala.io.Source

class CSharpGenerator extends Generator {
  override def generate(apiSpecification: ApiSpecification): List[GeneratorResult] = {
    val controllerTemplate = Source.fromResource("templates/csharp/csharp-controllers.hbs").mkString
    val modelTemplate = Source.fromResource("templates/csharp/csharp-models.hbs").mkString
    val gson = DefaultGsonParser.get()
    val csharpClasses = CSharpClasses(apiSpecification)
    val classSpecJson = gson.toJson(csharpClasses)
    val pathSpecs = SimplifiedPathSpecs(apiSpecification, CSharpCasing)
    val pathSpecJson = gson.toJson(pathSpecs)
    val modelFile = DefaultHandlebarsProxy.applyJson(modelTemplate, classSpecJson)
    val controllerFile = DefaultHandlebarsProxy.applyJson(controllerTemplate, pathSpecJson)

    List(
      GeneratorResult(s"./results/csharp/${apiSpecification.getName.toLowerCase}-model.cs", modelFile),
      GeneratorResult(s"./results/csharp/${apiSpecification.getName.toLowerCase}-controller.cs", controllerFile)
    )
  }
}
