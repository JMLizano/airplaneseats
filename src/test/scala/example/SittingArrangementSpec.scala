package example



import airplaneSeats.{FileParser, Solver}
import org.scalatest.FunSuite

class SittingArrangementSpec extends FunSuite {


  trait SeatsArrangement extends Solver with FileParser

  test("Basic case") {
    new SeatsArrangement {
      val fileToParse = "test.txt"
      val expectedOutput =
        """4 5 6 7
          |13 14 15 16
          |11 9 10 8
          |1 2 3 12
          |100%""".stripMargin
      doArrangement
      assert(satisfaction == 100)
      assert(expectedOutput == arrangementToString())
    }
  }

  test("Split group") {
    new SeatsArrangement {
      val fileToParse = "test_split_group.txt"
      val expectedOutput =
        """4 5 6 7
          |12 13 14 15
          |11 9 10 16
          |1 2 3 17
          |81%""".stripMargin
      doArrangement
      assert(satisfaction == 81)
      assert(expectedOutput == arrangementToString())
    }
  }

  test("Plane oversubscribed") {
    new SeatsArrangement {
      val fileToParse = "test_oversubscribed.txt"
      val expectedOutput =
        """4 5 6 7
          |15 16 17 18
          |11 9 10 13
          |1 2 3 12
          |73%
          |Waiting passengers: 21,20,14""".stripMargin
      doArrangement
      assert(satisfaction == 73)
      assert(expectedOutput == arrangementToString())
    }
  }

  test("Large plane") {
    new SeatsArrangement {
      val fileToParse = "test_large_plane.txt"
      val expectedOutput =
        """4 5 6 7
          |20 21 40 39
          |24 23 34 33
          |32 30 31 22
          |12 13 14 29
          |1 2 3 19
          |38 35 36 37
          |26 25 27 28
          |16 15 17 18
          |10 8 9 11
          |94%""".stripMargin
      doArrangement
      assert(satisfaction == 94)
      assert(expectedOutput == arrangementToString())
    }
  }
}
