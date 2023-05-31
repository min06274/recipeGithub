package min.bo.recipe.app.ui.select

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import min.bo.recipe.app.R
import min.bo.recipe.app.common.*
import min.bo.recipe.app.databinding.FragmentSelectBinding
import min.bo.recipe.app.ui.common.ViewModelFactory
import kotlin.concurrent.thread
import kotlin.math.round

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

        val gramInformationEditText1: EditText = view.findViewById(R.id.table_gram1)
        val gramInformationEditText2: EditText = view.findViewById(R.id.table_gram2)
        val gramInformationEditText3: EditText = view.findViewById(R.id.table_gram3)




        //개별출력 text에 나오게

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






        var my_kcal = 0.0
        var my_carbo = 0.0
        var my_protein = 0.0
        var my_fat = 0.0
        var first_carbo = 0.0
        var second_carbo = 0.0
        var third_carbo = 0.0


        var first_protein = 0.0
        var second_protein = 0.0
        var third_protein = 0.0

        var first_fat = 0.0
        var second_fat = 0.0
        var third_fat = 0.0


        setFragmentResultListener("requestKey") { requestKey, bundle ->
            //결과 값을 받는곳입니다.
            my_kcal = bundle.getDouble(KEY_TOTAL_KCAL)

            my_carbo = round(333.75*my_kcal/2000)
            my_protein = round(50*my_kcal/2000)
            my_fat = round(40*my_kcal/2000)


            first_carbo = bundle.getDouble(KEY_FIRST_CARBO)
            second_carbo = bundle.getDouble(KEY_SECOND_CARBO)
            third_carbo = bundle.getDouble(KEY_THIRD_CARBO)

            first_protein = bundle.getDouble(KEY_FIRST_PROTEIN)
            second_protein = bundle.getDouble(KEY_FIRST_PROTEIN)
            third_protein = bundle.getDouble(KEY_FIRST_PROTEIN)

            first_fat = bundle.getDouble(KEY_FIRST_FAT)
            second_fat = bundle.getDouble(KEY_SECOND_FAT)
            third_fat = bundle.getDouble(KEY_THIRD_FAT)
            println("fsfd")
            println(my_carbo)
            println(my_protein)
            println(my_fat)

            println(first_fat)
            println(second_fat)
            println(third_fat)


        }

        var carbo1 = 0.0
        var carbo2 = 0.0
        var carbo3 = 0.0
        var protein1 = 0.0
        var protein2 = 0.0
        var protein3 = 0.0
        var fat1 = 0.0
        var fat2 = 0.0
        var fat3 = 0.0


        binding.tableGram1.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var carbo_percent_oneday = 0f
                var protein_percent_oneday = 0f
                var fat_percent_oneday = 0f
                val inputText = p0.toString()
                if(inputText.isNullOrEmpty())
                {
                    carbo1 = 0.0
                    protein1 = 0.0
                    fat1 =0.0
                    carbo_percent_oneday = ((carbo1+carbo2+carbo3) / my_carbo * 100).toFloat()
                    protein_percent_oneday = ((protein1+protein2+protein3) / my_protein * 100).toFloat()
                    fat_percent_oneday = ((fat1+fat2+fat3) / my_fat * 100).toFloat()


                }
                else {
                    val fir_edit = inputText.toDouble()
                    var temp = round(fir_edit*first_carbo)
                    var temp_p = round(fir_edit*first_protein)
                    var temp_f = round(fir_edit*first_fat)
                    carbo1 =temp
                    protein1 = temp_p
                    fat1 = temp_f
                    carbo_percent_oneday = ((carbo1+carbo2+carbo3) / my_carbo * 100).toFloat()
                    protein_percent_oneday = ((protein1+protein2+protein3) / my_protein * 100).toFloat()
                    fat_percent_oneday = ((fat1+fat2+fat3) / my_fat * 100).toFloat()
                    println(carbo1+carbo2+carbo3)

                }
                if(my_carbo != 0.0) {
                    updatePieChart1(carbo_percent_oneday, binding.pieChart1)
                    updatePieChart2(protein_percent_oneday, binding.pieChart2)
                    updatePieChart3(fat_percent_oneday, binding.pieChart3)


                }
            }

            override fun afterTextChanged(p0: Editable?) {
                var carbo_percent_oneday = 0f
                var protein_percent_oneday = 0f
                var fat_percent_oneday = 0f
                val inputText = p0.toString()
                if(inputText.isNullOrEmpty())
                {
                    carbo1 = 0.0
                    protein1 = 0.0
                    fat1 =0.0
                    carbo_percent_oneday = ((carbo1+carbo2+carbo3) / my_carbo * 100).toFloat()
                    protein_percent_oneday = ((protein1+protein2+protein3) / my_protein * 100).toFloat()
                    fat_percent_oneday = ((fat1+fat2+fat3) / my_fat * 100).toFloat()


                }
                else {
                    val fir_edit = inputText.toDouble()
                    var temp = round(fir_edit*first_carbo)
                    var temp_p = round(fir_edit*first_protein)
                    var temp_f = round(fir_edit*first_fat)
                    carbo1 =temp
                    protein1 = temp_p
                    fat1 = temp_f
                    carbo_percent_oneday = ((carbo1+carbo2+carbo3) / my_carbo * 100).toFloat()
                    protein_percent_oneday = ((protein1+protein2+protein3) / my_protein * 100).toFloat()
                    fat_percent_oneday = ((fat1+fat2+fat3) / my_fat * 100).toFloat()
                    println(carbo1+carbo2+carbo3)


                }
                if(my_carbo != 0.0) {
                    updatePieChart1(carbo_percent_oneday, binding.pieChart1)
                    updatePieChart2(protein_percent_oneday, binding.pieChart2)
                    updatePieChart3(fat_percent_oneday, binding.pieChart3)


                }

            }


        })



        binding.tableGram2.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var carbo_percent_oneday = 0f
                var protein_percent_oneday = 0f
                var fat_percent_oneday = 0f
                val inputText = p0.toString()
                if(inputText.isNullOrEmpty())
                {
                    carbo2 = 0.0
                    protein2 = 0.0
                    fat2 =0.0
                    carbo_percent_oneday = ((carbo1+carbo2+carbo3) / my_carbo * 100).toFloat()
                    protein_percent_oneday = ((protein1+protein2+protein3) / my_protein * 100).toFloat()
                    fat_percent_oneday = ((fat1+fat2+fat3) / my_fat * 100).toFloat()


                }
                else {
                    val fir_edit = inputText.toDouble()
                    var temp = round(fir_edit*second_carbo)
                    var temp_p = round(fir_edit*second_protein)
                    var temp_f = round(fir_edit*second_fat)
                    carbo2 =temp
                    protein2 = temp_p
                    fat2 = temp_f
                    carbo_percent_oneday = ((carbo1+carbo2+carbo3) / my_carbo * 100).toFloat()
                    protein_percent_oneday = ((protein1+protein2+protein3) / my_protein * 100).toFloat()
                    fat_percent_oneday = ((fat1+fat2+fat3) / my_fat * 100).toFloat()
                    println(carbo2)
                    println(carbo3)
                }
                if(my_carbo != 0.0) {
                    updatePieChart1(carbo_percent_oneday, binding.pieChart1)
                    updatePieChart2(protein_percent_oneday, binding.pieChart2)
                    updatePieChart3(fat_percent_oneday, binding.pieChart3)


                }
            }

            override fun afterTextChanged(p0: Editable?) {
                var carbo_percent_oneday = 0f
                var protein_percent_oneday = 0f
                var fat_percent_oneday = 0f
                val inputText = p0.toString()
                if(inputText.isNullOrEmpty())
                {
                    carbo2 = 0.0
                    protein2 = 0.0
                    fat2 =0.0
                    carbo_percent_oneday = ((carbo1+carbo2+carbo3) / my_carbo * 100).toFloat()
                    protein_percent_oneday = ((protein1+protein2+protein3) / my_protein * 100).toFloat()
                    fat_percent_oneday = ((fat1+fat2+fat3) / my_fat * 100).toFloat()


                }
                else {
                    val fir_edit = inputText.toDouble()
                    var temp = round(fir_edit*second_carbo)
                    var temp_p = round(fir_edit*second_protein)
                    var temp_f = round(fir_edit*second_fat)
                    carbo2 =temp
                    protein2 = temp_p
                    fat2 = temp_f
                    carbo_percent_oneday = ((carbo1+carbo2+carbo3) / my_carbo * 100).toFloat()
                    protein_percent_oneday = ((protein1+protein2+protein3) / my_protein * 100).toFloat()
                    fat_percent_oneday = ((fat1+fat2+fat3) / my_fat * 100).toFloat()
                    println(carbo2)
                    println(carbo3)

                }
                if(my_carbo != 0.0) {
                    updatePieChart1(carbo_percent_oneday, binding.pieChart1)
                    updatePieChart2(protein_percent_oneday, binding.pieChart2)
                    updatePieChart3(fat_percent_oneday, binding.pieChart3)


                }
            }


        })




        binding.tableGram3.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var carbo_percent_oneday = 0f
                var protein_percent_oneday = 0f
                var fat_percent_oneday = 0f
                val inputText = p0.toString()
                if(inputText.isNullOrEmpty())
                {
                    carbo3 = 0.0
                    protein3 = 0.0
                    fat3 =0.0
                    carbo_percent_oneday = ((carbo1+carbo2+carbo3) / my_carbo * 100).toFloat()
                    protein_percent_oneday = ((protein1+protein2+protein3) / my_protein * 100).toFloat()
                    fat_percent_oneday = ((fat1+fat2+fat3) / my_fat * 100).toFloat()


                }
                else {
                    val fir_edit = inputText.toDouble()
                    var temp = round(fir_edit*third_carbo)
                    var temp_p = round(fir_edit*third_protein)
                    var temp_f = round(fir_edit*third_fat)
                    carbo3 =temp
                    protein3 = temp_p
                    fat3 = temp_f
                    carbo_percent_oneday = ((carbo1+carbo2+carbo3) / my_carbo * 100).toFloat()
                    protein_percent_oneday = ((protein1+protein2+protein3) / my_protein * 100).toFloat()
                    fat_percent_oneday = ((fat1+fat2+fat3) / my_fat * 100).toFloat()
                    println(carbo2)
                    println(carbo3)
                }
                if(my_carbo != 0.0) {
                    updatePieChart1(carbo_percent_oneday, binding.pieChart1)
                    updatePieChart2(protein_percent_oneday, binding.pieChart2)
                    updatePieChart3(fat_percent_oneday, binding.pieChart3)


                }
            }

            override fun afterTextChanged(p0: Editable?) {
                var carbo_percent_oneday = 0f
                var protein_percent_oneday = 0f
                var fat_percent_oneday = 0f
                val inputText = p0.toString()
                if(inputText.isNullOrEmpty())
                {
                    carbo3 = 0.0
                    protein3 = 0.0
                    fat3 =0.0
                    carbo_percent_oneday = ((carbo1+carbo2+carbo3) / my_carbo * 100).toFloat()
                    protein_percent_oneday = ((protein1+protein2+protein3) / my_protein * 100).toFloat()
                    fat_percent_oneday = ((fat1+fat2+fat3) / my_fat * 100).toFloat()


                }
                else {
                    val fir_edit = inputText.toDouble()
                    var temp = round(fir_edit*third_carbo)
                    var temp_p = round(fir_edit*third_protein)
                    var temp_f = round(fir_edit*third_fat)
                    carbo3 =temp
                    protein3 = temp_p
                    fat3 = temp_f
                    carbo_percent_oneday = ((carbo1+carbo2+carbo3) / my_carbo * 100).toFloat()
                    protein_percent_oneday = ((protein1+protein2+protein3) / my_protein * 100).toFloat()
                    fat_percent_oneday = ((fat1+fat2+fat3) / my_fat * 100).toFloat()
                    println(carbo2)
                    println(carbo3)
                }
                if(my_carbo != 0.0) {
                    updatePieChart1(carbo_percent_oneday, binding.pieChart1)
                    updatePieChart2(protein_percent_oneday, binding.pieChart2)
                    updatePieChart3(fat_percent_oneday, binding.pieChart3)


                }

            }


        })


        indivisualButton.setOnClickListener{
            val gramInformation1 = gramInformationEditText1.text.toString()
            val gramInformation2 = gramInformationEditText2.text.toString()
            val gramInformation3 = gramInformationEditText3.text.toString()






/*
            webView = WebView(requireContext())

            webView.settings.javaScriptEnabled =true

            webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE

            webView.webViewClient = WebViewClient()
            webView.loadUrl("http://192.168.0.125/page1?salt="+gramInformation1+"&sugar="+gramInformation2+"&blackpepper="+gramInformation3)

*/
            //println(gramInformation1)

            



        }





        }


    private fun updatePieChart1(inputText:Float,pieChart: PieChart) {
        val pieEntries = mutableListOf<PieEntry>()

        if (inputText in 0f..100f) {
            pieEntries.add(PieEntry(inputText,"탄수화물"))
            pieEntries.add(PieEntry((100f - inputText)))
        } else {
            pieEntries.add(PieEntry(0f))
            pieEntries.add(PieEntry(100f))
        }

        val dataSet = PieDataSet(pieEntries, "")
        dataSet.setDrawIcons(false)

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(10f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.BLACK)
        pieChart.setData(data)
        pieChart.description.isEnabled=false
        pieChart.legend.isEnabled = false

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.GREEN) // Color for carbo_percent_oneday
        colors.add(Color.DKGRAY) // Blank color for the remaining portion
        dataSet.colors = colors


        pieChart.animateY(1000, Easing.EaseInOutQuad)

        pieChart.invalidate()
    }


    private fun updatePieChart2(inputText:Float,pieChart: PieChart) {
        val pieEntries = mutableListOf<PieEntry>()

        if (inputText in 0f..100f) {
            pieEntries.add(PieEntry(inputText,"단백질"))
            pieEntries.add(PieEntry((100f - inputText)))
        } else {
            pieEntries.add(PieEntry(0f))
            pieEntries.add(PieEntry(100f))
        }

        val dataSet = PieDataSet(pieEntries, "")
        dataSet.setDrawIcons(false)

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(10f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.BLACK)
        pieChart.setData(data)
        pieChart.description.isEnabled=false
        pieChart.legend.isEnabled = false

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.GREEN) // Color for carbo_percent_oneday
        colors.add(Color.DKGRAY) // Blank color for the remaining portion
        dataSet.colors = colors


        pieChart.animateY(1000, Easing.EaseInOutQuad)

        pieChart.invalidate()
    }

    private fun updatePieChart3(inputText:Float,pieChart: PieChart) {
        val pieEntries = mutableListOf<PieEntry>()

        if (inputText in 0f..100f) {
            pieEntries.add(PieEntry(inputText,"지방"))
            pieEntries.add(PieEntry((100f - inputText)))
        } else {
            pieEntries.add(PieEntry(0f))
            pieEntries.add(PieEntry(100f))
        }

        val dataSet = PieDataSet(pieEntries, "")
        dataSet.setDrawIcons(false)

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(10f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.BLACK)
        pieChart.setData(data)
        pieChart.description.isEnabled=false
        pieChart.legend.isEnabled = false

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.GREEN) // Color for carbo_percent_oneday
        colors.add(Color.DKGRAY) // Blank color for the remaining portion
        dataSet.colors = colors


        pieChart.animateY(1000, Easing.EaseInOutQuad)

        pieChart.invalidate()
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

