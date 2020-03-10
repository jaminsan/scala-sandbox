package com.example.service.article

import com.example.model.{IdGenerator, Tag}

trait TagFactory extends TagValidator {

  val idGenerator: IdGenerator

  def newTags(tags: List[String], existingTags: List[Tag]): List[Tag] = {
    val newTagNames = tags diff existingTags.map(_.name)
    existingTags ++ newTagNames.map(newTag)
  }

  def newTag(name: String): Tag = {
    val tagId = idGenerator.newId()

    Tag(
      tagId = tagId.value,
      name  = name
    )
  }
}
