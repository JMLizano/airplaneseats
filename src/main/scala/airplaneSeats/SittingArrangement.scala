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
  /* planeSeats represents the free seats in the plane
      1 -> Seat is free
      0 -> Seat is occupied
   */
  lazy val planeSeats : Array[Array[Int]] = Array.fill(planeRows)(Array.fill(rowSeats)(1))
  // finalSeats represents the final passenger arrangement in the plane
  lazy val finalSeats : Array[Array[String]] = Array.fill(planeRows)(Array.fill(rowSeats)(""))
  // waitingPassengers are passengers that do not fill in the plane due to oversubscribing
  var waitingPassengers : Array[Passenger] = Array()
  val passengers: List[PassengerGroup]
}
