package ships
import Ship
import DirectionOfShip
import Point

class Cruiser (
    name: String,
    size: Int
): Ship(name,size) {
    override var hits: Int = 0
    override var destroyed: Boolean = false
    override lateinit var startPointOfShip: Point
    override lateinit var directionOfShip: DirectionOfShip
    override lateinit var pointsOfShip: MutableSet<Point>
}