package min.bo.recipe.app.ui.recipedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

    }

    private fun setNavigation(){
        binding.toolbarRecipeDetail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}