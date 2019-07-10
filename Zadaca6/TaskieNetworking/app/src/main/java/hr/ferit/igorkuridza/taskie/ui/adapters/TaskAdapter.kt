package hr.ferit.igorkuridza.taskie.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import hr.ferit.igorkuridza.taskie.R
import hr.ferit.igorkuridza.taskie.model.BackendTask

class TaskAdapter(private val onItemSelected: (BackendTask) -> Unit) : Adapter<TaskHolder>() {

    private val data: MutableList<BackendTask> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskHolder(v)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bindData(data[position], onItemSelected)
    }

    fun getTaskByPosition(position: Int): BackendTask{
        return data[position]
    }

    fun getTask(id: String): BackendTask? {
        for (task in data)
            if (task.id == id) return task
        return null
    }

    fun getTasks(): MutableList<BackendTask>{
        return data
    }

    fun deleteTask(task: BackendTask){
        data.remove(task)
        notifyItemRemoved(data.indexOf(task))
        notifyItemRangeRemoved(data.indexOf(task), data.size)
    }

    fun addData(item: BackendTask) {
        data.add(item)
        notifyItemInserted(data.size)
    }

    fun setData(data: MutableList<BackendTask>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun sortDataByPriority() {
        data.sortBy { it.taskPriority }
        data.reverse()
        notifyDataSetChanged()
    }

    fun getSize(): Int{
        return data.size
    }

}





