package com.jfast
package generators
import models.openapi.ApiSpecification
import converters.openapi.{DefaultGsonParser, DefaultHandlebarsProxy}
import models.generators.GeneratorResult
import models.generators.csharp.CSharpClasses
import models.generators.simplifiedmodels.{SimplifiedPathSpec, SimplifiedPathSpecs}

import com.jfast.utilities.CasingType.CSharpCasing

import scala.io.Source

class CSharpGenerator extends Generator {
  override def generate(apiSpecification: ApiSpecification): List[GeneratorResult] = {
    val gson = DefaultGsonParser.get()

    // get templates
    val controllerTemplate = Source.fromResource("templates/csharp/csharp-controllers.hbs").mkString
    val controllerBaseTemplate = Source.fromResource("templates/csharp/csharp-controller-base.hbs").mkString
    val modelTemplate = Source.fromResource("templates/csharp/csharp-models.hbs").mkString
    val interfaceTemplate = Source.fromResource("templates/csharp/csharp-interface.hbs").mkString

    // initialize classes for models
    val csharpClasses = CSharpClasses(apiSpecification)
    val classSpecJson = gson.toJson(csharpClasses)
    val modelFile = DefaultHandlebarsProxy.applyJson(modelTemplate, classSpecJson)

    // initialize path specs for partials
    val pathSpecs = SimplifiedPathSpecs(apiSpecification, CSharpCasing)
    val pathSpecJson = gson.toJson(pathSpecs)
    val controllerBaseFile = DefaultHandlebarsProxy.applyJson(controllerBaseTemplate, pathSpecJson)

    var generatorResults : List[GeneratorResult] = List(
      GeneratorResult(s"./results/csharp/${apiSpecification.getName.toLowerCase}-model.cs", modelFile),
      GeneratorResult(s"./results/csharp/Controllers/ControllerBase.cs", controllerBaseFile)
    )
    for controller <- pathSpecs.uniqueControllers do
      val controllerPathSpec = SimplifiedPathSpecs(apiSpecification, CSharpCasing, Some(pathSpecs.controllerPath(controller)))
      val controllerPathSpecJson = gson.toJson(controllerPathSpec)
      val controllerSplitFile = DefaultHandlebarsProxy.applyJson(controllerTemplate, controllerPathSpecJson)
      val interfaceSplitFile = DefaultHandlebarsProxy.applyJson(interfaceTemplate, controllerPathSpecJson)
      generatorResults = generatorResults ++
        List(GeneratorResult(s"./results/csharp/Controllers/${controller}.cs", controllerSplitFile)) ++
        List(GeneratorResult(s"./results/csharp/Interfaces/${controller}.cs", interfaceSplitFile))

    generatorResults
  }
}
