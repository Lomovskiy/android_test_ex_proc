package com.lomovskiy.test.ex.proc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.lomovskiy.test.ex.proc.databinding.ScreenWeatherBinding
import com.lomovskiy.test.ex.proc.domain.WeatherSnapshotEntity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ScreenWeatherViewModel : ViewModel() {

    private val states = MutableLiveData(ScreenWeather.State.empty())

    fun getStates(): LiveData<ScreenWeather.State> {
        return states
    }

    fun handleAction(action: Action) {
        when (action) {
            Action.OnRefresh -> {
                states.value = states.value?.copy(refreshing = true)
                viewModelScope.launch {
                    delay(3000)
                    states.value = states.value?.copy(refreshing = false)
                }
            }
        }
    }

    sealed class Action {
        object OnRefresh : Action()
    }

}

class ScreenWeather : Fragment(R.layout.screen_weather), SwipeRefreshLayout.OnRefreshListener {

    private var _binding: ScreenWeatherBinding? = null
    private val binding get() = _binding!!

    private val screenVM: ScreenWeatherViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ScreenWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnRefreshListener(this)
        screenVM.getStates().observe(viewLifecycleOwner, Observer(::renderState))
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onRefresh() {
        screenVM.handleAction(ScreenWeatherViewModel.Action.OnRefresh)
    }

    private fun renderState(state: State) {
        binding.root.isRefreshing = state.refreshing
        state.weatherSnapshotEntity?.let { weatherSnapshotEntity: WeatherSnapshotEntity ->
            binding.labelTemperature.text = weatherSnapshotEntity.temperature.toString()
            binding.labelSpeed.text = weatherSnapshotEntity.speed.toString()
            Picasso.get()
                    .load(weatherSnapshotEntity.imagePath)
                    .into(binding.imageWeather)
        }
    }

    data class State(
            val refreshing: Boolean,
            val weatherSnapshotEntity: WeatherSnapshotEntity?
    ) {

        companion object {

            fun empty(): State {
                return State(
                        refreshing = false,
                        weatherSnapshotEntity = null
                )
            }

        }

    }

}
