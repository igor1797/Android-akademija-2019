
fun main() {

    println("Enter player1 name: ")
    val name1= readLine()
    println("Enter player2 name: ")
    val name2= readLine()
    val player1 = Player(name1.toString(), Grid())
    val player2 = Player(name2.toString(), Grid())
    val game = Game(player1,player2)
    game.setupGame()
    game.playGame()
}