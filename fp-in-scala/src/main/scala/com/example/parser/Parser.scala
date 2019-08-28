package com.example.parser

import scala.util.matching.Regex


// 型パラメータだけで代数法則を定義できる！
// ParseError とか Parser 具体的な定義はここでは必要ない
// 何かしらの法則とかを書き出すだけ！うける！だからライブラリを見てみると型パラメータだけで実装できるのか
// こういう過程を経ているというのが意識できれば何となく雰囲気掴める
trait Parsers[ParseError, Parser[+ _]] {
  self =>

  def run[A](p: Parser[A])(input: String): Either[ParseError, A]

  // 最終的に実装がないやつがプリミティブ！！！
  // パーサを返す
  def succeed[A](a: A): Parser[A]

  // パースした部分の文字列を返す
  def slice[A](p: Parser[A]): Parser[String]

  def or[A](s1: Parser[A], s2: => Parser[A]): Parser[A]

  // 文字列にマッチした文字列を返す
  implicit def string(s: String): Parser[String]

  // 正規表現にマッチした文字列を返す
  implicit def regex(r: Regex): Parser[String]

  def flatMap[A, B](p: Parser[A])(f: A => Parser[B]): Parser[B]

  //  implicit def operators[A](p: Parser[A]): ParserOps[A] = ParserOps[A](p)

  implicit def asStringParser[A](a: A)(implicit f: A => Parser[String]): ParserOps[String] = ParserOps(f(a))

  // 文字にマッチした文字を返す
  def char(c: Char): Parser[Char] =
    string(c.toString) map (_.charAt(0))

  def many[A](p: Parser[A]): Parser[List[A]] =
    map2(p, many(p))(_ :: _) or succeed(List())

  def map[A, B](a: Parser[A])(f: A => B): Parser[B] =
    flatMap(a)(aa => succeed(f(aa)))

  def product[A, B](p: Parser[A], p2: => Parser[B]): Parser[(A, B)] =
  //    for {
  //      a <- p
  //      b <- p2
  //    } yield (a, b)
    flatMap(p)(a => map(p2)(b => (a, b)))

  def map2[A, B, C](p1: Parser[A], p2: => Parser[B])(f: (A, B) => C): Parser[C] =
  //    map(product(p1, p2))(f.tupled)
    flatMap(p1)(a =>
      map(p2)(b => f(a, b))
    )

  //  for {
  //    a <- p1
  //    b <- p2
  //  } yield f(a, b)


  def many1[A](p: Parser[A]): Parser[List[A]] =
    map2(p, many(p))(_ :: _)

  def listOfN[A](n: Int, p: Parser[A]): Parser[List[A]] =
    if (n <= 0) succeed(List()) else map2(p, listOfN(n - 1, p))(_ :: _)

  implicit class ParserOps[A](p: Parser[A]) {
    def |[B >: A](p2: Parser[B]): Parser[B] = self.or(p, p2)

    def or[B >: A](p2: => Parser[B]): Parser[B] = self.or(p, p2)

    def many: Parser[List[A]] = self.many(p)

    def map[B](f: A => B): Parser[B] = self.map(p)(f)

    def **[B](p2: Parser[B]): Parser[(A, B)] = self.product(p, p2)

    def product[B](p2: Parser[B]): Parser[(A, B)] = self.product(p, p2)

    // flatMap は１つ前のパーサの成否に依存する
    // 文脈依存（たぶんこれめっちゃ大事！Monadは文脈依存の計算を合成する
    // 手続き的な合成
    def flatMap[B](f: A => Parser[B]): Parser[B] = self.flatMap(p)(f)
  }


  // ###############################################################################
  val p: Parser[String] = "hoge" or "huga"

  // ex 9.6
  char('a').flatMap { c =>
    listOfN(c.toInt, char('a'))
  }
  // 上を for 内包表記で書くとこうなる
  for {
    digits <- "[0-9]+".r
    n = digits.toInt
    _ <- listOfN(n, char('a'))
  } yield n

  // 関数型プログラミングでは、代数を定義し、具体的な実装がないままの状態でその表現力を調べるのが一般的である。
  // 具体的な実装によって制限がかかってしまうと、APIを変更するのが難しくなる（特にライブラリの設計段階では）。

  // 型によって定義域などを定義できないと実装が制限されない
  // 型によって制限をかけられることが実装のしやすさにつながる？
  // これは特にドメインモデルの設計に役立つ？それともドメインサービス？
}

// データ型の代数の間に共通のパターン
// モノイドが並列化を促進