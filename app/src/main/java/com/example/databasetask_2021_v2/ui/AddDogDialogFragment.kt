package com.example.databasetask_2021_v2.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.databasetask_2021_v2.R
import com.example.databasetask_2021_v2.databinding.DialogFragmentAddDogBinding
import com.example.databasetask_2021_v2.repository.room.Dog

class AddDogDialogFragment(
    private val isAddDog: Boolean,
    private val dog: Dog? = null
    ): DialogFragment() {

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

        if(isAddDog) {
            binding.title.text = getString(R.string.add_dog_title)

        } else {
            binding.title.text = getString(R.string.update_dog_title)
            if(dog != null) {
                binding.dfNameEt.setText(dog.name)
                binding.dfAgeEt.setText(dog.age.toString())
                binding.dfBreedEt.setText(dog.breed)
            }
        }

        binding.dfSaveBtn.setOnClickListener {
            if (checkSetErrorMessages()) {
                if (isAddDog) {
                    listener?.save(
                        binding.dfNameEt.text.toString(),
                        binding.dfAgeEt.text.toString().toInt(),
                        binding.dfBreedEt.text.toString()
                    )
                    dismiss()
                } else {
                    if(dog != null)
                    listener?.update(
                        Dog(
                            id = dog.id,
                            binding.dfNameEt.text.toString(),
                            binding.dfAgeEt.text.toString().toInt(),
                            binding.dfBreedEt.text.toString()
                        )
                    )
                    dismiss()
                }
            }
        }

        binding.dfCancelBtn.setOnClickListener {
            dismiss()

        }

        return builder.create()
    }

    private fun checkSetErrorMessages(): Boolean {
        var result = false
        val errorList = mutableListOf<String>()
        val name = binding.dfNameEt.text.toString()
        val age = binding.dfAgeEt.text.toString().toIntOrNull()
        val breed = binding.dfBreedEt.text.toString()

        if (name == "") {
            errorList.add("name is empty")
        }
        if (age != null) {
            if (age > 32) {
                errorList.add("age more than 32")
            }

        } else {
            errorList.add("age is empty")
        }
        if (breed == "") {
            errorList.add("breed is empty")
        }
        if (errorList.isNotEmpty()) {

            binding.errorMessage.text = errorList.joinToString(separator = ", ")

        } else {
            result = true
        }
        return result
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        Log.d("DEBUG", "ON DESTROY")
    }

    companion object {
        const val TAG = "DialogFragment"
    }
}