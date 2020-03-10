package com.example.service.article

import cats.implicits._
import com.example.model.Tag
import com.example.service.PropertyViolation
import com.example.service.PropertyViolation.ValidationResult
import com.example.service.article.ArticleValidator.{TooManyTags, _}

trait ArticleValidator {

  def validateTags(tags: List[String]): ValidationResult[List[String]] =
    if (tags.length <= 5) tags.validNel
    else TooManyTags.invalidNel

  def validateTitle(title: String): ValidationResult[String] =
    if (title.replaceAll("\\s", "").length <= 50) title.validNel
    else TooLongTitle.invalidNel
}

object ArticleValidator {

  case object TooManyTags extends PropertyViolation {
    override def errorMessage: String = "No more than 5 tags can attached for each article."
  }

  case object TooLongTitle extends PropertyViolation {
    override def errorMessage: String = "Title must be less than 50 characters."
  }

}
