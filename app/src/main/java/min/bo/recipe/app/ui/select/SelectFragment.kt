package min.bo.recipe.app.ui.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import min.bo.recipe.app.R
import min.bo.recipe.app.databinding.FragmentSelectBinding
import min.bo.recipe.app.ui.common.ViewModelFactory

class SelectFragment:Fragment() {

    private val viewModel : SelectViewModel by viewModels{ ViewModelFactory(requireContext()) }
    private lateinit var binding:FragmentSelectBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        setToolBar()



        setTopBanners()


        val indivisualButton:Button = view.findViewById(R.id.indivisual_print_btn)
        val gramInformationEditText1: EditText = view.findViewById(R.id.gram_information1)
        val gramInformationEditText2: EditText = view.findViewById(R.id.gram_information2)
        val gramInformationEditText3: EditText = view.findViewById(R.id.gram_information3)


        indivisualButton.setOnClickListener{
            val gramInformation1 = gramInformationEditText1.text.toString()
            val gramInformation2 = gramInformationEditText2.text.toString()
            val gramInformation3 = gramInformationEditText3.text.toString()

            indivisualButton.text = "눌림"
            println(gramInformation1)
            println(gramInformation2)
            println(gramInformation3)


        }







        }

    private fun setToolBar() {
        viewModel.title.observe(viewLifecycleOwner, { title ->
            binding.title = title
        })
    }

    private fun setTopBanners() {
        with(binding.viewpagerSelectBanner) {
            adapter = SelectBannerAdapter().apply {

                viewModel.topBanners.observe(viewLifecycleOwner, { banners ->
                    submitList(banners)

                })


            }

            val pageWidth = resources.getDimension(R.dimen.viewpager_item_width)
            val pageMargin = resources.getDimension(R.dimen.viewpager_item_margin)
            val screenWidth = resources.displayMetrics.widthPixels
            val offset = screenWidth - pageWidth - pageMargin

            offscreenPageLimit = 3
            setPageTransformer { page, position ->
                page.translationX = position * -offset

            }
            TabLayoutMediator(
                binding.viewpagerSelectBannerIndicator, this
            ) { tab, position ->

            }.attach()


        }
    }



}

