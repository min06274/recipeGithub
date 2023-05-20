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

class SelectBannerAdapter: ListAdapter<Banner, SelectBannerAdapter.SelectBannerViewHolder>(
    BannerDiffCallback()
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectBannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_select_banner,parent,false)
        return SelectBannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: SelectBannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class SelectBannerViewHolder(view:View):RecyclerView.ViewHolder(view){
        private val bannerImageView = view.findViewById<ImageView>(R.id.iv_banner_image)
        private val bannerTitleTextView = view.findViewById<TextView>(R.id.tv_banner_title)
        private val bannerDetailBrandLabelTextView = view.findViewById<TextView>(R.id.tv_banner_detail_brand_label)
        private val bannerDetailInformationLabelTextView = view.findViewById<TextView>(R.id.tv_banner_detail_information_label)

        fun bind(banner: Banner){
        loadImage(banner.backgroundImageUrl,bannerImageView)


            bannerTitleTextView.text = banner.label
            bannerDetailBrandLabelTextView.text = banner.productDetail.brandName
            bannerDetailInformationLabelTextView.text = banner.productDetail.information

        }

        fun loadImage(urlString: String, imageView: ImageView)
        {
            GlideApp.with(itemView)
                .load(urlString)
                .into(imageView)
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