package com.jfast
package generators

import converters.openapi.{DefaultGsonParser, DefaultHandlebarsProxy}
import models.generators.GeneratorResult
import models.openapi.ApiSpecification
import models.generators.python.PythonClasses
import models.generators.simplifiedmodels.SimplifiedPathSpecs

import com.jfast.utilities.CasingType.PythonCasing

import scala.io.Source

class PythonGenerator extends Generator {
  override def generate(apiSpecification: ApiSpecification): List[GeneratorResult] = {
    val controllerTemplate = Source.fromResource("templates/python/python-controllers.hbs").mkString
    val modelTemplate = Source.fromResource("templates/python/python-models.hbs").mkString
    val gson = DefaultGsonParser.get()

    val pythonicClasses = PythonClasses(apiSpecification)
    val classSpecJson = gson.toJson(pythonicClasses)
    val pathSpecs = SimplifiedPathSpecs(apiSpecification, PythonCasing)
    val pathSpecJson = gson.toJson(pathSpecs)

    println(pathSpecJson)

    val modelFile = DefaultHandlebarsProxy.applyJson(modelTemplate, classSpecJson)
    val controllerFile = DefaultHandlebarsProxy.applyJson(controllerTemplate, pathSpecJson)

    List(
      GeneratorResult(s"./results/python/${apiSpecification.getName.toLowerCase}-model.py", modelFile),
      GeneratorResult(s"./results/python/${apiSpecification.getName.toLowerCase}-controller.py", controllerFile)
    )
  }
}
