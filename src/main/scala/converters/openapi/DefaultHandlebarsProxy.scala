package com.jfast
package converters.openapi

import com.github.jknack.handlebars.*
import com.github.jknack.handlebars
import com.github.jknack.handlebars.helper.{ConditionalHelpers, StringHelpers}
import com.google.gson.reflect.TypeToken

import java.util

object DefaultHandlebarsProxy {
  def applyJson(templateString: String, jsonString: String): String = {
    val gson = DefaultGsonParser.get()
    val typeToken = new TypeToken[util.Map[String, Object]](){}.getType
    val map: util.Map[String, Object] = gson.fromJson(jsonString, typeToken)
    val handlebars = new Handlebars()
    handlebars.registerHelpers(classOf[ConditionalHelpers])
    handlebars.registerHelpers(classOf[StringHelpers])
    val context: Context = Context.newBuilder(map).build()
    val template = handlebars.compileInline(templateString)
    val rendered = template.apply(context)
    rendered
  }
}
