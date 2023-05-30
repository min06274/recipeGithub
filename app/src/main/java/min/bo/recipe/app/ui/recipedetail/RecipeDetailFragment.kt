package min.bo.recipe.app.ui.recipedetail

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
import com.github.mikephil.charting.utils.MPPointF
import min.bo.recipe.app.common.KEY_CARBO
import min.bo.recipe.app.common.KEY_FAT
import min.bo.recipe.app.common.KEY_PROTEIN
import min.bo.recipe.app.common.KEY_RESULT
import min.bo.recipe.app.databinding.FragmentRecipeDetailBinding

class RecipeDetailFragment:Fragment() {


    private lateinit var binding:FragmentRecipeDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentRecipeDetailBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner

        setNavigation()


        println(requireArguments().getInt(KEY_CARBO))
        println(requireArguments().getInt(KEY_PROTEIN))
        println(requireArguments().getInt(KEY_FAT))
        println(requireArguments().getDouble(KEY_RESULT))

        val carbo_one_percent = 3.3*requireArguments().getDouble(KEY_RESULT)/2000 //자신의 기초대사량에서 1%인 탄수g
        //넘어온 탄수 g값이 자신의 탄수화물 일일섭취량의 몇퍼센트인지
        val carbo_percent_oneday = (requireArguments().getInt(KEY_CARBO) / carbo_one_percent).toFloat()


        println("carbopercent_oneday"+carbo_percent_oneday)
        binding.pieChart.setUsePercentValues(true)
        val carboPercentage = requireArguments().getInt(KEY_CARBO).toFloat()

        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(carbo_percent_oneday,"탄수화물"))
        entries.add(PieEntry(100f-carbo_percent_oneday,""))

        val dataSet = PieDataSet(entries, "")
        dataSet.setDrawIcons(false)


        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        binding.pieChart.setData(data)
        binding.pieChart.description.isEnabled = false
        binding.pieChart.legend.isEnabled = false



        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.GREEN) // Color for carbo_percent_oneday
        colors.add(Color.DKGRAY) // Blank color for the remaining portion
        dataSet.colors = colors


        binding.pieChart.animateY(1000, Easing.EaseInOutQuad)

        binding.pieChart.invalidate()

    }

    private fun setNavigation(){
        binding.toolbarRecipeDetail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}