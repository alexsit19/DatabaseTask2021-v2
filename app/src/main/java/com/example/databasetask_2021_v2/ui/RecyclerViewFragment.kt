package com.example.databasetask_2021_v2.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.databasetask_2021_v2.DogApplication
import com.example.databasetask_2021_v2.databinding.RecyclerviewFragmentBinding
import com.example.databasetask_2021_v2.repository.room.Dog
import com.example.databasetask_2021_v2.ui.adapter.DogListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RecyclerViewFragment : Fragment(), DogItemListener {

    private var adapter: DogListAdapter? = null
    private var _binding: RecyclerviewFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val dogsViewModel: DogViewModel by activityViewModels {
        DogViewModelFactory((activity?.application as DogApplication).getRepository())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RecyclerviewFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preference = PreferenceManager.getDefaultSharedPreferences(requireActivity())
        val settingsString = preference.getString("list", "")
        Log.d("DEBUG", "FROM FRAGMENT $settingsString")

        adapter = DogListAdapter(this)

        binding.recycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.recycler.adapter = adapter

        dogsViewModel.dogs.onEach(::renderDogs).launchIn(lifecycleScope)
    }

    private fun renderDogs(dogs: List<Dog>) {
        adapter?.submitList(dogs)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun delete(dog: Dog) {
        dogsViewModel.delete(dog)
    }

    override fun update(dog: Dog) {
        val dialog = AddDogDialogFragment(isAddDog = false, dog)
        dialog.show(parentFragmentManager, "DialogFragment")
        Log.d("DEBUG", "UPDATE FROM FRAGMENT")
    }
}
