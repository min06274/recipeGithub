package min.bo.recipe.app.ui.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import min.bo.recipe.app.R
import min.bo.recipe.app.databinding.FragmentListBinding
import min.bo.recipe.app.model.CerealData
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

        val cerealAdapter= CerealListAdapter()
        binding.rvList.adapter = cerealAdapter

        viewModel.items.observe(viewLifecycleOwner){
            cerealAdapter.submitList(it)
        }
    }

}