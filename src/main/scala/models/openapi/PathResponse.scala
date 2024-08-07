package com.jfast
package models.openapi


import scala.compiletime.uninitialized

class PathResponse {
  private var statusCode: String = uninitialized
  private var `type`: ResolvedType = uninitialized

  def getStatusCode: String = statusCode
  def setStatusCode(statusCode: String): Unit = this.statusCode = statusCode

  def getType: ResolvedType = `type`
  def setType(`type`: ResolvedType): Unit = this.`type` = `type`
}

