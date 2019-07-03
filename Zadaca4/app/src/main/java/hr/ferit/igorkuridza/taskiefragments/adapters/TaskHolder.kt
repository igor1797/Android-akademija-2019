package hr.ferit.igorkuridza.taskiefragments.adapters

import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.WrappedDrawable
import androidx.recyclerview.widget.RecyclerView
import hr.ferit.igorkuridza.taskiefragments.model.Task
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_task.view.*

class TaskHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun BindData(task: Task, onItemSelected: (Task) -> Unit){

        containerView.setOnClickListener { onItemSelected(task) }

        containerView.taskTitle.text = task.title

        val drawable = containerView.taskPriority.drawable
        val wrapDrawable = DrawableCompat.wrap(drawable)
        DrawableCompat.setTint(wrapDrawable, ContextCompat.getColor(containerView.context, task.priority.getColor()))
    }
}