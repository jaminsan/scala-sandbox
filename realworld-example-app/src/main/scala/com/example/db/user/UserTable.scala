package com.example.db.user

import java.time.Instant

import scalikejdbc._

case class UserRecord(
  userId:    String,
  name:      String,
  email:     String,
  bio:       Option[String],
  image:     Option[String],
  createdAt: Instant,
  updatedAt: Instant
)

object UserRecord {

  def apply(u: SyntaxProvider[UserRecord])(ws: WrappedResultSet): UserRecord =
    apply(u.resultName)(ws)

  def apply(u: ResultName[UserRecord])(ws: WrappedResultSet): UserRecord =
    UserRecord(
      userId    = ws.string(u.userId),
      name      = ws.string(u.name),
      email     = ws.string(u.email),
      bio       = ws.stringOpt(u.bio),
      image     = ws.stringOpt(u.image),
      createdAt = ws.offsetDateTime(u.createdAt).toInstant,
      updatedAt = ws.offsetDateTime(u.updatedAt).toInstant
    )
}

object UserTable extends SQLSyntaxSupport[UserRecord] {

  override val tableName: String = "user"

  val u = syntax("u")
}
