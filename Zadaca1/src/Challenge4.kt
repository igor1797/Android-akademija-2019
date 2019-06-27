/*
Challenge: Apply Functional Programming for Simple Data Analysis

We decided to gather data on the age of our users.
In this challenge, you'll be presented with this partly faulty data
of user ages which is based on four input files:
*/

val data = mapOf(
    "users1.csv" to listOf(32, 45, 17, -1, 34),
    "users2.csv" to listOf(19, -1, 67, 22),
    "users3.csv" to listOf(),
    "users4.csv" to listOf(56, 32, 18, 44)
)

/*
Apply the functions you learned about as well as other functions from Kotlin's
standard library to solve the following data analysis tasks:
*/
fun main() {
//1. Find the average age of users (use only valid ages for the calculation!)
    var averageAge=data.flatMap { it.value }
        .filter { it>0 }
        .average()
    println(averageAge)

//2. Extract the names of input files that contain faulty data
    var names= mutableListOf<String>()
    for (file in data) if((file.value.filter { it<0 }.size)>0) names.add(file.key)
    println(names)
//3. Count the total number of faulty entries across all input files
    val numberOfFaultyEntries=data.flatMap { it.value }
        .filter { it<0 }
        .size
    println(numberOfFaultyEntries)
}
/*
Hints: map() and flatMap() are often very useful functions for such tasks
Use IntelliJ's autocompletion to explore which other functions, that were not
presented in the lectures, are available (they could simplify the tasks)
 */