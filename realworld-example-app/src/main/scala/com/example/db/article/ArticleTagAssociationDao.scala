package com.example.db.article

import com.example.db.futureUpdate
import scalikejdbc._

import scala.concurrent.{ExecutionContext, Future}

class ArticleTagAssociationDao(implicit ec: ExecutionContext) {

  import ArticleTagAssociationTable._

  def insertAll(records: List[ArticleTagAssociationRecord])(implicit session: DBSession): Future[List[Int]] =
    Future.sequence(records.map(insert))

  def insert(record: ArticleTagAssociationRecord)(implicit session: DBSession): Future[Int] =
    futureUpdate {
      insertInto(ArticleTagAssociationTable)
        .namedValues(
          column.articleTagAssociationId -> record.articleTagAssociationId,
          column.articleId               -> record.articleId,
          column.tagId                   -> record.tagId
        )
    }

  def findByArticleId(articleId: String)(implicit session: DBSession): Future[List[ArticleTagAssociationRecord]] =
    Future {
      withSQL {
        select
          .from(ArticleTagAssociationTable as ata)
          .where
          .eq(ata.articleId, articleId)
      }.map(ArticleTagAssociationRecord(ata))
        .list()
        .apply()
    }

  def delete(articleTagAssociationId: Long)(implicit session: DBSession): Future[Int] =
    futureUpdate {
      deleteFrom(ArticleTagAssociationTable).where
        .eq(ata.articleTagAssociationId, articleTagAssociationId)
    }
}
