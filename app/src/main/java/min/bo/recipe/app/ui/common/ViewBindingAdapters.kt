package min.bo.recipe.app.ui.common

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("isColor")

fun upodateColor(view:View, isColor:String)
{
    if(view is TextView){
        if(isColor == "탄수화물 특화 시리얼 입니다."){
            view.setTextColor(Color.BLUE)
        }else if(isColor == "단백질 특화 시리얼 입니다."){
            view.setTextColor(Color.RED)

        }
        else {
            view.setTextColor(Color.BLACK)

        }
    }
}