package min.bo.recipe.app.ui.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import min.bo.recipe.app.R
import min.bo.recipe.app.ui.common.ViewModelFactory

class SelectFragment:Fragment() {

    private val viewModel : SelectViewModel by viewModels{ ViewModelFactory(requireContext()) }


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

        viewModel.title.observe(viewLifecycleOwner, { title->

            toolbarTitle.text = title.text

        })



        viewpager.adapter = SelectBannerAdapter().apply {

            viewModel.topBanners.observe(viewLifecycleOwner,{banners->
                submitList(banners)

            })
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

