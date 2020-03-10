package com.example.repo

import com.example.db.article.{TagDao, TagRecord}
import com.example.model.Tag
import scalikejdbc.DBSession

import scala.concurrent.{ExecutionContext, Future}

class TagRepo(tagDao: TagDao, implicit val ec: ExecutionContext) {

  def findByNames(names: List[String])(implicit session: DBSession): Future[List[Tag]] =
    tagDao.findByNames(names).map(_.map(_.toModel))

  def saveAll(tags: List[Tag])(implicit session: DBSession): Future[List[Int]] =
    tagDao.insertAll(TagRecord(tags))
}
