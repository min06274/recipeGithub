package min.bo.recipe.app.ui.select

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import min.bo.recipe.app.R
import min.bo.recipe.app.databinding.FragmentSelectBinding
import min.bo.recipe.app.ui.common.ViewModelFactory
import kotlin.concurrent.thread

class SelectFragment:Fragment() {

    private val viewModel : SelectViewModel by viewModels{ ViewModelFactory(requireContext()) }
    private lateinit var binding:FragmentSelectBinding
    private lateinit var webView: WebView

    val database1 = Firebase.database("https://cereal-22a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
    val myRef = database1.getReference("top_banners")

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


        val cereal1:TextView = view.findViewById(R.id.carbo)
        val cereal2:TextView = view.findViewById(R.id.protein)
        val cereal3:TextView = view.findViewById(R.id.fat)

        myRef.child("0").child("product_detail").child("brand_name").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val brandName = dataSnapshot.getValue(String::class.java)
                cereal1.text = brandName
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that occur during the retrieval
            }
        })


        myRef.child("1").child("product_detail").child("brand_name").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val brandName = dataSnapshot.getValue(String::class.java)
                cereal2.text = brandName
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that occur during the retrieval
            }
        })

        myRef.child("2").child("product_detail").child("brand_name").addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val brandName = dataSnapshot.getValue(String::class.java)
                cereal3.text = brandName
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that occur during the retrieval
            }
        })



        indivisualButton.setOnClickListener{
            val gramInformation1 = gramInformationEditText1.text.toString()
            val gramInformation2 = gramInformationEditText2.text.toString()
            val gramInformation3 = gramInformationEditText3.text.toString()

            indivisualButton.text = "눌림"
            println(gramInformation1)
            println(gramInformation2)
            println(gramInformation3)




            webView = WebView(requireContext())

            webView.settings.javaScriptEnabled =true

            webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE

            webView.webViewClient = WebViewClient()
            webView.loadUrl("http://192.168.0.125/page1?salt=3&sugar=3&blackpepper=3")



        }







        }

    override fun onDestroy() {
        super.onDestroy()
        // Ensure proper cleanup of the WebView
        if (::webView.isInitialized) {
            webView.destroy()
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

