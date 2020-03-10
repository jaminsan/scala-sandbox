package com.example.service

import cats.data.ValidatedNel

trait PropertyViolation {

  def errorMessage: String
}

object PropertyViolation {

  type ValidationResult[A] = ValidatedNel[PropertyViolation, A]
}
