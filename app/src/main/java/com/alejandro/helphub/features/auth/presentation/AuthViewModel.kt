package com.alejandro.helphub.features.auth.presentation

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alejandro.helphub.features.auth.data.CountryProvider
import com.alejandro.helphub.features.auth.domain.CountriesModel
import com.alejandro.helphub.features.auth.domain.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val _charLimit = MutableStateFlow<String?>("")
    val charLimit: StateFlow<String?> = _charLimit.asStateFlow()

    private val _userData = MutableStateFlow(UserData())
    val userData: StateFlow<UserData> = _userData.asStateFlow()

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable: LiveData<Boolean> = _isLoginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _countries = MutableLiveData<List<CountriesModel>>()
    val countries: LiveData<List<CountriesModel>> = _countries

    private val _selectedCountry =
        MutableLiveData(CountryProvider.countries.first())
    val selectedCountry: LiveData<CountriesModel> = _selectedCountry

    private val _isExpanded = MutableLiveData<Boolean>(false)
    val isExpanded: LiveData<Boolean> = _isExpanded

    private val _isCheckBoxChecked = MutableLiveData<Boolean>(false)
    val isCheckBoxChecked: LiveData<Boolean> = _isCheckBoxChecked

    private val _isSwitchChecked = MutableLiveData<Boolean>(false)
    val isSwitchChecked: LiveData<Boolean> = _isSwitchChecked

    private val _expanded = MutableStateFlow(false)
    val expanded: StateFlow<Boolean> = _expanded.asStateFlow()

    private val _selectedDays = MutableStateFlow<List<String>>(emptyList())
    val selectedDays: StateFlow<List<String>> = _selectedDays

    val daysOfWeek = listOf(
        "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
    )


    val categories = listOf(
        "Idiomas",
        "Fitness",
        "Diseño",
        "Tutorias",
        "Animales",
        "Bricolaje",
        "Consultoría",
        "Informática",
        "Cuidado Personal",
        "Ayuda"
    )

    /*
    init {

        _countries.value =
            CountryProvider.countries // Asignamos la lista del provider
        _selectedCountry.value =
            CountryProvider.countries.first() // El primer país como predeterminado
    }
     */

    fun updateLearningMode(mode:String){
        Log.d("AuthViewModel", "Updating learning mode to: $mode")
        val updateUserData= userData.value.copy(mode = mode)
        _userData.value=updateUserData
    }

    fun updateSkillDescription(skillDescription:String){
        Log.d("AuthViewModel", "Updating skillDescription to: $skillDescription")
        val updateUserData= userData.value.copy(skillDescription = skillDescription)
        _userData.value=updateUserData
    }

    fun updateUserDays(days: List<String>) {
        _userData.value = _userData.value.copy(selectedDays = days)
    }
    fun updateUserPhotoUri(photoUri: Uri){
        val updateUserData=userData.value.copy(userPhotoUri=photoUri)
        _userData.value=updateUserData
    }
    fun updateUserPhotoBitmap(bitmap: Bitmap) {
        _userData.value = _userData.value.copy(photoBitmap = bitmap)
    }

    fun updatePhoneNumber(phoneNumber:String){
        Log.d("AuthViewModel", "Updating phoneNumber to: $phoneNumber")
        _userData.update{it.copy(phoneNumber = phoneNumber)}
    }
    fun updateSelectedCountry(country: CountriesModel) {
        _userData.update{it.copy(countryCode=country.code)}
    }
    fun updateUserEmail(email:String){
        Log.d("AuthViewModel", "Updating email to: $email")
        val updateUserData= userData.value.copy(email = email)
        _userData.value=updateUserData
    }
    fun updateUserPassword(password:String){
        Log.d("AuthViewModel", "Updating password to: $password")
        val updateUserData= userData.value.copy(password = password)
        _userData.value=updateUserData
    }

    fun updateUserName(name: String){
        Log.d("AuthViewModel", "Updating name to: $name")
        val updateUserData= userData.value.copy(name = name)
        _userData.value=updateUserData
    }
    fun updateUserSurname(surname: String){
        Log.d("AuthViewModel", "Updating surname to: $surname")
        val updateUserData= userData.value.copy(surname = surname)
        _userData.value=updateUserData
    }
    fun updateUserDescription(userDescription: String) {
        Log.d("AuthViewModel", "Updating postTitle to: $userDescription")
        val updateUserData= userData.value.copy(userDescription = userDescription)
        _userData.value=updateUserData
    }

    fun updatePostTitle(postTitle: String) {
        Log.d("AuthViewModel", "Updating postTitle to: $postTitle")
       val updateUserData= userData.value.copy(postTitle = postTitle)
        _userData.value=updateUserData
    }

    fun updatePostalCode(postalCode: String) {
        Log.d("AuthViewModel", "Updating postalCode to: $postalCode")
        val updateUserData=userData.value.copy(postalCode = postalCode )
        _userData.value= updateUserData
    }

    fun updateSelectedLevel(level: String) {
        Log.d("AuthViewModel", "Updating selectedLevel to: $level")
        val updateUserData=_userData.value.copy(selectedLevel = level )
        _userData.value=updateUserData
    }
    fun updateAvailability(availability: String) {
        Log.d("AuthViewModel", "Updating selectedLevel to: $availability")
        val updateUserData=_userData.value.copy(availability = availability )
        _userData.value=updateUserData
    }

    fun updateSelectedCategory(category: String) {
        Log.d("AuthViewModel", "Updating selectedCategory to: $category")
        val updateUserData=_userData.value.copy(selectedCategory = category )
        _userData.value= updateUserData
    }

    fun onDayChecked(day: String, isChecked: Boolean) {
        val currentDays = _selectedDays.value.toMutableList()
        if (isChecked) {
            currentDays.add(day)
        } else {
            currentDays.remove(day)
        }
        _selectedDays.value = currentDays
        updateUserDays(currentDays) // Actualiza la variable userData si es necesario
    }

    fun toggleDropdown() {
        _expanded.value = !_expanded.value
    }

    fun onSwitchCheckedChanged(isChecked: Boolean) {
        _isSwitchChecked.value = isChecked
    }

    fun onCheckBoxCheckedChanged(isChecked: Boolean) {
        _isCheckBoxChecked.value = isChecked
    }

    fun toggleExpanded() {
        _isExpanded.value = !(_isExpanded.value ?: false)
    }

   fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _isLoginEnable.value = enableLogin(email, password)
    }

    private fun enableLogin(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6
}