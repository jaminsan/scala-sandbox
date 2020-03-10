package com.example.db.article

import scalikejdbc._

case class ArticleTagAssociationRecord(articleTagAssociationId: String, articleId: String, tagId: String)

object ArticleTagAssociationRecord {

  def apply(ata: SyntaxProvider[ArticleTagAssociationRecord])(ws: WrappedResultSet): ArticleTagAssociationRecord =
    apply(ata.resultName)(ws)

  def apply(ata: ResultName[ArticleTagAssociationRecord])(ws: WrappedResultSet): ArticleTagAssociationRecord =
    ArticleTagAssociationRecord(
      articleTagAssociationId = ws.string(ata.articleTagAssociationId),
      articleId = ws.string(ata.articleId),
      tagId = ws.string(ata.tagId)
    )
}

object ArticleTagAssociationTable extends SQLSyntaxSupport[ArticleTagAssociationRecord] {

  override def tableName: String = "article_tag_association"

  val ata = syntax("ata")
}
