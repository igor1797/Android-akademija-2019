import ships.AircraftCarrier
import ships.Battleship
import ships.Cruiser
import ships.Destroyer

class Grid(){
    private var ships: ArrayList<Ship> = arrayListOf()
    private var activeShips: List<String> = listOf()
    var lost: Boolean = false

    fun setShips(){
        ships.add(AircraftCarrier("AircraftCarrier",5))
        ships.add(Battleship("Battleship",4))
        ships.add(Cruiser("Cruiser",3))
        ships.add(Destroyer("Destroyer",2))
        for (ship in ships) {
            ship.setPointsOfShip()
        }
    }

    fun printGridWithShips(){
        val pointsOfAllShips = ships.flatMap { it.pointsOfShip }
        println("   A B C D E F G H I J")
        for(i in 1..10) {
            if (i==10) print ("$i")
            else print (" $i")
            for (j in 'A'..'J') {
                if (pointsOfAllShips.contains(Point(j, i))) print(" S")
                else print(" /")
            }
            println()
        }
    }
    fun shotOnShip(point: Point){
        for (ship in ships){
            if (ship.pointsOfShip.contains(Point(point.xCordinate,point.yCordinate))) {
                ship.increaseHits()
                //if (ship.destroyed) ships.remove(ship)
            }
            this.activeShips = ships.filter { !it.destroyed }.map { it.name }
            this.lost = activeShips.isEmpty()
        }
    }

}