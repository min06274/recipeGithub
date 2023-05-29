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
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import min.bo.recipe.app.R

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

            val database1 = Firebase.database("https://cereal-22a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
            val myRef = database1.getReference("top_banners")


            var carbo_gram_kcal= 0
            var protein_gram_kcal = 0
            var fat_gram_kcal = 0

            myRef.child("0").child("product_detail").child("kcal").addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val kcal = dataSnapshot.getValue(String::class.java)
                    carbo_gram_kcal = kcal!!.toInt()

                    /*
                    coroutineScope.launch {
                        delayAndUpdateKcalText(gramText,kcalText,carbo_gram_kcal,protein_gram_kcal,fat_gram_kcal,result)
                    }
                    */


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
                result/=5
                var carbo_gram = 20
                var protein_gram = 20
                var fat_gram = 20
                var total_kcal = carbo_gram*carbo_gram_kcal+protein_gram*protein_gram_kcal+fat_gram*fat_gram_kcal


                if (total_kcal >=result)
                {

                    while(total_kcal >= result)
                    {
                        carbo_gram -=1
                        fat_gram-=1
                        total_kcal = carbo_gram*carbo_gram_kcal+protein_gram*protein_gram_kcal+fat_gram*fat_gram_kcal
                    }
                }

                else if(total_kcal < result)
                {

                    while(total_kcal<result)
                    {
                        protein_gram+=3
                        carbo_gram +=1
                        fat_gram +=1
                        total_kcal = carbo_gram*carbo_gram_kcal+protein_gram*protein_gram_kcal+fat_gram*fat_gram_kcal
                    }

                }

                gramText.text = "1번 : " +carbo_gram.toString() +"g | 2번 : " +protein_gram.toString() + "g | 3번 : " +fat_gram.toString()+"g"


                webView = WebView(requireContext())

                webView.settings.javaScriptEnabled =true

                webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE

                webView.webViewClient = WebViewClient()
                webView.loadUrl("http://192.168.0.125/page1?salt="+carbo_gram.toString()+"&sugar="+protein_gram.toString()+"&blackpepper="+fat_gram.toString())

            }, 3000)

        }
    }


    /*
    suspend fun delayAndUpdateKcalText(gramText:TextView,kcalText:TextView, carbo_gram_kcal:Double,protein_gram_kcal:Double,fat_gram_kcal:Double,result:Double) {
        delay(4000) // Delay for 1 second (adjust the duration as needed)

        // Update the kcalText
        withContext(Dispatchers.Main) {

            var rresult = result


            var ccarbo_gram_kcal = carbo_gram_kcal
            var pprotein_gram_kcal = protein_gram_kcal
            var ffat_gram_kcal = fat_gram_kcal

            kcalText.text = "탄수 시리얼: " + ccarbo_gram_kcal.toString() + " 단백질 시리얼: " + pprotein_gram_kcal.toString() + " 지방 시리얼: " + ffat_gram_kcal.toString()

            var carbo_gram = 20
            var protein_gram = 20
            var fat_gram = 20



            rresult/=5
            var total_kcal = carbo_gram*ccarbo_gram_kcal+protein_gram*pprotein_gram_kcal+fat_gram*ffat_gram_kcal

            println(total_kcal)
            println(rresult)
            println("carbo")
            println(carbo_gram)
            if (total_kcal >=rresult)
            {

                while(total_kcal >= rresult)
                {
                    carbo_gram -=1
                    fat_gram-=1
                    total_kcal = carbo_gram*ccarbo_gram_kcal+protein_gram*pprotein_gram_kcal+fat_gram*ffat_gram_kcal
                }
            }

            else if(total_kcal < rresult)
            {


                while(total_kcal<rresult)
                {
                    protein_gram+=3
                    carbo_gram +=1
                    fat_gram +=1
                    total_kcal = carbo_gram*ccarbo_gram_kcal+protein_gram*pprotein_gram_kcal+fat_gram*ffat_gram_kcal
                }
            }



            gramText.text = "탄수 :" +carbo_gram.toString() +" 단백질 : " +protein_gram.toString() + " 지방 : " +fat_gram.toString()



        }



    }

    */
}