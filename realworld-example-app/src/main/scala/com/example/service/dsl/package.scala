package com.example.service

import cats.data.NonEmptyList
import com.example.exception.InvalidPropertyException

import scala.concurrent.{ExecutionContext, Future}

package object dsl {

  type PropertyValidationFuture[A] = Future[Either[NonEmptyList[PropertyViolation], A]]

  implicit class FutureOptionOps[A](self: Future[Option[A]]) {

    def getOrFailWith[E <: RuntimeException](e: E)(implicit ec: ExecutionContext): Future[A] =
      self.flatMap {
        case Some(v) => Future.successful(v)
        case None => Future.failed(e)
      }
  }

  def failIfInvalid[A](validate: => PropertyValidationFuture[A])(implicit ec: ExecutionContext): Future[A] =
    validate.flatMap {
      case Right(v) => Future.successful(v)
      case Left(violations) => Future.failed(new InvalidPropertyException(violations.toList))
    }

  implicit class FutureIdOps[T](obj: T) {

    def asFuture: Future[T] = Future.successful(obj)
  }
}
