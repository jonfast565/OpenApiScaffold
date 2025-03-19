package com.jfast
package models.generators.simplifiedmodels

import com.jfast.utilities.ControllerUtilities

import java.util

class SimplifiedPathSpec (
                           val pathName: String,
                           val url: String,
                           val operationType: String,
                           val pathList: java.util.LinkedList[SimplifiedPathVar],
                           val queryList: java.util.LinkedList[SimplifiedPathVar],
                           val body: SimplifiedPathVar,
                           val allVariables: java.util.LinkedList[SimplifiedPathVar],
                           val responses: java.util.LinkedList[SimplifiedResponse]
                         ) {

  def controllerName(): String = ControllerUtilities.getControllerName(url)
}
