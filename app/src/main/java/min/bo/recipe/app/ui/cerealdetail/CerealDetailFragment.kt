package min.bo.recipe.app.ui.cerealdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import min.bo.recipe.app.common.KEY_CEREAL_ID
import min.bo.recipe.app.common.KEY_CEREAL_NAME
import min.bo.recipe.app.databinding.FragmentCerealDetailBinding

class CerealDetailFragment: Fragment() {

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


        val cerealId = requireArguments().getString(KEY_CEREAL_ID)
        val cerealName = requireArguments().getString(KEY_CEREAL_NAME)
        binding.toolbarCerealDetail.title = cerealName
    }
}