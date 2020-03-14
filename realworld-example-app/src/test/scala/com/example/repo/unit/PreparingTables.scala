package com.example.repo.unit

import scalikejdbc.config.DBs
import scalikejdbc.{DB, SQL}

trait PreparingTables extends ScalikeLogging {

  DBs.setup(Symbol("default"))

  try {
    DB autoCommit { implicit s =>
      SQL("""create table if not exists article (
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
      SQL("""create table if not exists tag (
          |tag_id varchar(30),
          |name varchar(30),
          |primary key (tag_id)
          |)""".stripMargin).execute().apply()
    }
  } catch { case _: Exception => }

  try {
    DB autoCommit { implicit s =>
      SQL("""create table if not exists article_tag_association (
          |article_tag_association_id varchar(30),
          |article_id varchar(30),
          |tag_id varchar(30),
          |primary key (article_tag_association_id)
          |)""".stripMargin).execute().apply()
    }
  } catch { case _: Exception => }

  try {
    DB autoCommit { implicit s =>
      SQL("""create table if not exists employee (
          |employee_id varchar(30),
          |name varchar(30),
          |primary key (employee_id)
          |)""".stripMargin).execute().apply()
    }
  } catch { case _: Exception => }

  try {
    DB autoCommit { implicit s =>
      SQL("""create table if not exists large (
          |large_id varchar(30),
          |column1 varchar(30),
          |column2 varchar(30),
          |column3 varchar(30),
          |column4 varchar(30),
          |column5 varchar(30),
          |column6 varchar(30),
          |column7 varchar(30),
          |column8 varchar(30),
          |column9 varchar(30),
          |column10 varchar(30),
          |column11 varchar(30),
          |column12 varchar(30),
          |column13 varchar(30),
          |column14 varchar(30),
          |column15 varchar(30),
          |column16 varchar(30),
          |column17 varchar(30),
          |column18 varchar(30),
          |column19 varchar(30),
          |column20 varchar(30),
          |column21 varchar(30),
          |column22 varchar(30),
          |column23 varchar(30),
          |column24 varchar(30),
          |primary key (large_id)
          |)""".stripMargin).execute().apply()
    }
  } catch { case _: Exception => }
}
