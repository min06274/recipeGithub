package min.bo.recipe.app.ui.logdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import min.bo.recipe.app.common.KEY_LOG_DATE
import min.bo.recipe.app.databinding.FragmentLogDetailBinding

class LogDetailFragment: Fragment() {

    private lateinit var binding:FragmentLogDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner=viewLifecycleOwner
        setNavigation()

        binding.toolbarLogDetail.title = requireArguments().getString(KEY_LOG_DATE)
    }



    private fun setNavigation(){
        binding.toolbarLogDetail.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
}