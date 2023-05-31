package min.bo.recipe.app.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import min.bo.recipe.app.R
import min.bo.recipe.app.common.*
import java.math.BigDecimal
import kotlin.concurrent.thread
import kotlin.math.abs
import kotlin.reflect.typeOf

class RecipeFragment: Fragment() {

    private lateinit var webView: WebView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val printButton: Button = view.findViewById(R.id.print_button)



        val printText: TextView = view.findViewById(R.id.metabolic)

        val kcalText:TextView=view.findViewById(R.id.cereals_kcal)

        val gramText:TextView=view.findViewById(R.id.cereals_gram)

        val coroutineScope = CoroutineScope(Dispatchers.Main)

        val genderRadioGroup: RadioGroup = view.findViewById(R.id.genderRadioGroup)

        val purposeRadioGroup:RadioGroup = view.findViewById(R.id.purposeRadioGroup)

        val spinner: Spinner = view.findViewById(R.id.spinner)
        var selectedItem: String = "26"
        var selectedGender: String = "남성"
        var selectedPurpose:String = "일반"
        val items = resources.getStringArray(R.array.items)

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedItem = parent?.getItemAtPosition(position).toString()
                println(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //TODO Handle the case when no item is selected
            }
        }

        genderRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton: RadioButton =
                view.findViewById(genderRadioGroup.checkedRadioButtonId)
            selectedGender = selectedRadioButton.text.toString()
            println(selectedGender)
        }

        purposeRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton: RadioButton =
                view.findViewById(purposeRadioGroup.checkedRadioButtonId)
            selectedPurpose = selectedRadioButton.text.toString()
            println(selectedPurpose)
        }


        printButton.setOnClickListener {
            val weightEdit: EditText = view.findViewById(R.id.weight_information)
            val heightEdit: EditText = view.findViewById(R.id.height_information)
            val weightString: String = weightEdit.text.toString()
            val heightString: String = heightEdit.text.toString()


            var result:Double = 0.0
            if(weightString.isNotEmpty() && heightString.isNotEmpty()) {

                if (selectedGender == "남성") {
                    result =
                        66.5 + (13.7 * weightString.toDouble()) + (5 * heightString.toDouble()) - (6.8 * selectedItem.toInt())
                }
                else{
                    result = 655.1 +(9.563*weightString.toDouble()) +(1.85*heightString.toDouble())-(4.7*selectedItem.toInt())

                }

                if(selectedPurpose =="벌크업"){
                    result+=300
                }
                else if(selectedPurpose=="다이어트")
                {
                    result-=300
                }
                printText.text= result.toString()

            }


            val result_to = result
            val database1 = Firebase.database("https://cereal-22a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
            val myRef = database1.getReference("top_banners")


            var carbo_gram_kcal= 0
            var protein_gram_kcal = 0
            var fat_gram_kcal = 0


            var cartridge1Carbo = 0.0
            var cartridge1Protein= 0.0
            var cartridge1Fat=0.0

            var cartridge2Carbo = 0.0
            var cartridge2Protein= 0.0
            var cartridge2Fat=0.0

            var cartridge3Carbo = 0.0
            var cartridge3Protein= 0.0
            var cartridge3Fat=0.0



            myRef.child("0").child("product_detail").child("information").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val carbo_string = dataSnapshot.getValue(String::class.java)

                    cartridge1Carbo = carbo_string?.slice(13..14)!!.toDouble()/100

                    cartridge1Protein = carbo_string?.slice(21..22)!!.toDouble()/100

                    cartridge1Fat = carbo_string?.slice(28..29)!!.toDouble()/100


                }



                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle any errors that occur during the retrieval
                }
            })


            myRef.child("1").child("product_detail").child("information").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val carbo_string = dataSnapshot.getValue(String::class.java)

                    cartridge2Carbo = carbo_string?.slice(13..14)!!.toDouble()/100

                    cartridge2Protein = carbo_string?.slice(21..22)!!.toDouble()/100

                    cartridge2Fat = carbo_string?.slice(28..29)!!.toDouble()/100


                }



                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle any errors that occur during the retrieval
                }
            })


            myRef.child("2").child("product_detail").child("information").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val carbo_string = dataSnapshot.getValue(String::class.java)

                    cartridge3Carbo = carbo_string?.slice(13..14)!!.toDouble()/100

                    cartridge3Protein = carbo_string?.slice(21..22)!!.toDouble()/100

                    cartridge3Fat = carbo_string?.slice(28..29)!!.toDouble()/100


                }



                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle any errors that occur during the retrieval
                }
            })




            myRef.child("0").child("product_detail").child("kcal").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val kcal = dataSnapshot.getValue(String::class.java)
                    carbo_gram_kcal = kcal!!.toInt()



                }



                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle any errors that occur during the retrieval
                }
            })


            myRef.child("1").child("product_detail").child("kcal").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val kcal = dataSnapshot.getValue(String::class.java)
                    protein_gram_kcal = kcal!!.toInt()


                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle any errors that occur during the retrieval
                }
            })

            myRef.child("2").child("product_detail").child("kcal").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val kcal = dataSnapshot.getValue(String::class.java)
                    fat_gram_kcal = kcal!!.toInt()



                }


                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle any errors that occur during the retrieval
                }
            })


            Handler(Looper.getMainLooper()).postDelayed({
                kcalText.text = "1번 : " + carbo_gram_kcal.toString() + "k | 2번 : " + protein_gram_kcal.toString() + "k | 3번 : " + fat_gram_kcal.toString()+"k"
                result = result *0.15
                val first_cereal = listOf(carbo_gram_kcal,cartridge1Carbo,cartridge1Protein,cartridge1Fat)
                val second_cereal = listOf(protein_gram_kcal,cartridge2Carbo,cartridge2Protein,cartridge2Fat)
                val third_cereal = listOf(fat_gram_kcal,cartridge3Carbo,cartridge3Protein,cartridge3Fat)




                var first_gram = 20
                var second_gram = 20
                var third_gram = 20
                var total_kcal = first_gram*first_cereal[0].toInt()+second_gram*second_cereal[0].toInt()+third_gram*third_cereal[0].toInt()


                if (total_kcal >=result)
                {

                    while(total_kcal >= result)
                    {
                        first_gram-=1

                        second_gram-=1
                        third_gram-=1
                        total_kcal = first_gram*first_cereal[0].toInt()+second_gram*second_cereal[0].toInt()+third_gram*third_cereal[0].toInt()
                    }
                }

                else if(total_kcal < result)
                {

                    while(total_kcal<result)
                    {
                        first_gram+=1

                        second_gram+=1
                        third_gram+=1
                        total_kcal = first_gram*first_cereal[0].toInt()+second_gram*second_cereal[0].toInt()+third_gram*third_cereal[0].toInt()
                    }

                }

                println(first_gram)
                println(second_gram)
                println(third_gram)

                println(result)
                println(total_kcal)
                val carbo_gram = first_cereal[1].toDouble()*first_gram + second_cereal[1].toDouble()*second_gram+third_cereal[1].toDouble()*third_gram
                val protein_gram = first_cereal[2].toDouble()*first_gram + second_cereal[2].toDouble()*second_gram+third_cereal[2].toDouble()*third_gram
                val fat_gram = first_cereal[3].toDouble()*first_gram + second_cereal[3].toDouble()*second_gram+third_cereal[3].toDouble()*third_gram



                //gramText.text = "1번 : " +carbo_gram.toString() +"g | 2번 : " +protein_gram.toString() + "g | 3번 : " +fat_gram.toString()+"g"


                openRecipeDetail(first_gram,second_gram,third_gram,carbo_gram,protein_gram,fat_gram,total_kcal,result_to)

                /*
                webView = WebView(requireContext())

                webView.settings.javaScriptEnabled =true

                webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE

                webView.webViewClient = WebViewClient()
                webView.loadUrl("http://192.168.0.125/page1?salt="+carbo_gram.toString()+"&sugar="+protein_gram.toString()+"&blackpepper="+fat_gram.toString())
*/
            }, 3000)

        }
    }

    private fun openRecipeDetail(first_gram:Int,second_gram:Int,third_gram:Int,carbo_gram:Double,protein_gram:Double,fat_gram:Double,print_kcal:Int,total_kcal:Double){
        findNavController().navigate(R.id.action_recipe_to_recipe_detail, bundleOf(


            KEY_CARTRIDGE_GRAM1 to first_gram,
            KEY_CARTRIDGE_GRAM2 to second_gram,
            KEY_CARTRIDGE_GRAM3 to third_gram,

            KEY_CARBO to carbo_gram,
            KEY_PROTEIN to protein_gram,
            KEY_FAT to fat_gram,

            KEY_PRINT_KCAL to print_kcal,
            KEY_TOTAL_KCAL to total_kcal


        ))
    }



}