package com.example.repo

import com.example.db.article.FollowAssociationDao
import com.example.db.user.UserDao
import com.example.model.Profile
import scalikejdbc.DBSession

import scala.concurrent.{ExecutionContext, Future}

class ProfileRepo(userRepo: UserDao, followAssociationRepo: FollowAssociationDao, implicit val ec: ExecutionContext) {

  def findByUserName(userName: String, maybeFollowerId: Option[String])(implicit session: DBSession): Future[Profile] =
    for {
      user    <- userRepo.findByName(userName)
      profile <- findByUserId(user.userId, maybeFollowerId)
    } yield profile

  def findByUserId(userId: String, maybeFollowerId: Option[String])(implicit session: DBSession): Future[Profile] =
    for {
      user <- userRepo.findById(userId)
      following <- maybeFollowerId match {
        case Some(followerId) => followAssociationRepo.findByFollowerAndFollowed(followerId, userId).map(_.isDefined)
        case None => Future.successful(false)
      }
    } yield Profile(user, following)
}
