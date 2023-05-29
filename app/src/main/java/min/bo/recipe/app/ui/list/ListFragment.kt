package min.bo.recipe.app.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import min.bo.recipe.app.R
import min.bo.recipe.app.common.*
import min.bo.recipe.app.databinding.FragmentListBinding
import min.bo.recipe.app.model.CerealData
import min.bo.recipe.app.ui.common.EventObserver
import min.bo.recipe.app.ui.common.ViewModelFactory

class ListFragment: Fragment() {


    private val viewModel:ListViewModel by viewModels { ViewModelFactory(requireContext()) }

    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater,container,false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cerealAdapter= CerealListAdapter(viewModel)
        binding.rvList.adapter = cerealAdapter

        viewModel.items.observe(viewLifecycleOwner){
            cerealAdapter.submitList(it)
        }

        viewModel.openCerealEvent.observe(viewLifecycleOwner, EventObserver{
            openCerealDetail(it.cereal_id,it.name,it.cereal_image_url,it.information,it.cereal_kcal,it.cereal_purchase_url)

        })

    }

    override fun onResume() {
        super.onResume()
        // Perform the refresh logic here
        println("dldldl")
    }
    private fun openCerealDetail(cerealId:String,cerealName:String,cerealImage:String,cerealInformation:String,cerealKcal:String,cerealPurchase:String){
        findNavController().navigate(R.id.action_list_to_cereal_detail, bundleOf(
                KEY_CEREAL_ID to cerealId,
                KEY_CEREAL_NAME to cerealName,
                KEY_CEREAL_IMAGE to cerealImage,
                KEY_CEREAL_INFORMATION to cerealInformation,
                KEY_CEREAL_KCAL to cerealKcal,
                KEY_CEREAL_URL to cerealPurchase
                ))
    }
}