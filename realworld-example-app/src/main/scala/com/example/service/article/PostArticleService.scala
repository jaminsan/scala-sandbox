package com.example.service.article

import cats.implicits._
import com.example.model.{Article, NewArticle}
import com.example.repo.{ArticleRepo, ProfileRepo, TagRepo}
import com.example.service.dsl._
import scalikejdbc.DBSession

import scala.concurrent.{ExecutionContext, Future}

case class PostArticleCommand(userId: String, newArticle: NewArticle)

trait PostArticleService {

  val profileRepo:      ProfileRepo
  val articleRepo:      ArticleRepo
  val tagRepo:          TagRepo
  val articleFactory:   ArticleFactory
  val tagFactory:       TagFactory
  val tagValidator:     TagValidator
  val articleValidator: ArticleValidator
  implicit val ec:      ExecutionContext

  def exec(command: PostArticleCommand)(implicit session: DBSession): Future[Article] =
    for {
      _            <- failIfInvalid { validate(command) }
      profile      <- profileRepo.findByUserId(command.userId, None)
      existingTags <- tagRepo.findByNames(command.newArticle.tags)
      articleTags  <- tagFactory.newTags(command.newArticle.tags, existingTags).asFuture
      article      <- articleFactory.newArticle(profile.userId, command.newArticle, articleTags).asFuture
      _            <- articleRepo.save(article)
      _            <- tagRepo.saveAll(articleTags diff existingTags)
    } yield article

  private def validate(command: PostArticleCommand): PropertyValidationFuture[PostArticleCommand] =
    (
      articleValidator.validateTitle(command.newArticle.title),
      articleValidator.validateTags(command.newArticle.tags),
      tagValidator.validateNames(command.newArticle.tags)
    ).mapN((_, _, _) => command).toEither.asFuture
}
