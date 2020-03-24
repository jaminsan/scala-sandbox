package com.example.repo.unit

import com.dimafeng.testcontainers.{ForAllTestContainer, MultipleContainers, MySQLContainer}
import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.must.Matchers
import scalikejdbc._

class StreamingSpec
  extends FixtureAnyFlatSpec
  with ForAllTestContainer
  with AutoRollback
  with ScalikeLogging
  with Matchers {

  import com.example.repo.unit.StreamingSpec.EmployeeTable
  import EmployeeTable._

  override val container = MySQLContainer()

  val poolName = Symbol("streaming")

  override def afterStart(): Unit = {
    Class.forName("com.mysql.cj.jdbc.Driver")
    ConnectionPool.add(
      poolName,
      s"${container.jdbcUrl}?useCursorFetch=true",
      "test",
      "test"
    )
  }

  override def db(): DB = NamedDB(poolName).toDB()

  "execute query with fetchSize" should "fetch query result from DB to memory per fetchSize" in { implicit s =>
    createEmployeeTable()
    (1 to 10).foreach(idx => insertEmployee(s"id$idx", s"name$idx"))

    // NOTE: com.mysql.cj.protocol.a.result.ResultsetRowsCursor#next の return などにブレイクポイントを張って
    // this.fetchedRows の件数と fetchSize 回 next が呼ばれると中身が変わることを確認する
    sql"""select * from employee"""
      .fetchSize(4)
      .foreach { ws =>
        println(ws.string(1))
      }

    // NOTE: fetchSize ごとに DB からレコードをメモリに持ってくるが apply が呼ばれたときに全件読み込まれるぽい
    // 順次読み込みたいなら foreach 使うか scalikejdbc-streams を検討するのが良さそう
    sql"""select * from employee"""
      .fetchSize(4)
      .map(_.string(1))
      .iterable()
      .apply() // ここで fetchSize ごとに全件読み込み
      .to(LazyList)
      .collectFirst { case "id6" => "success" }
      .foreach(println)
  }

  def createEmployeeTable()(implicit s: DBSession) =
    sql"""create table employee (employee_id varchar(30), name varchar(30), primary key (employee_id))"""
      .execute()
      .apply()

  def insertEmployee(id: String, name: String)(implicit s: DBSession) =
    withSQL(insertInto(EmployeeTable).namedValues(column.employeeId -> id, column.name -> name)).execute().apply()

}

object StreamingSpec {

  case class EmployeeRecord(employeeId: String, name: String)

  object EmployeeRecord {

    def apply(e: ResultName[EmployeeRecord])(ws: WrappedResultSet): EmployeeRecord =
      EmployeeRecord(
        employeeId = ws.string(e.employeeId),
        name       = ws.string(e.name)
      )
  }

  object EmployeeTable extends SQLSyntaxSupport[EmployeeRecord] {

    override val tableName = "employee"

    override def columnNames = Seq("employee_id", "name")

    val e = syntax("e")
  }
}
