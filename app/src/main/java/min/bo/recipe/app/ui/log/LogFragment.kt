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
import min.bo.recipe.app.common.*
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
            openLogDetail(it.date,it.time,it.cartridge1,it.cartridge2,it.cartridge3,it.cartridge_gram1,it.cartridge_gram2,it.cartridge_gram3,
            it.carbo_gram,it.protein_gram,it.fat_gram,it.my_carbo_gram,it.my_protein_gram,it.my_fat_gram,it.carbo_percent,it.protein_percent,it.fat_percent,
            it.print_kcal,it.total_kcal,it.kcal_percent)

        })
    }



    private fun openLogDetail(logDate:String,logTime:String,logCartridge1:String,logCartridge2:String,logCartridge3:String,
    logCartridgeGram1:String,logCartridgeGram2: String,logCartridgeGram3: String,
    logPrintCarbo:String,logPrintProtein:String,logPrintFat:String,
    logMyCarbo:String,logMyProtein:String,logMyFat:String,
    logCarboPercent:String,logProteinPercent:String,logFatPercent:String,
    logPrintKcal:String,logTotalKcal:String,logKcalPercent:String){
        findNavController().navigate(R.id.action_log_to_log_detail,bundleOf(
            KEY_LOG_DATE to logDate,
            KEY_LOG_TIME to logTime,
            KEY_CARTRIDGE_NAME1 to logCartridge1,
            KEY_CARTRIDGE_NAME2 to logCartridge2,
            KEY_CARTRIDGE_NAME3 to logCartridge3,
            KEY_CARTRIDGE_GRAM1 to logCartridgeGram1,
            KEY_CARTRIDGE_GRAM2 to logCartridgeGram2,
            KEY_CARTRIDGE_GRAM3 to logCartridgeGram3,
            KEY_CARBO to logPrintCarbo,
            KEY_PROTEIN to logPrintProtein,
            KEY_FAT to logPrintFat,

            KEY_MY_CARBO to logMyCarbo,
            KEY_MY_PROTEIN to logMyProtein,
            KEY_MY_FAT to logMyFat,


            KEY_CARBO_PERCENT to logCarboPercent,
            KEY_PROTEIN_PERCENT to logProteinPercent,
            KEY_FAT_PERCENT to logFatPercent,


            KEY_PRINT_KCAL to logPrintKcal,
            KEY_TOTAL_KCAL to logTotalKcal,
            KEY_KCAL_PERCENT to logKcalPercent

            ))
    }
}