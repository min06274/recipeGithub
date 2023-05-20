package min.bo.recipe.app.ui.select

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import min.bo.recipe.app.Banner
import min.bo.recipe.app.GlideApp
import min.bo.recipe.app.R
import min.bo.recipe.app.databinding.ItemSelectBannerBinding

class SelectBannerAdapter: ListAdapter<Banner, SelectBannerAdapter.SelectBannerViewHolder>(
    BannerDiffCallback()
) {

    private lateinit var binding: ItemSelectBannerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectBannerViewHolder {
        binding = ItemSelectBannerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SelectBannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectBannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class SelectBannerViewHolder(private val binding: ItemSelectBannerBinding):RecyclerView.ViewHolder(binding.root){


        fun bind(banner: Banner){

            binding.banner = banner
            binding.executePendingBindings()

        }


    }

}

class BannerDiffCallback:DiffUtil.ItemCallback<Banner>(){
    override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem.productDetail.productId == newItem.productDetail.productId
    }

    override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
        return oldItem == newItem
    }

}