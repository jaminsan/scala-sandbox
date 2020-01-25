package com.example.dbclient

import org.scalatest.{FixtureTestSuite, Outcome}
import scalikejdbc.{ConnectionPool, DB, DBSession, LoanPattern, SettingsProvider}

trait AutoRollback extends LoanPattern { self : FixtureTestSuite =>

  override type FixtureParam = DBSession

  protected [this] def settingsProvider: SettingsProvider =
    SettingsProvider.default

  def db(): DB =
    DB(conn = ConnectionPool.borrow(), settingsProvider = settingsProvider)

  def fixture(implicit session: DBSession): Unit = {}

  override def withFixture(test: OneArgTest): Outcome =
    using(db()) {db =>
      try {
        db.begin()
        db.withinTx { implicit session =>
          fixture(session)
        }
        withFixture(test.toNoArgTest(db.withinTxSession()))
      } finally {
        db.rollbackIfActive()
      }
    }

}
