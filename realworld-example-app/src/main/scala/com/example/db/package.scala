package com.example

import scalikejdbc.DBSession
import scalikejdbc._

import scala.concurrent.{ExecutionContext, Future}

// NOTE: blocking 専用の ExecutionContext を用意して implicit parameter に渡すこと
package object db {

  object futureUpdate {

    def apply[A <: SQLBuilder[UpdateOperation]](builder: A)(implicit s: DBSession, ec: ExecutionContext): Future[Int] =
      Future {
        withSQL[UpdateOperation](builder).update.apply()
      }
  }
}
