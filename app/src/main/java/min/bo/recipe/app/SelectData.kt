package min.bo.recipe.app

import com.google.gson.annotations.SerializedName

data class SelectData(

    val title:Title,
    @SerializedName("top_banners") val topBanners: List<Banner>

)
