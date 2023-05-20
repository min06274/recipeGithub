package min.bo.recipe.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import org.json.JSONObject

class SelectFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbarTitle = view.findViewById<TextView>(R.id.toolbar_select_title)

        val viewpager = view.findViewById<ViewPager2>(R.id.viewpager_select_banner)
        val viewpagerIndicator = view.findViewById<TabLayout>(R.id.viewpager_select_banner_indicator)

        val assetLoader= AssetLoader()
        val selectJsonString = assetLoader.getJsonString(requireContext(),"select.json")

        if(!selectJsonString.isNullOrEmpty()){
            val gson = Gson()
            val selectData = gson.fromJson(selectJsonString,SelectData::class.java)





            toolbarTitle.text = selectData.title.text


            viewpager.adapter = SelectBannerAdapter().apply{
                submitList(selectData.topBanners)
            }

            val pageWidth = resources.getDimension(R.dimen.viewpager_item_width)
            val pageMargin = resources.getDimension(R.dimen.viewpager_item_margin)
            val screenWidth = resources.displayMetrics.widthPixels
            val offset = screenWidth-pageWidth-pageMargin

            viewpager.offscreenPageLimit=3
            viewpager.setPageTransformer { page, position ->
                page.translationX = position * -offset

            }
            TabLayoutMediator(viewpagerIndicator,viewpager
            ) { tab, position ->

            }.attach()


        }
        }


}

