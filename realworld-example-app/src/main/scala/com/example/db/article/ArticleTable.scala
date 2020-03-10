package com.example.db.article

import java.time.Instant

import com.example.model.Article
import scalikejdbc._
import io.scalaland.chimney.dsl._

case class ArticleRecord(
  articleId:   String,
  slug:        String,
  title:       String,
  description: String,
  body:        String,
  createdAt:   Instant,
  updatedAt:   Instant,
  authorId:    String
)

object ArticleRecord {

  def apply(a: SyntaxProvider[ArticleRecord])(ws: WrappedResultSet): ArticleRecord =
    apply(a.resultName)(ws)

  def apply(a: ResultName[ArticleRecord])(ws: WrappedResultSet): ArticleRecord =
    ArticleRecord(
      articleId   = ws.string(a.articleId),
      slug        = ws.string(a.slug),
      title       = ws.string(a.title),
      description = ws.string(a.description),
      body        = ws.string(a.body),
      createdAt   = ws.offsetDateTime(a.createdAt).toInstant,
      updatedAt   = ws.offsetDateTime(a.updatedAt).toInstant,
      authorId    = ws.string(a.authorId)
    )

  def apply(article: Article): ArticleRecord =
    article.transformInto[ArticleRecord]

  def toModel(article: ArticleRecord, tags: List[TagRecord]): Article =
    article
      .into[Article]
      .withFieldComputed(_.tagIds, _ => tags.map(_.tagId))
      .transform
}

object ArticleTable extends SQLSyntaxSupport[ArticleRecord] {

  override def tableName: String = "article"

  val a = syntax("a")
}
