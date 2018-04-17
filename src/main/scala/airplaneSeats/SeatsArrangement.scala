package airplaneSeats


object SeatsArrangement extends App  with Solver with FileParser {

  require(args.length == 1)
  val fileToParse = args(0)

  doArrangement
  println(arrangementToString)
}
