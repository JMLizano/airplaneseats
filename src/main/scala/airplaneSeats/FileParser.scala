package airplaneSeats

import scala.io.Source

trait FileParser extends SittingArrangement {

  val fileToParse: String
  val clientRegex = """(\d+)(W)?""".r

  private def readLines(file: String) = {
    val source = Source.fromFile(file)
    try {
      source.getLines().toList
    } finally {
      source.close()
    }
  }

  private def getPlaneSize(dimensions: List[String]) = dimensions match {
    case x :: y :: Nil => (x.toInt, y.toInt)
    case _ => throw new Error("Invalid file")
  }

  private def parseLine(line: List[String]): List[Passenger] = {
    for {
      clientRegex(id, window) <- line
    } yield Passenger(id.toString, window != null)
  }

  lazy val fileContents = readLines(fileToParse).map(line => line.split("\\s+").toList)
  lazy val (rowSeats, planeRows) = getPlaneSize(fileContents.head)
  lazy val passengers : List[PassengerGroup] = fileContents.tail.map(l => PassengerGroup(parseLine(l)))

}
