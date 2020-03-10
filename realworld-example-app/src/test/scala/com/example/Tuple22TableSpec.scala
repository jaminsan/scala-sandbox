package com.example

import org.scalatest.Inspectors.forAll
import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.must.Matchers
import scalikejdbc._

class Tuple22TableSpec extends FixtureAnyFlatSpec with PreparingTables with AutoRollback with Matchers {

  import LargeTable._

  behavior of "over 22 columns db access"

  it should "insert then select records" in { implicit s =>
    insertLargeRecord(1)
    insertLargeRecord(2)
    insertLargeRecord(3)

    val records = withSQL(select.from(LargeTable as l)).map(LargeRecord(l.resultName)).list().apply()

    records must have length 3
    forAll(records) { record =>
      record.column1 mustBe "column1"
      record.column2 mustBe "column2"
      record.column3 mustBe "column3"
      record.column4 mustBe "column4"
      record.column5 mustBe "column5"
      record.column6 mustBe "column6"
      record.column7 mustBe "column7"
      record.column8 mustBe "column8"
      record.column9 mustBe "column9"
      record.column10 mustBe "column10"
      record.column11 mustBe "column11"
      record.column12 mustBe "column12"
      record.column13 mustBe "column13"
      record.column14 mustBe "column14"
      record.column15 mustBe "column15"
      record.column16 mustBe "column16"
      record.column17 mustBe "column17"
      record.column18 mustBe "column18"
      record.column19 mustBe "column19"
      record.column20 mustBe "column20"
      record.column21 mustBe "column21"
      record.column22 mustBe "column22"
      record.column23 mustBe "column23"
      record.column24 mustBe "column24"
    }
  }

  def insertLargeRecord(index: Int)(implicit s: DBSession): Unit =
    applyUpdate(
      insertInto(LargeTable)
        .namedValues(
          column.largeId  -> s"largeId$index",
          column.column1  -> "column1",
          column.column2  -> "column2",
          column.column3  -> "column3",
          column.column4  -> "column4",
          column.column5  -> "column5",
          column.column6  -> "column6",
          column.column7  -> "column7",
          column.column8  -> "column8",
          column.column9  -> "column9",
          column.column10 -> "column10",
          column.column11 -> "column11",
          column.column12 -> "column12",
          column.column13 -> "column13",
          column.column14 -> "column14",
          column.column15 -> "column15",
          column.column16 -> "column16",
          column.column17 -> "column17",
          column.column18 -> "column18",
          column.column19 -> "column19",
          column.column20 -> "column20",
          column.column21 -> "column21",
          column.column22 -> "column22",
          column.column23 -> "column23",
          column.column24 -> "column24",
        )
    )

}

case class LargeRecord(
  largeId:  String,
  column1:  String,
  column2:  String,
  column3:  String,
  column4:  String,
  column5:  String,
  column6:  String,
  column7:  String,
  column8:  String,
  column9:  String,
  column10: String,
  column11: String,
  column12: String,
  column13: String,
  column14: String,
  column15: String,
  column16: String,
  column17: String,
  column18: String,
  column19: String,
  column20: String,
  column21: String,
  column22: String,
  column23: String,
  column24: String
)

object LargeRecord {

  def apply(l: ResultName[LargeRecord])(ws: WrappedResultSet): LargeRecord =
    LargeRecord(
      largeId  = ws.string(l.largeId),
      column1  = ws.string(l.column1),
      column2  = ws.string(l.column2),
      column3  = ws.string(l.column3),
      column4  = ws.string(l.column4),
      column5  = ws.string(l.column5),
      column6  = ws.string(l.column6),
      column7  = ws.string(l.column7),
      column8  = ws.string(l.column8),
      column9  = ws.string(l.column9),
      column10 = ws.string(l.column10),
      column11 = ws.string(l.column11),
      column12 = ws.string(l.column12),
      column13 = ws.string(l.column13),
      column14 = ws.string(l.column14),
      column15 = ws.string(l.column15),
      column16 = ws.string(l.column16),
      column17 = ws.string(l.column17),
      column18 = ws.string(l.column18),
      column19 = ws.string(l.column19),
      column20 = ws.string(l.column20),
      column21 = ws.string(l.column21),
      column22 = ws.string(l.column22),
      column23 = ws.string(l.column23),
      column24 = ws.string(l.column24)
    )
}

object LargeTable extends SQLSyntaxSupport[LargeRecord] {

  override def tableName: String = "large"

  val l = syntax("l")
}
