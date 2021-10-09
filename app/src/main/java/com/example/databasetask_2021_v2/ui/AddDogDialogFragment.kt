package com.example.databasetask_2021_v2.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.databasetask_2021_v2.BusinessLogic.AddDialogFragmentLogic
import com.example.databasetask_2021_v2.databinding.DialogFragmentAddDogBinding
import com.example.databasetask_2021_v2.repository.room.Dog

class AddDogDialogFragment(
    private val isAddDog: Boolean,
    private val dog: Dog? = null
) : DialogFragment() {

    private val businessLogic = AddDialogFragmentLogic(dog, isAddDog)
    private var _binding: DialogFragmentAddDogBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var listener: DialogListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = activity as DialogListener
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFragmentAddDogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireContext())
            .setView(binding.root)

        binding.title.text = getString(businessLogic.getTitleId())

        setDogFieldsInEditText()

        binding.dfSaveBtn.setOnClickListener {
            val errorMessage = businessLogic.getErrorMessage(
                name = binding.dfNameEt.text.toString(),
                age = binding.dfAgeEt.text.toString().toIntOrNull(),
                breed = binding.dfBreedEt.text.toString()
            )

            binding.errorMessage.text = errorMessage

            if (errorMessage == "") {
                if (isAddDog) {
                    saveDog()
                    dismiss()
                } else {
                    updateDog()
                    dismiss()
                }
            }
        }

        binding.dfCancelBtn.setOnClickListener {
            dismiss()
        }

        return builder.create()
    }

    private fun saveDog() {
        listener?.save(
            binding.dfNameEt.text.toString(),
            binding.dfAgeEt.text.toString().toInt(),
            binding.dfBreedEt.text.toString()
        )
    }

    private fun updateDog() {
        if (dog != null)
            listener?.update(
                Dog(
                    id = dog.id,
                    binding.dfNameEt.text.toString(),
                    binding.dfAgeEt.text.toString().toInt(),
                    binding.dfBreedEt.text.toString()
                )
            )
    }

    private fun setDogFieldsInEditText() {
        if (dog != null) {
            binding.dfNameEt.setText(dog.name)
            binding.dfAgeEt.setText(dog.age.toString())
            binding.dfBreedEt.setText(dog.breed)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("DEBUG", "ON DESTROY")
    }

    companion object {
        const val TAG = "DialogFragment"
    }
}
