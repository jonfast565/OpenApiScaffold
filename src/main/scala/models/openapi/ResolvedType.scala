package com.jfast
package models.openapi


import models.openapi.FieldType

class ResolvedType(val fieldType: FieldType,
                    val aggregateType: AggregateType,
                    val customTypeName: String)

