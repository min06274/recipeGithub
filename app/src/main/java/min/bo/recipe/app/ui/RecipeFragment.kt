package min.bo.recipe.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import min.bo.recipe.app.R

class RecipeFragment: Fragment() {

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
        }
    }
}