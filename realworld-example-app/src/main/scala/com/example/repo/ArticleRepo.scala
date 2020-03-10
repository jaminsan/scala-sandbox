package com.example.repo

import com.example.db.article._
import com.example.model.{Article, IdGenerator}
import scalikejdbc.DBSession

import scala.concurrent.{ExecutionContext, Future}

class ArticleRepo(
  private val articleDao:               ArticleDao,
  private val tagDao:                   TagDao,
  private val articleTagAssociationDao: ArticleTagAssociationDao,
  private val idGenerator:              IdGenerator,
  implicit val ec:                      ExecutionContext
) {

  // format: off
  def save(article: Article)(implicit session: DBSession): Future[Article] =
    for {
      _               <- articleDao.insert(ArticleRecord(article))
      tagAssociations = article.tagIds.map(tagId => ArticleTagAssociationRecord(idGenerator.newId().value, article.articleId, tagId))
      _               <- articleTagAssociationDao.insertAll(tagAssociations)
    } yield article
  // format: on

  def findBySlug(slug: String)(implicit session: DBSession): Future[Article] =
    for {
      article         <- articleDao.findBySlug(slug)
      tagAssociations <- articleTagAssociationDao.findByArticleId(article.articleId)
      tags            <- tagDao.findByIds(tagAssociations.map(_.tagId))
    } yield ArticleRecord.toModel(article, tags)
}
