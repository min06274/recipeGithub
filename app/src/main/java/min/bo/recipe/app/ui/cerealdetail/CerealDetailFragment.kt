package min.bo.recipe.app.ui.cerealdetail

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import min.bo.recipe.app.common.*
import min.bo.recipe.app.databinding.FragmentCerealDetailBinding
import min.bo.recipe.app.model.CerealData

class CerealDetailFragment: Fragment() {
    private val database1 = Firebase.database("https://cereal-22a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val myRef = database1.getReference("top_banners")

    private val database2 = Firebase.database("https://cereal-22a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val cerealListRef = database2.getReference("cereal_list")
    private lateinit var binding: FragmentCerealDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCerealDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=viewLifecycleOwner

        setNavigation()
        binding.deleteBtn.setBackgroundColor(Color.parseColor("#FF0000"))

        val cerealId = requireArguments().getString(KEY_CEREAL_ID)
        val cerealName = requireArguments().getString(KEY_CEREAL_NAME)
        val cerealImage = requireArguments().getString(KEY_CEREAL_IMAGE)
        val cerealInformation = requireArguments().getString(KEY_CEREAL_INFORMATION)
        val cerealKcal = requireArguments().getString(KEY_CEREAL_KCAL)
        val cerealPurchase = requireArguments().getString(KEY_CEREAL_URL)
        binding.toolbarCerealDetail.title = cerealName
        binding.cerealDetailInformation.text = cerealInformation
        binding.cerealDetailKcal.text = cerealKcal

        Glide.with(requireContext())
            .load(cerealImage)
            .into(binding.cerealDeatilImg);


        binding.deleteBtn.setOnClickListener{
            val query = cerealListRef.orderByChild("cereal_id").equalTo(cerealId)

            println("두두두두")
            println(cerealId)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val childSnapshot = dataSnapshot.children.firstOrNull()

                    childSnapshot?.let {
                        binding.deleteBtn.text= "삭제 됨"

                        val cerealIndex = it.key?.toInt()
                        var totalChildrenCount:Long = 3





                        cerealListRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                totalChildrenCount =  dataSnapshot.childrenCount

                                cerealIndex?.let { index ->
                                    for (i in index until totalChildrenCount - 1) {
                                        val currentChild = dataSnapshot.child((i + 1).toString())
                                        val currentChildRef = cerealListRef.child(i.toString())
                                        currentChildRef.setValue(currentChild.value)
                                        currentChildRef.child("cereal_id").setValue(i.toString())
                                    }

                                    // Delete the last child
                                    cerealListRef.child((totalChildrenCount - 1).toString()).removeValue()
                                }

                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                println("Failed to read cereals")
                            }
                        })


                        println("두두두")
                        println(totalChildrenCount)


                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle the error if necessary
                }
            })


            findNavController().navigateUp()

        }



        binding.cerealPurchaseBtn.setOnClickListener {
            val webpage: Uri = Uri.parse(cerealPurchase)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
                startActivity(intent)

        }

        binding.cerealDetailCarboBtn.setOnClickListener{
            binding.cerealDetailCarboBtn.text="추가됨"
            myRef.child(0.toString()).child("background_image_url")
                .setValue(cerealImage)
            myRef.child(0.toString()).child("product_detail").child("brand_name")
                .setValue(cerealName)
            myRef.child(0.toString()).child("product_detail").child("information")
                .setValue(cerealInformation)
            myRef.child(0.toString()).child("product_detail").child("kcal")
                .setValue(cerealKcal)
        }

        binding.cerealDetailProteinBtn.setOnClickListener{
            binding.cerealDetailProteinBtn.text="추가됨"
            myRef.child(1.toString()).child("background_image_url")
                .setValue(cerealImage)
            myRef.child(1.toString()).child("product_detail").child("brand_name")
                .setValue(cerealName)
            myRef.child(1.toString()).child("product_detail").child("information")
                .setValue(cerealInformation)
            myRef.child(1.toString()).child("product_detail").child("kcal")
                .setValue(cerealKcal)
        }

        binding.cerealDetailFatBtn.setOnClickListener{
            binding.cerealDetailFatBtn.text="추가됨"
            myRef.child(2.toString()).child("background_image_url")
                .setValue(cerealImage)
            myRef.child(2.toString()).child("product_detail").child("brand_name")
                .setValue(cerealName)
            myRef.child(2.toString()).child("product_detail").child("information")
                .setValue(cerealInformation)
            myRef.child(2.toString()).child("product_detail").child("kcal")
                .setValue(cerealKcal)
        }

    }

    private fun setNavigation(){
        binding.toolbarCerealDetail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }


}