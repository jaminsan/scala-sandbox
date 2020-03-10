package com.example.db.article

import scalikejdbc._

case class FollowAssociationRecord(
  followAssociationId: String,
  followerId:          String,
  followedId:          String
)

object FollowAssociationRecord {

  def apply(fa: SyntaxProvider[FollowAssociationRecord])(ws: WrappedResultSet): FollowAssociationRecord =
    apply(fa.resultName)(ws)

  def apply(fa: ResultName[FollowAssociationRecord])(ws: WrappedResultSet): FollowAssociationRecord =
    FollowAssociationRecord(
      followAssociationId = ws.string(fa.followAssociationId),
      followerId          = ws.string(fa.followerId),
      followedId          = ws.string(fa.followedId)
    )
}

object FollowAssociationTable extends SQLSyntaxSupport[FollowAssociationRecord] {

  override def tableName: String = "follow_association"

  val fa = syntax("fa")
}
