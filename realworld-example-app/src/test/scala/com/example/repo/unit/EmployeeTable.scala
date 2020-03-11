package com.example.repo.unit

import scalikejdbc._

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
