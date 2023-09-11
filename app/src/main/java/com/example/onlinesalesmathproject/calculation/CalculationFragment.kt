package com.example.onlinesalesmathproject.calculation

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.onlinesalesmathproject.R
import com.example.onlinesalesmathproject.databinding.FragmentCalculationBinding
import com.example.onlinesalesmathproject.history.CalculationHistroyViewmodel
import com.example.onlinesalesmathproject.history.database.CalculationData
import com.example.onlinesalesmathproject.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CalculationFragment : Fragment() {

    private val TAG = "CalculationFragment"

    private var binding: FragmentCalculationBinding? = null
    private val calculationViewmodel by viewModels<CalculationViewmodel>()
    private val histroyViewmodel by viewModels<CalculationHistroyViewmodel>()
    private val calculationRecyclerViewAdapter by lazy {
        CalculationRecyclerViewAdapter()
    }

    private val inputList: MutableList<String> = ArrayList()
    private val resultList: MutableList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalculationBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        binding?.historyButton?.setOnClickListener {
            findNavController().navigate(R.id.action_calculationFragment_to_historyFragment)
        }

        binding?.submitButton?.setOnClickListener {

            getExpression()
            for (list in inputList) {
                calculationViewmodel.getResultData(list)
            }
            inputList.clear()
        }

        lifecycleScope.launch {
            calculationViewmodel.getResult.collect() {
                when (it) {
                    is Resource.Success -> {
                        Log.d(TAG, it.data?.pods?.get(1)?.subpods?.get(0)?.plaintext.toString())

                        val output =
                            "${it.data?.inputstring} = ${it.data?.pods?.get(1)?.subpods?.get(0)?.plaintext}"
                        val resultData = CalculationData(calculationString = output)
                        histroyViewmodel.insert(resultData)
                        resultList.add(output)
                        calculationRecyclerViewAdapter.differ.submitList(resultList)
                        calculationRecyclerViewAdapter.notifyDataSetChanged()
                    }
                    is Resource.Error -> {
                        Log.d(TAG, it.message.toString())
                    }
                    is Resource.Loading -> {

                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding?.calculationRecyclerView?.apply {
            adapter = calculationRecyclerViewAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun getExpression() {
        val expressions = binding?.inputEditText?.text.toString()
        val inputLines = expressions.split("\n")
        for (line in inputLines) {
            if (isValidMathExpression(line)) {
                inputList.add(line)
            }
        }
        binding?.inputEditText?.text?.clear()
    }

    fun isValidMathExpression(input: String): Boolean {
        val pattern = Regex("^[\\d+\\-*/^%]+$")
        return pattern.matches(input)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}