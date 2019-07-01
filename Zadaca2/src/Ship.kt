abstract class Ship(
    val name: String,
    protected val size: Int
){
    abstract var hits: Int
    abstract var startPointOfShip: Point
    abstract var directionOfShip: DirectionOfShip
    abstract var pointsOfShip: MutableSet<Point>
    abstract var destroyed: Boolean

    fun setPointsOfShip(){
        this.pointsOfShip = mutableSetOf()
        val rand = (0..1).random()
        if (rand == 0) this.directionOfShip = DirectionOfShip.HORIZONTAL
        else this.directionOfShip = DirectionOfShip.VERTICAL
        when (this.directionOfShip) {
            DirectionOfShip.VERTICAL -> {
                this.startPointOfShip = Point(('A'..'J').random(), (1..(10-this.size+1)).random())
                this.pointsOfShip.add(this.startPointOfShip)
                for (i in 1 until size)
                    this.pointsOfShip.add(Point(this.startPointOfShip.xCordinate, this.startPointOfShip.yCordinate + i))
            }
            DirectionOfShip.HORIZONTAL -> {
                this.startPointOfShip = Point(('A'..('J'-this.size+1)).random(), (1..10).random())
                this.pointsOfShip.add(this.startPointOfShip)
                for (i in 1 until size)
                    this.pointsOfShip.add(Point(this.startPointOfShip.xCordinate+i, this.startPointOfShip.yCordinate))
            }
        }
    }

    fun increaseHits(){
        this.hits+=1
        this.destroyed = this.size == this.hits
    }
}