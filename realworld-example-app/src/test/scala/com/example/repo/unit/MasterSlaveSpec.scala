package com.example.repo.unit

import com.dimafeng.testcontainers.{Container, ForAllTestContainer, MultipleContainers, MySQLContainer}
import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.must.Matchers
import scalikejdbc._

class MasterSlaveSpec
  extends FixtureAnyFlatSpec
  with ForAllTestContainer
  with AutoRollback
  with ScalikeLogging
  with Matchers {

  import com.example.repo.unit.MasterSlaveSpec.{EmployeeRecord, EmployeeTable}
  import EmployeeTable._

  val master = new MySQLContainer()
  val slave  = new MySQLContainer()
  override def container: Container = MultipleContainers(master, slave)

  val poolNameMasterSlave = Symbol("master-slave")
  val poolNameSlave       = Symbol("slave")

  override def afterStart(): Unit = {
    Class.forName("com.mysql.cj.jdbc.Driver")
    val masterUrl = s"${master.containerIpAddress}:${master.mappedPort(3306)}"
    val slaveUrl  = s"${slave.containerIpAddress}:${slave.mappedPort(3306)}"

    ConnectionPool.add(
      poolNameMasterSlave,
      s"jdbc:mysql:replication://$masterUrl,$slaveUrl/test",
      "test",
      "test"
    )

    ConnectionPool.add(
      poolNameSlave,
      slave.jdbcUrl,
      "test",
      "test"
    )
  }

  override def db = NamedDB(poolNameMasterSlave).toDB()

  "readonly session" should "access only slave" in { implicit s =>
    NamedDB(poolNameSlave) autoCommit { implicit s =>
      createEmployeeTable
      insertEmployee("slaveEmployeeId1", "slaveName1")
    }
    createEmployeeTable
    insertEmployee("masterEmployeeId1", "masterName1")
    insertEmployee("masterEmployeeId2", "masterName2")

    // readOnly true だと slave にいく
    db readOnly { implicit s =>
      val records = selectEmployees()
      records must have length 1
      records.head.employeeId mustBe "slaveEmployeeId1"
    }

    // readOnly false だと master にいく
    val records = selectEmployees()
    records must have length 2
    records.head.employeeId mustBe "masterEmployeeId1"
  }

  def createEmployeeTable()(implicit s: DBSession) =
    sql"""create table employee (employee_id varchar(30), name varchar(30), primary key (employee_id))"""
      .execute()
      .apply()

  def insertEmployee(id: String, name: String)(implicit s: DBSession) =
    withSQL(insertInto(EmployeeTable).namedValues(column.employeeId -> id, column.name -> name)).execute().apply()

  def selectEmployees()(implicit s: DBSession): List[EmployeeRecord] =
    withSQL(select.from(EmployeeTable as e)).map(EmployeeRecord(e.resultName)).list().apply()
}

object MasterSlaveSpec {

  case class EmployeeRecord(employeeId: String, name: String)

  object EmployeeRecord {

    def apply(e: ResultName[EmployeeRecord])(ws: WrappedResultSet): EmployeeRecord =
      EmployeeRecord(employeeId = ws.string(e.employeeId), name = ws.string(e.name))
  }

  object EmployeeTable extends SQLSyntaxSupport[EmployeeRecord] {

    override def connectionPoolName = Symbol("master-slave")

    override val tableName = "employee"

    override def columnNames = Seq("employee_id", "name")

    val e = syntax("e")
  }
}
