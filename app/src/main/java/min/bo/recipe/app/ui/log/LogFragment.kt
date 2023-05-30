package min.bo.recipe.app.ui.log

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import min.bo.recipe.app.R
import min.bo.recipe.app.common.KEY_LOG_DATE
import min.bo.recipe.app.common.KEY_LOG_TIME
import min.bo.recipe.app.databinding.FragmentLogBinding
import min.bo.recipe.app.model.LogData
import min.bo.recipe.app.ui.common.EventObserver
import min.bo.recipe.app.ui.common.ViewModelFactory
import min.bo.recipe.app.ui.list.ListViewModel

class LogFragment: Fragment() {

    private val viewModel: LogViewModel by viewModels { ViewModelFactory(requireContext()) }

    private lateinit var binding:FragmentLogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val logAdapter = LogAdapter(viewModel)
        binding.rvLog.adapter=logAdapter
        viewModel.items.observe(viewLifecycleOwner){
            logAdapter.submitList(it)
        }


        viewModel.openLogEvent.observe(viewLifecycleOwner, EventObserver{
            openLogDetail(it.date,it.time)

        })
    }



    private fun openLogDetail(logDate:String,logTime:String){
        findNavController().navigate(R.id.action_log_to_log_detail,bundleOf(
            KEY_LOG_DATE to logDate,
            KEY_LOG_TIME to logTime
        ))
    }
}