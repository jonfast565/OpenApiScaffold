package com.jfast
package generators

import models.openapi.ApiSpecification

import com.jfast.models.generators.GeneratorResult

trait Generator {
  def generate(apiSpecification: ApiSpecification): List[GeneratorResult] = {
    List()
  }
}
