package com.example

import scalikejdbc._
import scalikejdbc.config.DBs

trait PreparingTables {

  DBs.setupAll()

  try {
    DB autoCommit { implicit s =>
      SQL("""create table article (
          |article_id varchar(30),
          |slug varchar(30),
          |title varchar(30),
          |description varchar(50),
          |body varchar(200),
          |created_at timestamp,
          |updated_at timestamp,
          |author_id varchar(30),
          |primary key (article_id)
          |)""".stripMargin).execute().apply()
    }
  } catch { case _: Exception => }

  try {
    DB autoCommit { implicit s =>
      SQL("""create table tag (
          |tag_id varchar(30),
          |name varchar(30),
          |primary key (tag_id)
          |)""".stripMargin).execute().apply()
    }
  } catch { case _: Exception => }

  try {
    DB autoCommit { implicit s =>
      SQL("""create table article_tag_association (
          |article_tag_association_id varchar(30),
          |article_id varchar(30),
          |tag_id varchar(30),
          |primary key (article_tag_association_id)
          |)""".stripMargin).execute().apply()
    }
  } catch { case _: Exception => }
}
