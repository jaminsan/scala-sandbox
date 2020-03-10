package com.example.db.article

import com.example.model.Tag
import scalikejdbc._
import io.scalaland.chimney.dsl._

case class TagRecord(tagId: String, name: String) {

  def toModel: Tag = TagRecord.toModel(this)
}

object TagRecord {

  def apply(t: SyntaxProvider[TagRecord])(ws: WrappedResultSet): TagRecord =
    apply(t.resultName)(ws)

  def apply(t: ResultName[TagRecord])(ws: WrappedResultSet): TagRecord =
    TagRecord(
      tagId = ws.string(t.tagId),
      name  = ws.string(t.name)
    )

  def apply(tags: List[Tag]): List[TagRecord] =
    tags.map(_.transformInto[TagRecord])

  def toModel(tagRecord: TagRecord): Tag =
    tagRecord.transformInto[Tag]
}

object TagTable extends SQLSyntaxSupport[TagRecord] {

  override def tableName: String = "tag"

  val t = syntax("t")
}
