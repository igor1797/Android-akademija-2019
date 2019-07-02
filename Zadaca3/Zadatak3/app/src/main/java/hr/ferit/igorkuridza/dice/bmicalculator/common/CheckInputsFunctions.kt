package hr.ferit.igorkuridza.dice.bmicalculator.common

fun areInputsWithinBoundaries(height: Float, weight: Float): Boolean{
    if(weight<=0 || weight>=350 || height>2.5 || height<=0) return false
    return true
}

fun areInputsNotEmpty(weight: String, height: String): Boolean{
    if(weight.isNotEmpty() && height.isNotEmpty()) return true
    return false
}