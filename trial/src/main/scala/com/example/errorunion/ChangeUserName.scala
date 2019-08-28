package com.example.errorunion

import com.example.errorunion.ChangeUserPassword.ChangeUserPasswordError
import com.example.errorunion.UserError.{InvalidPasswordLength, PasswordPolicyError, PasswordSameAsOld}
import com.sun.tools.javac.util.FatalError

object TypeSupport {

  type ¬[A] = A => Nothing
  type ¬¬[A] = ¬[¬[A]]
  type ∨[T, U] = ¬[¬[T] with ¬[U]]
  type |∨|[T, U] = {type λ[X] = ¬¬[X] <:< (T ∨ U)}
}

case class User(userId: Long, name: String, age: Int, password: String) {
  self =>

  def changePassword(password: String): Either[PasswordPolicyError, User] =
    invalidPasswordLength.orElse(passwordSameAsOld).lift.apply(password) match {
      case Some(e: PasswordPolicyError) => Left(e)
      case _ => Right(copy(password = password))
    }

  private def invalidPasswordLength: PartialFunction[String, PasswordPolicyError] = {
    case s if s.length < 8 => InvalidPasswordLength
  }

  private def passwordSameAsOld: PartialFunction[String, PasswordPolicyError] = {
    case s if s == self.password => PasswordSameAsOld
  }
}

object UserError {

  sealed trait PasswordPolicyError

  case object InvalidPasswordLength extends PasswordPolicyError

  case object PasswordSameAsOld extends PasswordPolicyError

}

trait UserRepository {

  def find(id: Long): Option[User] =
    if (id == 1) Some(User(1, "hoge", 20, "password")) else None

  def store(e: User): Either[FatalError, Unit] =
    Right(println(s"store user $e"))

  def unSubscribers(): List[User] = Nil
}

class ChangeUserPassword {

  import TypeSupport.|∨|

  type Repository = {
    def find(id: Long): Option[User]
    def store(e: User): Either[FatalError, Unit]
  }

  type UseCaseError = (ChangeUserPasswordError |∨| PasswordPolicyError)#λ

  def exec(userId: Long,
           password: String,
           userRepo: Repository
          ): Either[UseCaseError, Unit] = ???
  // BaseException を Try で囲ってから UseCaseError or Unit に変換する必要がありそう
//    for {
//      user <- userRepo.find(userId).toRight(UserNotFound)
//      newUser <- user.changePassword(password)
//      _ <- userRepo.store(newUser)
//    } yield ()
}

object ChangeUserPassword {

  sealed trait ChangeUserPasswordError

  case object UserNotFound extends ChangeUserPasswordError

  case object FatalError extends ChangeUserPasswordError

}

class Main(useCase: ChangeUserPassword, userRepository: UserRepository) {

  def run(): Unit = {
    useCase.exec(1, "hogehoge", userRepository)
  }
}

object Main extends App {

  override def main(args: Array[String]): Unit = {
    new Main(new ChangeUserPassword(), new UserRepository {})
  }
}