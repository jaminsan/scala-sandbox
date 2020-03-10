package com.example.db.article

import com.example.db.futureUpdate
import scalikejdbc.{DBSession, _}

import scala.concurrent.{ExecutionContext, Future}

class FollowAssociationDao(implicit ec: ExecutionContext) {

  import FollowAssociationTable._

  def findByFollower(followerId: String)(implicit session: DBSession): Future[List[FollowAssociationRecord]] =
    Future {
      withSQL {
        select
          .from(FollowAssociationTable as fa)
          .where
          .eq(fa.followerId, followerId)
      }.map(FollowAssociationRecord(fa))
        .list()
        .apply()
    }

  def findByFollowerAndFollowed(
    followerId:       String,
    followedId:       String
  )(implicit session: DBSession): Future[Option[FollowAssociationRecord]] =
    Future {
      withSQL {
        select
          .from(FollowAssociationTable as fa)
          .where
          .eq(fa.followerId, followerId)
          .and
          .eq(fa.followedId, followedId)
      }.map(FollowAssociationRecord(fa))
        .single()
        .apply()
    }

  def delete(followAssociationId: String)(implicit session: DBSession): Future[Int] =
    futureUpdate {
      deleteFrom(FollowAssociationTable).where
        .eq(fa.followAssociationId, followAssociationId)
    }

  def insert(record: FollowAssociationRecord)(implicit session: DBSession): Future[Int] =
    futureUpdate {
      insertInto(FollowAssociationTable)
        .namedValues(
          column.followAssociationId -> record.followAssociationId,
          column.followerId          -> record.followerId,
          column.followedId          -> record.followedId
        )
    }

  def findByFollowerSQL(followerId: String)(implicit session: DBSession): Future[List[FollowAssociationRecord]] =
    Future {
      sql"select * from ${FollowAssociationTable as fa} where ${fa.followerId} = $followerId"
        .map(FollowAssociationRecord(fa))
        .list()
        .apply()
    }

  def deleteSQL(followAssociationId: String)(implicit session: DBSession): Future[Int] =
    Future {
      sql"delete from ${FollowAssociationTable as fa} where ${fa.followAssociationId} = $followAssociationId"
        .update()
        .apply()
    }

  def insertSQL(record: FollowAssociationRecord)(implicit session: DBSession): Future[Int] =
    Future {
      sql"insert into ${FollowAssociationTable as fa} values (?, ?, ?)"
        .bindByName(
          Symbol("followAssociationId") -> record.followAssociationId,
          Symbol("followerId")          -> record.followerId,
          Symbol("followedId")          -> record.followedId
        )
        .update()
        .apply()
    }
}
