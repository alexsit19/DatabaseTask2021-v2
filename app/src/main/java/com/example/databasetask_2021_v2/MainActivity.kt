package com.example.databasetask_2021_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.preference.PreferenceManager
import com.example.databasetask_2021_v2.databinding.ActivityMainBinding
import com.example.databasetask_2021_v2.repository.room.Dog
import com.example.databasetask_2021_v2.ui.*

class MainActivity : AppCompatActivity(), DialogListener, FilterListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val viewModel: DogViewModel by viewModels {
        DogViewModelFactory((this.application as DogApplication).getRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener {
            val dialog = AddDogDialogFragment(isAddDog = true)
            dialog.show(supportFragmentManager, AddDogDialogFragment.TAG)
        }

        startRecyclerViewFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)

    }

    override fun onSupportNavigateUp(): Boolean {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        moveSettingsFragment()
        return super.onSupportNavigateUp()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                val filterDialog = FilterDialogFragment()
                filterDialog.show(supportFragmentManager, FilterDialogFragment.TAG)
                Log.d("DEBUG", "filter click")
                true
            }
            R.id.settings -> {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                startSettingsFragment()
                Log.d("DEBUG", "settings click")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startSettingsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.place_for_fragments, SettingsFragment(), "settings")
            .commit()
    }

    private fun moveSettingsFragment() {
        supportFragmentManager.findFragmentByTag("settings")?.let {
            supportFragmentManager.beginTransaction()
                .remove(it)
                .commit()
        }
        startRecyclerViewFragment()
    }

    private fun startRecyclerViewFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.place_for_fragments, RecyclerViewFragment(), "")
            .commit()
    }

    override fun save(name: String, age: Int, breed: String) {
        viewModel.insert(name, age, breed)
    }

    override fun update(dog: Dog) {
        viewModel.update(dog)
    }

    override fun getDataSortBy(sortBy: String) {
        viewModel.getDataSortedBy(sortBy)
        Log.d("DEBUG", "FilterString: $sortBy")
    }
}
