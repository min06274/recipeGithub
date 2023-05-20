package min.bo.recipe.app.model

import com.google.gson.annotations.SerializedName
import min.bo.recipe.app.Banner

data class SelectData(

    val title: Title,
    @SerializedName("top_banners") val topBanners: List<Banner>

)
