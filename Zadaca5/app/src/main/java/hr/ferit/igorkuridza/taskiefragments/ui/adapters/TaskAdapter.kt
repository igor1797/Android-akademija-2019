package hr.ferit.igorkuridza.taskiefragments.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import hr.ferit.igorkuridza.taskiefragments.R
import hr.ferit.igorkuridza.taskiefragments.model.Priority
import hr.ferit.igorkuridza.taskiefragments.model.Task

class TaskAdapter(private val onItemSelected:(Task) -> Unit): Adapter<TaskHolder>() {

    private val data: MutableList<Task> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return  TaskHolder(view)
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.BindData(data[position], onItemSelected)
    }

    override fun getItemCount() = data.size

    fun refresh(){
        notifyDataSetChanged()
    }

    fun sortByPriority(){
        data.sortBy { it.priority.getValue() }
        data.reverse()
        notifyDataSetChanged()
    }

    fun getTaskByPosition(position: Int) = data[position]

    fun setData(data: MutableList<Task>){
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }
}