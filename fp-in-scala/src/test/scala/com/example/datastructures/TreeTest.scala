package com.example.datastructures

import org.scalatest.flatspec.AnyFlatSpec

class TreeTest extends AnyFlatSpec {

  "maximum関数" should "Treeの中で最大の値を返す" in {
    assert(
      Tree.maximum(
        Leaf(1)
      ) == 1)

    assert(
      Tree.maximum(
        Branch(
          Leaf(1),
          Leaf(3)
        )
      ) == 3
    )

    assert(
      Tree.maximum(
        Branch(
          Branch(
            Leaf(10),
            Branch(
              Leaf(1),
              Leaf(20)
            )
          ),
          Leaf(3)
        )
      ) == 20
    )
  }

  "depth関数" should "Treeの深さを返す" in {
    assert(
      Tree.depth(
        Leaf(1)
      ) == 1
    )

    assert(
      Tree.depth(
        Branch(
          Leaf(1),
          Branch(
            Leaf(2),
            Leaf(3)
          )
        )
      ) == 3
    )

    assert(
      Tree.depth(
        Branch(
          Branch(
            Branch(
              Branch(
                Leaf(1),
                Leaf(1)
              ),
              Leaf(1)
            ),
            Leaf(1)
          ),
          Branch(
            Leaf(2),
            Leaf(3)
          )
        )
      ) == 5
    )
  }
}
