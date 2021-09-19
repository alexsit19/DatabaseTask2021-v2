package com.example.databasetask_2021_v2.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.databasetask_2021_v2.databinding.FilterBinding

class FilterDialogFragment: DialogFragment() {
    private var _binding: FilterBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var listener: FilterListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = activity as FilterListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FilterBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)

        binding.filterRadioGroup.setOnCheckedChangeListener { _, checkedId ->

            when (checkedId) {
                binding.sortById.id -> listener?.getDataSortBy("id")
                binding.sortByName.id -> listener?.getDataSortBy("name")
                binding.sortByAge.id -> listener?.getDataSortBy("age")
                binding.sortByBreed.id -> listener?.getDataSortBy("breed")
            }
            dismiss()

        }

        return builder.create()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}