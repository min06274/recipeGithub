package min.bo.recipe.app.ui.list

import android.view.LayoutInflater
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import min.bo.recipe.app.databinding.ItemListBinding
import min.bo.recipe.app.model.CerealData
import android.view.ViewGroup
import min.bo.recipe.app.Banner
import min.bo.recipe.app.databinding.ItemSelectBannerBinding

class CerealListAdapter: ListAdapter<CerealData,  CerealListAdapter.ListViewHolder>(ListDiffCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListViewHolder(private val binding: ItemListBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(cereal:CerealData){
            binding.list = cereal
            binding.executePendingBindings()
        }
    }
}

class ListDiffCallback : DiffUtil.ItemCallback<CerealData>(){
    override fun areItemsTheSame(oldItem: CerealData, newItem: CerealData): Boolean {
        return oldItem.cereal_id == newItem.cereal_id
    }

    override fun areContentsTheSame(oldItem: CerealData, newItem: CerealData): Boolean {
        return oldItem==newItem

    }

}