package com.example.repo

import java.time.OffsetDateTime

import com.example.db.article._
import com.example.model.{Article, IdGenerator, ModelId}
import com.example.{AutoRollback, FutureOps, PreparingTables}
import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.must.Matchers

class ArticleRepoSpec
  extends org.scalatest.flatspec.FixtureAnyFlatSpec
  with PreparingTables
  with AutoRollback
  with Matchers
  with MockFactory {

  implicit val ec = scala.concurrent.ExecutionContext.Implicits.global

  behavior of "ArticleRepo"

  it should "save article" in { implicit session =>
    val article =
      Article(
        articleId   = "articleId",
        slug        = "slug",
        title       = "title",
        description = "description",
        body        = "body",
        createdAt   = OffsetDateTime.now().toInstant,
        updatedAt   = OffsetDateTime.now().toInstant,
        tagIds      = List("tagId1", "tagId2", "tagId3"),
        authorId    = "authorId"
      )

    new TagDao()
      .insertAll(
        List(
          TagRecord(tagId = "tagId1", name = "name1"),
          TagRecord(tagId = "tagId2", name = "name2"),
          TagRecord(tagId = "tagId3", name = "name3")
        ))
      .await()

    new context {
      // format: off
      (idGeneratorMock.newId: () => ModelId).expects().returning(ModelId("ataId1", OffsetDateTime.now().toInstant)).once()
      (idGeneratorMock.newId: () => ModelId).expects().returning(ModelId("ataId2", OffsetDateTime.now().toInstant)).once()
      (idGeneratorMock.newId: () => ModelId).expects().returning(ModelId("ataId3", OffsetDateTime.now().toInstant)).once()
      // format: on

      val result = sut.save(article).await()

      val savedArticle = sut.findBySlug(article.slug).await()
      savedArticle mustBe article
    }
  }

  trait context {
    val idGeneratorMock          = mock[IdGenerator]
    val articleDao               = new ArticleDao()
    val tagDao                   = new TagDao()
    val articleTagAssociationDao = new ArticleTagAssociationDao()

    val sut =
      new ArticleRepo(
        articleDao               = articleDao,
        tagDao                   = tagDao,
        articleTagAssociationDao = articleTagAssociationDao,
        idGenerator              = idGeneratorMock,
        ec                       = ec
      )
  }
}
