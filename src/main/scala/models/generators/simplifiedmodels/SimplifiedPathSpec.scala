package com.jfast
package models.generators.simplifiedmodels

import models.generators.simplifiedmodels.SimplifiedResponse

import java.util

class SimplifiedPathSpec (val pathName: String,
                          val url: String,
                          val operationType: String,
                          val pathList: util.LinkedList[SimplifiedPathVar],
                          val queryList: util.LinkedList[SimplifiedPathVar],
                          val body: SimplifiedPathVar,
                          val allVariables: util.LinkedList[SimplifiedPathVar],
                          val responses: util.LinkedList[SimplifiedResponse])
