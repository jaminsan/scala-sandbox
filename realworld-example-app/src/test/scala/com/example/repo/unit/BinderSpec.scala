package com.example.repo.unit

import java.sql.ResultSet

import org.scalatest.OptionValues._
import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.must.Matchers
import scalikejdbc._

class BinderSpec extends FixtureAnyFlatSpec with PreparingTables with AutoRollback with Matchers {

  import BinderSpec._
  import EmployeeTable._

  implicit val employeeIdParameterBinder = ParameterBinderFactory[EmployeeId] { value => (stmt, idx) =>
    stmt.setString(idx, value.value)
  }

  implicit val employeeNameParameterBinder = ParameterBinderFactory[EmployeeName] { value => (stmt, idx) =>
    stmt.setString(idx, value.value)
  }

  implicit val employeeIdTypeBinder = new TypeBinder[EmployeeId] {
    override def apply(rs: ResultSet, columnIndex: Int):    EmployeeId = EmployeeId(rs.getString(columnIndex))
    override def apply(rs: ResultSet, columnLabel: String): EmployeeId = EmployeeId(rs.getString(columnLabel))
  }

  implicit val employeeNameTypeBinder = new TypeBinder[EmployeeName] {
    override def apply(rs: ResultSet, columnIndex: Int):    EmployeeName = EmployeeName(rs.getString(columnIndex))
    override def apply(rs: ResultSet, columnLabel: String): EmployeeName = EmployeeName(rs.getString(columnLabel))
  }

  "Binders" should "implicitly convert between custom object and stmt value" in { implicit s =>
    applyUpdate {
      insertInto(EmployeeTable)
        .namedValues(
          column.employeeId -> EmployeeId("employeeId"),
          column.name       -> EmployeeName("employeeName")
        )
    }

    val record =
      withSQL(selectFrom(EmployeeTable as e))
        .map(
          ws =>
            EmployeeRecord(
              employeeId = ws.get[EmployeeId](e.resultName.employeeId),
              name       = ws.get[EmployeeName](e.resultName.name),
          ))
        .single().apply()

    record.value.employeeId mustBe EmployeeId("employeeId")
    record.value.name mustBe EmployeeName("employeeName")
  }
}

object BinderSpec {

  case class EmployeeId(value: String) extends AnyVal

  case class EmployeeName(value: String) extends AnyVal

  case class EmployeeRecord(employeeId: EmployeeId, name: EmployeeName)

  object EmployeeRecord {

    def apply(e: ResultName[EmployeeRecord])(ws: WrappedResultSet): EmployeeRecord =
      EmployeeRecord(
        employeeId = EmployeeId(ws.string(e.employeeId)),
        name       = EmployeeName(ws.string(e.name))
      )
  }

  object EmployeeTable extends SQLSyntaxSupport[EmployeeRecord] {

    override val tableName = "employee"

    override def columnNames = Seq("employee_id", "name")

    val e = syntax("e")
  }
}
