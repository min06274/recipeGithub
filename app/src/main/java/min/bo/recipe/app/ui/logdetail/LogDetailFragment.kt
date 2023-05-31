package min.bo.recipe.app.ui.logdetail

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import min.bo.recipe.app.common.*
import min.bo.recipe.app.databinding.FragmentLogDetailBinding

class LogDetailFragment: Fragment() {

    private lateinit var binding:FragmentLogDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner=viewLifecycleOwner
        setNavigation()

        val date = requireArguments().getString(KEY_LOG_DATE)
        val time = requireArguments().getString(KEY_LOG_TIME)
        val cartridge1 = requireArguments().getString(KEY_CARTRIDGE_NAME1)
        val cartridge2 = requireArguments().getString(KEY_CARTRIDGE_NAME2)
        val cartridge3 = requireArguments().getString(KEY_CARTRIDGE_NAME3)
        val cartridge_gram1 = requireArguments().getString(KEY_CARTRIDGE_GRAM1)
        val cartridge_gram2 = requireArguments().getString(KEY_CARTRIDGE_GRAM2)
        val cartridge_gram3 = requireArguments().getString(KEY_CARTRIDGE_GRAM3)
        val print_carbo = requireArguments().getString(KEY_CARBO)
        val print_protein = requireArguments().getString(KEY_PROTEIN)
        val print_fat = requireArguments().getString(KEY_FAT)
        val my_carbo = requireArguments().getString(KEY_MY_CARBO)
        val my_protein = requireArguments().getString(KEY_MY_PROTEIN)
        val my_fat = requireArguments().getString(KEY_MY_FAT)
        val carbo_percent = requireArguments().getString(KEY_CARBO_PERCENT)
        val protein_percent = requireArguments().getString(KEY_PROTEIN_PERCENT)
        val fat_percent = requireArguments().getString(KEY_FAT_PERCENT)
        val print_kcal = requireArguments().getString(KEY_PRINT_KCAL)
        val total_kcal = requireArguments().getString(KEY_TOTAL_KCAL)
        val kcal_percent = requireArguments().getString(KEY_KCAL_PERCENT)




        binding.toolbarLogDetail.title = date + "   " +time

        binding.logPrintCereals.text = cartridge1 +" : " +cartridge_gram1 + "g\n" + cartridge2 + " : " + cartridge_gram2 + "g\n" + cartridge3 + " : " + cartridge_gram3 + "g을 출력했습니다."


        binding.chart1Title.text = print_carbo + " / " + my_carbo
        binding.chart2Title.text = print_protein + " / " + my_protein
        binding.chart3Title.text = print_fat + " / " + my_fat
        binding.chart4Title.text = print_kcal + " / " + total_kcal

        binding.pieChart1.setUsePercentValues(true)


        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(carbo_percent!!.toFloat(),"탄수화물"))
        entries.add(PieEntry(100f-carbo_percent!!.toFloat(),""))



        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)



        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(10f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.BLACK)
        binding.pieChart1.setData(data)
        binding.pieChart1.description.isEnabled=false
        binding.pieChart1.legend.isEnabled = false







        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.GREEN) // Color for carbo_percent_oneday
        colors.add(Color.DKGRAY) // Blank color for the remaining portion
        dataSet.colors = colors


        binding.pieChart1.animateY(1000, Easing.EaseInOutQuad)

        binding.pieChart1.invalidate()


        binding.pieChart2.setUsePercentValues(true)

        val entries2:ArrayList<PieEntry> = ArrayList()
        entries2.add(PieEntry(protein_percent!!.toFloat(),"단백질"))
        entries2.add(PieEntry(100f-protein_percent!!.toFloat(),""))



        val dataSet2 = PieDataSet(entries2,"")
        dataSet2.setDrawIcons(false)

        val data2 = PieData(dataSet2)
        data2.setValueFormatter(PercentFormatter())

        data2.setValueTextSize(10f)
        data2.setValueTypeface(Typeface.DEFAULT_BOLD)
        data2.setValueTextColor(Color.BLACK)
        binding.pieChart2.setData(data2)
        binding.pieChart2.description.isEnabled=false
        binding.pieChart2.legend.isEnabled=false


        val colors2: ArrayList<Int> = ArrayList()
        colors2.add(Color.RED)
        colors2.add(Color.DKGRAY)
        dataSet2.colors = colors2


        binding.pieChart2.animateY(1000, Easing.EaseInOutQuad)

        binding.pieChart2.invalidate()



        binding.pieChart3.setUsePercentValues(true)

        val entries3:ArrayList<PieEntry> = ArrayList()
        entries3.add(PieEntry(fat_percent!!.toFloat(),"지방"))
        entries3.add(PieEntry(100f-fat_percent!!.toFloat(),""))



        val dataSet3 = PieDataSet(entries3,"")
        dataSet3.setDrawIcons(false)

        val data3 = PieData(dataSet3)
        data3.setValueFormatter(PercentFormatter())

        data3.setValueTextSize(10f)
        data3.setValueTypeface(Typeface.DEFAULT_BOLD)
        data3.setValueTextColor(Color.BLACK)
        binding.pieChart3.setData(data3)
        binding.pieChart3.description.isEnabled=false
        binding.pieChart3.legend.isEnabled=false


        val colors3: ArrayList<Int> = ArrayList()
        colors3.add(Color.BLUE)
        colors3.add(Color.DKGRAY)
        dataSet3.colors = colors3


        binding.pieChart3.animateY(1000, Easing.EaseInOutQuad)

        binding.pieChart3.invalidate()



        binding.pieChart4.setUsePercentValues(true)

        val entries4:ArrayList<PieEntry> = ArrayList()
        entries4.add(PieEntry(kcal_percent!!.toFloat(),"칼로리"))
        entries4.add(PieEntry(100f-kcal_percent!!.toFloat(),""))



        val dataSet4= PieDataSet(entries4,"")
        dataSet4.setDrawIcons(false)

        val data4 = PieData(dataSet4)
        data4.setValueFormatter(PercentFormatter())

        data4.setValueTextSize(10f)
        data4.setValueTypeface(Typeface.DEFAULT_BOLD)
        data4.setValueTextColor(Color.BLACK)
        binding.pieChart4.setData(data4)
        binding.pieChart4.description.isEnabled=false
        binding.pieChart4.legend.isEnabled=false


        val colors4: ArrayList<Int> = ArrayList()
        colors4.add(Color.CYAN)
        colors4.add(Color.DKGRAY)
        dataSet4.colors = colors4


        binding.pieChart4.animateY(1000, Easing.EaseInOutQuad)

        binding.pieChart4.invalidate()


        println(requireArguments().getString(KEY_CARTRIDGE_NAME1))

        println(requireArguments().getString(KEY_CARBO_PERCENT))
    }



    private fun setNavigation(){
        binding.toolbarLogDetail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}