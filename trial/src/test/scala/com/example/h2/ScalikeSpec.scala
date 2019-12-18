package com.example.h2

import org.scalatest.{MustMatchers, fixture}
import scalikejdbc.scalatest.AutoRollback
import scalikejdbc.{ConnectionPool, DBSession, _}

class ScalikeSpec extends fixture.WordSpec with AutoRollback with MustMatchers {

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
          )).list().apply()

      users.head.id mustBe 1
      users.head.name mustBe "hiraku"
      users.head.age mustBe 26
    }
  }

  case class User(id: Long, name: String, age: Int)
}
