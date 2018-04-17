package airplaneSeats

trait SittingArrangement {

  case class PassengerGroup(passengers: List[Passenger]) {
    val groupSize: Int = passengers.length
    val satisfaction: Int = groupSize
  }

  case class Passenger(id: String, windowPreference: Boolean) {
    var seat: Tuple2[Int, Int] = null

    def assignSeat(row: Int, seat: Int) = {
      this.seat = (row, seat)
    }
  }

  val planeRows: Int
  val rowSeats: Int
  lazy val planeSeats : Array[Array[Int]] = Array.fill(planeRows)(Array.fill(rowSeats)(1))
  lazy val finalSeats : Array[Array[String]] = Array.fill(planeRows)(Array.fill(rowSeats)(""))
  var waitingPassengers : Array[Passenger] = Array()
  val passengers: List[PassengerGroup]
}
