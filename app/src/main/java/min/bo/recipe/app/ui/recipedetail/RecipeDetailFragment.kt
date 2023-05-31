package min.bo.recipe.app.ui.recipedetail

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import min.bo.recipe.app.common.*
import min.bo.recipe.app.databinding.FragmentRecipeDetailBinding
import min.bo.recipe.app.model.CerealData
import min.bo.recipe.app.model.LogData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Arrays.toString
import kotlin.math.round

class RecipeDetailFragment:Fragment() {



    private val database1 = Firebase.database("https://cereal-22a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val logRef = database1.getReference("log_list")

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

        val cartridge1 = requireArguments().getInt(KEY_CARTRIDGE_GRAM1)
        val cartridge2 = requireArguments().getInt(KEY_CARTRIDGE_GRAM2)
        val cartridge3 = requireArguments().getInt(KEY_CARTRIDGE_GRAM3)


        binding.printCereals.text = "1번 : " + cartridge1.toString() + "g 2번 : " +cartridge2.toString() + "g 3번 : "+cartridge3.toString() + "g 이 출력됩니다."



        val print_kcal = requireArguments().getInt(KEY_PRINT_KCAL)

        val my_kcal = round(requireArguments().getDouble(KEY_TOTAL_KCAL))
        val print_carbo = round(requireArguments().getDouble(KEY_CARBO))
        val print_protein = round(requireArguments().getDouble(KEY_PROTEIN))
        val print_fat = round(requireArguments().getDouble(KEY_FAT))

        val my_carbo = round(333.75*my_kcal/2000)
        val my_protein = round(50*my_kcal/2000)
        val my_fat = round(40*my_kcal/2000)

        //넘어온 탄수 g값이 자신의 탄수화물 일일섭취량의 몇퍼센트인지
        val carbo_percent_oneday = (print_carbo / my_carbo*100).toFloat()

        val proetein_percent_oneday = (print_protein / my_protein*100).toFloat()


        val fat_percent_oneday = (print_fat / my_fat*100).toFloat()


        val kcal_percent_oneday = (print_kcal/my_kcal*100).toFloat()

        binding.chart1Title.text = print_carbo.toString() + " / " + my_carbo.toString()
        binding.chart2Title.text = print_protein.toString() + " / " + my_protein.toString()
        binding.chart3Title.text = print_fat.toString() + " / " + my_fat.toString()
        binding.chart4Title.text = print_kcal.toString() + " / " + my_kcal.toString()


        binding.pieChart1.setUsePercentValues(true)


        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(carbo_percent_oneday,"탄수화물"))
        entries.add(PieEntry(100f-carbo_percent_oneday,""))



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
        entries2.add(PieEntry(proetein_percent_oneday,"단백질"))
        entries2.add(PieEntry(100f-proetein_percent_oneday,""))



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
        entries3.add(PieEntry(fat_percent_oneday,"지방"))
        entries3.add(PieEntry(100f-fat_percent_oneday,""))



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
        entries4.add(PieEntry(kcal_percent_oneday,"칼로리"))
        entries4.add(PieEntry(100f-kcal_percent_oneday,""))



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
        colors4.add(Color.YELLOW)
        colors4.add(Color.DKGRAY)
        dataSet4.colors = colors4


        binding.pieChart4.animateY(1000, Easing.EaseInOutQuad)

        binding.pieChart4.invalidate()



        binding.addLogBtn.setOnClickListener{

            logRef.addListenerForSingleValueEvent(object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    val count = dataSnapshot.childrenCount
                    val newLogRef = logRef.child((count).toString())

                    var now = LocalDate.now()
                    var Strnow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    val currentTime = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("HH:mm")
                    val formattedTime = currentTime.format(formatter).toString()

                    val newLog = LogData(
                        date = Strnow,
                        time = formattedTime,
                        cartridge1 = "1",
                        cartridge2 = "1",
                        cartridge3 = "1",
                        cartridge_gram1 = "1",
                        cartridge_gram2 = "1",
                        cartridge_gram3 = "1",
                        carbo_gram = "1",
                        protein_gram = "1",
                        fat_gram = "1",
                        carbo_percent = "1%",
                        protein_percent = "2%",
                        fat_percent = "3%",
                        print_kcal = "250",
                        total_kcal = "1800",
                        log_id = count.toString()

                    )

                    newLogRef.setValue(newLog)


                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("Failed to read cereals")
                }
            })

        }

    }

    private fun setNavigation(){
        binding.toolbarRecipeDetail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}