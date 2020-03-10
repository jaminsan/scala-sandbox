package com.example.service.article

import cats.implicits._
import com.example.service.PropertyViolation
import com.example.service.PropertyViolation.ValidationResult
import com.example.service.article.TagValidator.TooLongTagName

trait TagValidator {

  def validateName(name: String): ValidationResult[String] =
    if (name.length <= 20) name.validNel
    else TooLongTagName.invalidNel

  def validateNames(names: List[String]): ValidationResult[List[String]] =
    if (!names.map(validateName).exists(_.isInvalid)) names.validNel
    else TooLongTagName.invalidNel
}

object TagValidator {

  case object TooLongTagName extends PropertyViolation {
    override def errorMessage: String = "Tag name must be less than 20 characters"
  }
}
