package com.example.dbclient.scalike

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.FixtureAnyWordSpec
import scalikejdbc.{ConnectionPool, DBSession, _}

class ScalikeSpec extends FixtureAnyWordSpec with AutoRollback with Matchers {

  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:h2:mem:yetanother;INIT=RUNSCRIPT FROM 'classpath:ddl.sql'", "sa", "sa")

  "select" should {

    "return all user" in { implicit session: DBSession =>
      sql"insert into test.user (name, age) values ('hiraku', 26)".update().apply()

      val users = sql"select * from test.user"
        .map(
          rs =>
            User(
              id   = rs.long("id"),
              name = rs.string("name"),
              age  = rs.int("age")
          ))
        .list()
        .apply()

      users.head.id mustBe 1
      users.head.name mustBe "hiraku"
      users.head.age mustBe 26
    }

    "hoge" in { implicit session: DBSession =>
      import UserDao._
      withSQL {
        select
          .from(UserDao as u)
          .where
          .eq(u.name, "mishima")
      }.map(rs => rs.get(1)).single().apply()
    }
  }

  case class User(id: Long, name: String, age: Int)

  object UserDao extends SQLSyntaxSupport[User] {

    override val tableName = "user"

    val u = syntax("u")

    override def columnNames: Seq[String] = Seq("name", "age")
  }
}
