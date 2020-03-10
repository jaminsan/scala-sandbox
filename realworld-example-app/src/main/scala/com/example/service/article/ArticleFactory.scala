package com.example.service.article

import com.example.model.{Article, IdGenerator, NewArticle, Tag}

trait ArticleFactory extends ArticleValidator {

  val idGenerator: IdGenerator
  val slugifier:   Slugifier

  def newArticle(userId: String, newArticle: NewArticle, tags: List[Tag]): Article =
    article(userId, newArticle, tags)

  private def article(userId: String, newArticle: NewArticle, tags: List[Tag]): Article = {
    val articleId = idGenerator.newId()

    Article(
      articleId   = articleId.value,
      slug        = slugifier.slugify(newArticle.title),
      title       = newArticle.title,
      description = newArticle.description,
      body        = newArticle.body,
      createdAt   = articleId.generatedAt,
      updatedAt   = articleId.generatedAt,
      tagIds      = tags.map(_.tagId),
      authorId    = userId
    )
  }
}
