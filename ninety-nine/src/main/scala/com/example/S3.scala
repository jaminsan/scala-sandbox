package com.example

object S3 {

  /**
    * リストの n 番目の要素を取得する
    */
  def nth[A](n: Int, l: List[A]): A = {
    def nthR(count: Int, l: List[A]): A = (count, l) match {
      // 明示的に 0 の時に探索を終了することを示した方がわかりやすい
      // かつ並んでいる case が同じものをみているので if でガードするよりもそれぞれの case 分の比較がわかりやすくてコードが読みやすい
      case (0, h :: _) => h
      case (c, _ :: tail) => nthR(c - 1, tail)
      case (_, Nil) => throw new NoSuchElementException
    }

    if (n >= l.length) throw new IllegalArgumentException("n must be lower then length of list")
    if (n < 0) throw new IllegalArgumentException("n must be bigger than 0")
    nthR(n, l)
  }

  def main(args: Array[String]): Unit = {
    val l = List(1, 1, 2, 3, 5, 8)
    assert(nth(2, l) == 2)
    assert(nth(3, l) == 3)
    assert(nth(0, l) == 1)
    assert(nth(5, l) == 8)
  }
}
