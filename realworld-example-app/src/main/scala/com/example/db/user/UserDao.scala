package com.example.db.user

import com.example.exception.NotFoundModelException
import scalikejdbc.{DBSession, _}

import scala.concurrent.{ExecutionContext, Future}

class UserDao(implicit ec: ExecutionContext) {

  import UserTable._

  def findById(userId: String)(implicit session: DBSession): Future[UserRecord] =
    Future {
      withSQL { select.from(UserTable as u) }
        .map(UserRecord(u))
        .single()
        .apply()
        .getOrElse(throw new NotFoundModelException(s"User not found for userId=$userId"))
    }

  def findByIds(userIds: List[String])(implicit session: DBSession): Future[List[UserRecord]] =
    Future {
      withSQL {
        select
          .from(UserTable as u)
          .where
          .in(u.userId, userIds)
      }.map(UserRecord(u))
        .list()
        .apply()
    }

  def findByEmailOption(email: String)(implicit session: DBSession): Future[Option[UserRecord]] =
    Future {
      withSQL {
        select
          .from(UserTable as u)
          .where
          .eq(u.email, email)
      }.map(UserRecord(u))
        .single()
        .apply()
    }

  def findByName(name: String)(implicit session: DBSession): Future[UserRecord] =
    findByNameOption(name).map(_.getOrElse(throw new NotFoundModelException(s"User not found for name=$name")))

  def findByNameOption(name: String)(implicit session: DBSession): Future[Option[UserRecord]] =
    Future {
      withSQL {
        select
          .from(UserTable as u)
          .where
          .eq(u.name, name)
      }.map(UserRecord(u))
        .single()
        .apply()
    }
}
