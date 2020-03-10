package com.example.db.article

import com.example.db.futureUpdate
import com.example.exception.NotFoundModelException
import scalikejdbc._

import scala.concurrent.{ExecutionContext, Future}

class ArticleDao(implicit val ec: ExecutionContext) {

  import ArticleTable._

  def insert(record: ArticleRecord)(implicit session: DBSession): Future[Int] =
    futureUpdate {
      insertInto(ArticleTable)
        .namedValues(
          column.articleId   -> record.articleId,
          column.slug        -> record.slug,
          column.title       -> record.title,
          column.description -> record.description,
          column.body        -> record.body,
          column.createdAt   -> record.createdAt,
          column.updatedAt   -> record.updatedAt,
          column.authorId    -> record.authorId
        )
    }

  def findBySlugOption(slug: String)(implicit session: DBSession): Future[Option[ArticleRecord]] =
    Future {
      withSQL {
        select
          .from(ArticleTable as a)
          .where
          .eq(a.slug, slug)
      }.map(ArticleRecord(a))
        .single()
        .apply()
    }

  def findBySlug(slug: String)(implicit session: DBSession): Future[ArticleRecord] =
    findBySlugOption(slug)
      .map(_.getOrElse(throw new NotFoundModelException(s"Article not found for slug=$slug")))

  def findById(articleId: String)(implicit session: DBSession): Future[ArticleRecord] =
    Future {
      withSQL {
        select.from(ArticleTable as a).where.eq(a.articleId, articleId)
      }.map(ArticleRecord(a))
        .single()
        .apply()
        .getOrElse(throw new NotFoundModelException(s"Article not found for articleId=$articleId"))
    }
}
