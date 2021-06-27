package com.example.testapplication.ui.main.taskManager

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.R
import com.example.testapplication.data.database.entities.Tasks
import com.example.testapplication.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecyclerAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Tasks, RecyclerAdapter.TaskViewHolder>(TasksComparator()), KoinComponent {

    private var tasks: List<Tasks> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = tasks[position]
        holder.bind(currentTask)

        // Create the click listener for the "set as done" icon
        holder.imageViewCheckImg.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val repo: UserRepository? by inject()
                repo?.setAsDone(currentTask.id)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = tasks.count()

    fun setTasks(tasks: List<Tasks>) {
        this.tasks = tasks
        notifyDataSetChanged() //TODO change this method to notify item inserted/removed
    }

    class TaskViewHolder(itemView: View, val listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val textViewTitle: TextView = itemView.findViewById(R.id.task_title)
        private val textViewTimerType: TextView = itemView.findViewById(R.id.task_timertype)
        val imageViewCheckImg: ImageView = itemView.findViewById(R.id.task_checked)

        fun bind(task: Tasks) {
            textViewTitle.text = task.name
            textViewTimerType.text = task.description

            if (task.isCompleted){
                imageViewCheckImg.setImageResource(R.drawable.task_done_check)
            } else{
                imageViewCheckImg.setImageResource(R.drawable.task_todo_check)
            }

        }

        companion object {
            fun create(parent: ViewGroup, listener: OnItemClickListener): TaskViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_task, parent, false)
                return TaskViewHolder(view, listener)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    class TasksComparator : DiffUtil.ItemCallback<Tasks>() {
        override fun areItemsTheSame(oldItem: Tasks, newItem: Tasks): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Tasks, newItem: Tasks): Boolean {
            return oldItem.id == newItem.id
        }
    }
}