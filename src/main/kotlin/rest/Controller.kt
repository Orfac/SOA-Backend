package rest

import config.Utils
import exceptions.RequestHandlingException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolation
import javax.validation.Valid
import javax.validation.Validator

open class Controller(val request: HttpServletRequest, val response: HttpServletResponse) {
  val validator : Validator = Utils.validator
  fun doRequest(parameters: List<Any>, processFunction: (List<Any>) -> Unit) {
    try {
      validate(parameters)
      processFunction(parameters)
    } catch (ex: RequestHandlingException) {
      response.sendError(400, ex.message)
    }
  }

  protected fun validate(@Valid parameters: List<Any>){
    val constraintViolations = HashSet<ConstraintViolation<Any>>()
    for (parameter in parameters){
      val parameterViolations = validator.validate(parameter)
      if (parameterViolations.isNotEmpty()){
        constraintViolations.addAll(parameterViolations)
      }
    }
    if (constraintViolations.isNotEmpty()){
      throw RequestHandlingException(constraintViolations.joinToString())
    }
  }
}