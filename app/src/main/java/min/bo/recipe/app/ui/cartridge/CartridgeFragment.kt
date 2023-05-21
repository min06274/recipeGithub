package min.bo.recipe.app.ui.cartridge

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import min.bo.recipe.app.R
import min.bo.recipe.app.model.Cereal
import min.bo.recipe.app.ui.common.ViewModelFactory

class CartridgeFragment:Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cartridge,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val database1 = Firebase.database("https://cereal-22a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val myRef = database1.getReference("top_banners")



        val database = FirebaseDatabase.getInstance("https://cereal-22a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
        val cerealsRef = database.getReference("cereals")

        val jsonButton: Button = view.findViewById(R.id.json_btn)

        jsonButton.setOnClickListener {
            //change_information(cerealsRef, myRef)

            addCereals(cerealsRef)



        }




    }


    private fun addCereals(cerealsRef: DatabaseReference){
        val cereal1 = Cereal(
            "1번시리얼",
            "30g당 탄수 5g"
        )

        cerealsRef.child("1").setValue(cereal1)
    }

    private fun change_information(
        cerealsRef: DatabaseReference,
        myRef: DatabaseReference
    ) {
        cerealsRef.child("0").child("information").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val information = task.result?.value as? String
                myRef.child("0").child("product_detail").child("information").setValue(information)
                println(information)
            } else {
                println("Failed to read cereal information")
            }
        }
    }

}