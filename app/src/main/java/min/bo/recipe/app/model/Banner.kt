package min.bo.recipe.app

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("background_image_url") val backgroundImageUrl: String,
    val label:String,
    @SerializedName("product_detail") val productDetail:ProductDetail

)

data class ProductDetail(
    @SerializedName("brand_name") val brandName:String,
    val information:String,
    @SerializedName("product_id") val productId:String,
    val kcal:String
)