package com.example

import scalatags.Text.all._

object MinimalApplication extends cask.MainRoutes {

  @cask.get("/")
  def hello() = {
    "Hello World!"
    html(
      head(
        link(
          rel := "stylesheet",
          href := "https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
        )
      ),
      body(
        h1("Scala Chat!"),
        hr,
        div(
          p(b("alice"), " ", "Hello World!"),
          p(b("bob"), " ", "I am cow, hear me moo"),
          p(b("charlie"), " ", "I weigh twice as much as you")
        ),
        hr,
        div(
          input(`type` := "text", placeholder := "User name", width := "20%"),
          input(`type` := "text", placeholder := "Please write a message!", width := "80%")
        )
      )
    ).render
  }

  @cask.post("/do-thing")
  def doThing(request: cask.Request) =
    new String(request.readAllBytes()).reverse

  initialize()
}
