package com.example.databasetask_2021_v2.business_logic

import com.example.databasetask_2021_v2.R
import com.example.databasetask_2021_v2.repository.room.Dog

class AddDialogFragmentLogic(private val dog: Dog?, private val isAddDog: Boolean) {

    fun getTitleId(): Int {
        return if (isAddDog) {
            R.string.add_dog_title
        } else {
            R.string.update_dog_title
        }
    }

    fun getErrorMessage(name: String, age: Int?, breed: String): String {
        var errorString = ""
        val errorList = mutableListOf<String>()

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
            errorString = errorList.joinToString(separator = ", ")
        }
        return errorString
    }
}
