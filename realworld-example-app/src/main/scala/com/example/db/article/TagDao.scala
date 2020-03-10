package com.example.db.article

import com.example.db.futureUpdate
import scalikejdbc._

import scala.concurrent.{ExecutionContext, Future}

class TagDao(implicit ec: ExecutionContext) {

  import TagTable._

  def insertAll(records: List[TagRecord])(implicit session: DBSession): Future[List[Int]] =
    Future.sequence(records.map(insert))

  def insert(record: TagRecord)(implicit session: DBSession): Future[Int] =
    futureUpdate {
      insertInto(TagTable)
        .namedValues(
          column.tagId -> record.tagId,
          column.name  -> record.name
        )
    }

  def findByNames(names: List[String])(implicit session: DBSession): Future[List[TagRecord]] =
    Future {
      withSQL {
        select
          .from(TagTable as t)
          .where
          .in(t.name, names)
      }.map(TagRecord(t.resultName))
        .list()
        .apply()
    }

  def findByIds(tagIds: List[String])(implicit session: DBSession): Future[List[TagRecord]] =
    Future {
      withSQL {
        select
          .from(TagTable as t)
          .where
          .in(t.tagId, tagIds)
      }.map(TagRecord(t.resultName))
        .list()
        .apply()
    }

  def findAll()(implicit session: DBSession): Future[List[TagRecord]] =
    Future {
      withSQL {
        select.from(TagTable as t)
      }.map(TagRecord(t.resultName))
        .list()
        .apply()
    }
}
