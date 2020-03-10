package com.example.model

import java.time.Instant

case class Article(
  articleId:   String,
  slug:        String,
  title:       String,
  description: String,
  body:        String,
  createdAt:   Instant,
  updatedAt:   Instant,
  tagIds:      List[String],
  authorId:    String
)
