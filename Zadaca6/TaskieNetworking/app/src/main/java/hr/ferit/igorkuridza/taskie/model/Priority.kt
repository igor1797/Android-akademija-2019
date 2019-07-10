package hr.ferit.igorkuridza.taskie.model

import androidx.annotation.ColorRes
import hr.ferit.igorkuridza.taskie.R

enum class PriorityColor(@ColorRes private val colorRes: Int) {
    LOW(R.color.colorLow),
    MEDIUM(R.color.colorMedium),
    HIGH(R.color.colorHigh);

    fun getColor(): Int = colorRes
}

enum class BackendPriorityTask(private val num: Int) {
    LOW(1),
    MEDIUM(2),
    HIGH(3);

    fun getValue(): Int = num
}

