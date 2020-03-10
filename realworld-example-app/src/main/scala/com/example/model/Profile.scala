package com.example.model

import com.example.db.user.UserRecord

case class Profile(userId: String, userName: String, bio: Option[String], image: Option[String], following: Boolean)

object Profile {

  def apply(user: UserRecord, following: Boolean): Profile =
    Profile(userId = user.userId, userName = user.name, bio = user.bio, image = user.image, following = following)
}
