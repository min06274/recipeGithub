package min.bo.recipe.app.ui.cerealdetail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import min.bo.recipe.app.common.*
import min.bo.recipe.app.databinding.FragmentCerealDetailBinding

class CerealDetailFragment: Fragment() {
    private val database1 = Firebase.database("https://cereal-22a02-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private val myRef = database1.getReference("top_banners")
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

        binding.cerealPurchaseBtn.setOnClickListener {
            val webpage: Uri = Uri.parse(cerealPurchase)
            val intent = Intent(Intent.ACTION_VIEW, webpage)
                startActivity(intent)

        }

        binding.cerealDetailCarboBtn.setOnClickListener{
            binding.cerealDetailCarboBtn.text="눌림"
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
            binding.cerealDetailProteinBtn.text="눌림"
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
            binding.cerealDetailFatBtn.text="눌림"
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