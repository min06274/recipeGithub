package min.bo.recipe.app.model

import android.graphics.Bitmap
import android.widget.ImageView
import com.google.gson.annotations.SerializedName

data class CerealData(
    val cereal_image_url: String,
    val name:String,
    val information:String,
    val cereal_id:String,
    val cereal_kcal:String

)
