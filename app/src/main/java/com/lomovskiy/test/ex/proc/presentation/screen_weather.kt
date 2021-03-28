package com.lomovskiy.test.ex.proc.presentation

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.common.api.ResolvableApiException
import com.lomovskiy.lib.ui.Command
import com.lomovskiy.test.ex.proc.R
import com.lomovskiy.test.ex.proc.databinding.ScreenWeatherBinding
import com.lomovskiy.test.ex.proc.domain.LocationException
import com.lomovskiy.test.ex.proc.domain.WeatherInteractor
import com.lomovskiy.test.ex.proc.domain.WeatherSnapshotEntity
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ScreenWeatherViewModel(
    private val interactor: WeatherInteractor
) : ViewModel() {

    private val states = MutableLiveData(ScreenWeather.State.empty())
    private val commands = MutableLiveData<Command<ScreenWeather.Command>>()

    init {
        fetchWeather()
    }

    fun getStates(): LiveData<ScreenWeather.State> {
        return states
    }

    fun getCommands(): LiveData<Command<ScreenWeather.Command>> {
        return commands
    }

    fun handleAction(action: Action) {
        when (action) {
            Action.OnRefresh -> {
                states.value = states.value?.copy(refreshing = true)
                fetchWeather()
            }
        }
    }

    private fun fetchWeather() {
        viewModelScope.launch {
            try {
                val latestWeatherSnapshot: WeatherSnapshotEntity = interactor.getLatestWeatherSnapshot()
                states.value = states.value?.copy(
                    weatherSnapshotEntity = latestWeatherSnapshot,
                    refreshing = false
                )
            } catch (e: LocationException) {
                if (e is LocationException.DisabledLocationException) {
                    commands.value = Command(ScreenWeather.Command.ResolveLocationException(e.cs))
                }
            }
        }
    }

    sealed class Action {
        object OnRefresh : Action()
    }

}

class ScreenWeather(
    private val interactor: WeatherInteractor
) : Fragment(R.layout.screen_weather), ViewModelProvider.Factory, SwipeRefreshLayout.OnRefreshListener {

    private var _binding: ScreenWeatherBinding? = null
    private val binding get() = _binding!!

    private val screenVM: ScreenWeatherViewModel by viewModels(
        factoryProducer = { this }
    )

    private val permissionLauncher: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted: Boolean ->
        if (granted) {
            screenVM.getStates().observe(viewLifecycleOwner, Observer(::renderState))
            screenVM.getCommands().observe(viewLifecycleOwner, Observer {
                it.get()?.let(::handleCommand)
            })
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ScreenWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setOnRefreshListener(this)
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ScreenWeatherViewModel(interactor) as T
    }

    override fun onRefresh() {
        screenVM.handleAction(ScreenWeatherViewModel.Action.OnRefresh)
    }

    private fun handleCommand(command: Command) {
        when (command) {
            is Command.ResolveLocationException -> {
                command.e.startResolutionForResult(requireActivity(), 1)
            }
        }
    }

    private fun renderState(state: State) {
        binding.root.isRefreshing = state.refreshing
        state.weatherSnapshotEntity?.let { weatherSnapshotEntity: WeatherSnapshotEntity ->
            binding.labelTemperature.text = weatherSnapshotEntity.temp.toString()
            binding.labelSpeed.text = weatherSnapshotEntity.speed.toString()
            Picasso.get()
                    .load(weatherSnapshotEntity.imagePath)
                    .into(binding.imageWeather)
        }
    }

    sealed class Command {
        class ResolveLocationException(val e: ResolvableApiException) : Command()
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
