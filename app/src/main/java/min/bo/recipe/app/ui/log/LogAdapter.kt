package min.bo.recipe.app.ui.log

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import min.bo.recipe.app.databinding.ItemListBinding
import min.bo.recipe.app.databinding.ItemLogBinding
import min.bo.recipe.app.model.CerealData
import min.bo.recipe.app.model.LogData

class LogAdapter(private val viewModel:LogViewModel):ListAdapter<LogData,LogAdapter.LogViewHolder>(LogDiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {

        val binding = ItemLogBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        holder.bind(getItem(position))

    }


    inner class LogViewHolder(private val binding: ItemLogBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(log:LogData){
            binding.viewModel = viewModel
            binding.log = log
            binding.executePendingBindings()
        }
    }

}




class LogDiffCallback : DiffUtil.ItemCallback<LogData>(){
    override fun areItemsTheSame(oldItem: LogData, newItem: LogData): Boolean {
        return oldItem.log_id == newItem.log_id
    }

    override fun areContentsTheSame(oldItem: LogData, newItem: LogData): Boolean {
        return oldItem==newItem

    }

}