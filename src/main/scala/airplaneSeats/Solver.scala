package airplaneSeats

trait Solver extends  SittingArrangement {

  /**
  * Find rows where the group fits, and orders them by window seats available.
  * Rows with fewer window seats are fist in the array, so groups with no window preference
  * pick the first row in the array and vice versa
  */
  def findRow(groupSize: Int, windowPreference: Boolean) : Int = {
    val seatsWithIndex = planeSeats.zipWithIndex
    // Get only rows with enough space, and sort them by available window seats
    val validRows = seatsWithIndex.filter(_._1.sum >= groupSize).sortBy( x => (x._1.head + x._1.last))
    if (windowPreference)
      validRows.last._2
    else
      validRows.head._2
  }

  /**
    * Given a row, finds column from which start sitting the group (always assign seats from left to right)
    * If group has no window preference always start from first free seat, otherwhise look if there are
    * free windows seats.
    */
  def findCol(row: Int, groupSize: Int, windowPreference: Boolean) : Int = {
    if (windowPreference)
      planeSeats(row) match {
        case array: Array[Int] if array.head == 0 && array.last == 1 => array.length - groupSize
        case _ => planeSeats(row).indexWhere(_ == 1)
      }
    else
      planeSeats(row).indexWhere(_ == 1)
  }


  def getSeats(passengers: List[Passenger]) = {
    val groupWindowPreference = passengers.count(_.windowPreference) > 0
    val row = findRow(passengers.length, groupWindowPreference)
    val col = findCol(row, passengers.length, groupWindowPreference)
    for (
      (p,i) <- passengers.sortBy(!_.windowPreference).zipWithIndex
    ){
      planeSeats(row)(col + i) = 0
      finalSeats(row)(col + i) = p.id
      p.assignSeat(row, col + i)
    }
  }

  /**
    * If there is enough space in any row for the group go ahead. Otherwise split the group,
    * making subgroups as large as possible
    */
  def placeGroup(group: PassengerGroup) : Unit = {
    val maxFreeSeats = planeSeats.map(_.sum).max
    if (maxFreeSeats == 0)
      for (p <- group.passengers){ waitingPassengers = p+: waitingPassengers }
    else
      if (group.groupSize <= maxFreeSeats)
        getSeats(group.passengers)
      else {
        val subGroup = group.passengers.take(maxFreeSeats)
        getSeats(subGroup)
        placeGroup(PassengerGroup(group.passengers.drop(maxFreeSeats)))
      }
  }

  def doArrangement = {
    for (
      group <- passengers.sortWith(_.satisfaction > _.satisfaction )
    ) {
      placeGroup(group)
    }
  }

  def arrangementToString() = {
    val inPlane = finalSeats.map(_.mkString(" ")).mkString("\n") + f"\n$satisfaction" + "%"
    val waiting =  if (waitingPassengers.length > 0 )
                    "\nWaiting passengers: " + waitingPassengers.map(_.id).mkString(",")
                    else ""

    f"$inPlane$waiting"
  }

  lazy val satisfaction = {
    val numPassengers = passengers.map(_.passengers).flatten.length
    var unsatisfied = 0
    for (
      PassengerGroup(passengers) <- passengers
    ) {
      if (passengers.count(_.seat == null) > 0 )
        unsatisfied = unsatisfied + passengers.length
      else if (passengers.map(_.seat._1).toSet.size > 1)
        unsatisfied = unsatisfied + passengers.length
      else
        unsatisfied += passengers.filter(p => p.windowPreference && (p.seat._2 !=0 && p.seat._2 != rowSeats -1)).length
    }
    ((1.0 - (unsatisfied.toFloat/numPassengers)) * 100).toInt
  }
}
