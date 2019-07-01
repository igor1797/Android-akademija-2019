class Game(
    private val player1: Player,
    private val player2: Player
) {
    fun setupGame(){
        player1.grid.setShips()
        player2.grid.setShips()
        println("Grid with ships of player: ${player1.name}")
        player1.grid.printGridWithShips()
        println("Grid with ships of player: ${player2.name}")
        player2.grid.printGridWithShips()
    }
    fun playGame(){
        while (!player1.grid.lost && !player2.grid.lost) {
            var xCorP1: String
            var yCorP1: String
            var xCorP2: String
            var yCorP2: String
            println("${player1.name} enter xCordinate where you want to shot")
            xCorP1= readLine().toString().toUpperCase()
            println("${player1.name} enter yCordinate where you want to shot")
            yCorP1= readLine().toString()
            player2.grid.shotOnShip(Point(xCorP1.single(),yCorP1.toInt()))
            println("${player2.name} enter xCordinate where you want to shot")
            xCorP2= readLine().toString().toUpperCase()
            println("${player2.name} enter yCordinate where you want to shot")
            yCorP2= readLine().toString()
            player1.grid.shotOnShip(Point(xCorP2.single(),yCorP2.toInt()))
            if (player1.grid.lost) println("Game over ${player2.name} win")
            if (player2.grid.lost) println("Game over ${player1.name} win")
        }
    }
}