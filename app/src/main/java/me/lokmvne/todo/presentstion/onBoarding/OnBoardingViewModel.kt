package me.lokmvne.todo.presentstion.onBoarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.lokmvne.common.data.data_store.ToDoPreferences
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val dataStore: ToDoPreferences
) : ViewModel() {

    fun setFirstTime(){
        viewModelScope.launch {
            dataStore.setFirstTime()
        }
    }
}