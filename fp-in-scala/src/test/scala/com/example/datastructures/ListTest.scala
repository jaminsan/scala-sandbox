package com.example.datastructures

import com.example.datastructures.{List => MyList, Nil => MyNil}
import org.scalatest.concurrent.TimeLimits
import org.scalatest.{DiagrammedAssertions, FlatSpec}

class ListTest extends FlatSpec with DiagrammedAssertions with TimeLimits {

  "drop関数" should "指定した数だけ要素を削除した新しいリストを返す" in {
    val l = MyList(1, 2, 3, 4, 5)

    assert(MyList.drop(l, 3) == MyList(4, 5))
    assert(MyList.drop(l, 0) == MyList(1, 2, 3, 4, 5))
    assert(MyList.drop(l, 5) == MyNil)
  }

  "drop関数" should "指定した数がリストの要素数より大きい" in {
    intercept[RuntimeException] {
      val l = MyList(1, 2, 3, 4, 5)

      MyList.drop(l, 6)
      MyList.drop(l, 10)
    }

  }

  "init関数" should "末尾以外の要素を持つ新しいリストを返す" in {
    assert(MyList.init(MyList(1, 2, 3, 4, 5)) == MyList(1, 2, 3, 4))
    assert(MyList.init(MyList(1)) == MyNil)
    assert(MyList.init(MyNil) == MyNil)
  }

  "reverse関数" should "リストの要素を反転させた新しいリストを返す" in {
    assert(MyList.reverse(MyList(1, 2, 3, 4, 5)) == MyList(5, 4, 3, 2, 1))
    assert(MyList.reverse(MyList(1)) == MyList(1))
    assert(MyList.reverse(MyNil) == MyNil)
  }


  "append関数" should "指定した要素を末尾に追加した新しいリストを返す" in {
    assert(MyList.append(MyList(1, 2), 1) == MyList(1, 2, 1))
    assert(MyList.append(MyNil, 1) == MyList(1))
  }

  "filter関数" should "リストから奇数を全て削除した新しいリストを返す" in {
    assert(MyList.filter(MyList(1, 2, 3, 4, 5))(_ % 2 == 0) == MyList(2, 4))
    assert(MyList.filter(MyList(1, 2, 2, 4, 5, 6, 6))(_ % 2 == 0) == MyList(2, 2, 4, 6, 6))
    assert(MyList.filter(MyList(1, 3, 5))(_ % 2 == 0) == MyNil)
  }

  "zipThenAdd関数" should "２つのリストの数値を足し合わせた値を要素として持つ新しいリストを返す" in {
    assert(MyList.zipThenAdd(MyList(1, 2, 3), MyList(2, 3, 4)) == MyList(3, 5, 7))
    assert(MyList.zipThenAdd(MyList(1), MyList(2, 3, 4)) == MyList(3))
    assert(MyList.zipThenAdd(MyList(1, 2, 3), MyList(2)) == MyList(3))
  }
}
