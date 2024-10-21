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

    private val _isSignUpButtonEnabled=MutableStateFlow(false)
    val isSignUpButtonEnabled:StateFlow<Boolean> = _isSignUpButtonEnabled.asStateFlow()

    private val _selectedCategoriesOfInterest= MutableStateFlow<List<String>>(
        emptyList()
    )
    val selectedCategoriesOfInterest:StateFlow<List<String>> = _selectedCategoriesOfInterest.asStateFlow()

    private val _charLimit = MutableStateFlow<String?>("")
    val charLimit: StateFlow<String?> = _charLimit.asStateFlow()

    private val _userData = MutableStateFlow(UserData())
    val userData: StateFlow<UserData> = _userData.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()


    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable: LiveData<Boolean> = _isLoginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _countries = MutableStateFlow<List<CountriesModel>>(emptyList())
    val countries: StateFlow<List<CountriesModel>> = _countries.asStateFlow()

    private val _selectedCountry = MutableStateFlow(CountryProvider.countries.first())
    val selectedCountry: StateFlow<CountriesModel> = _selectedCountry.asStateFlow()

    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: StateFlow<Boolean> = _isExpanded.asStateFlow()

    private val _isCheckBoxChecked = MutableStateFlow(false)
    val isCheckBoxChecked: StateFlow<Boolean> = _isCheckBoxChecked.asStateFlow()

    private val _isSwitchChecked = MutableStateFlow(false)
    val isSwitchChecked: StateFlow<Boolean> = _isSwitchChecked.asStateFlow()

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
    val popularCategories=listOf(
        "Idiomas",
        "Fitness",
        "Diseño",
        "Tutorias",
        "Animales",
        "Bricolaje",
        "Consultoría",
        "Informática",
        "Cuidado Personal"
    )

    fun updateCategoriesOfInterest(category: String,isSelected:Boolean){
        val currentCategories = _userData.value.categoriesOfInterest.toMutableList()
        if (isSelected) {
            currentCategories.add(category)
        } else {
            currentCategories.remove(category)
        }
        _userData.value = _userData.value.copy(categoriesOfInterest = currentCategories)
    }

    fun updateSelectedCategoriesOfInterest(category: String){
        val currentCategories=userData.value.categoriesOfInterest.toMutableList()?:mutableListOf()
        currentCategories.add(category)
        _userData.value=_userData.value.copy(categoriesOfInterest = currentCategories)
    }
    fun removeCategoryOfInterest(category: String) {
        val currentCategories = _userData.value.categoriesOfInterest?.toMutableList() ?: mutableListOf()
        currentCategories.remove(category)
        _userData.value = _userData.value.copy(categoriesOfInterest = currentCategories)
    }


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

    fun updateSignUpButtonState() {
        _isSignUpButtonEnabled.value = userData.value.name.isNotBlank() &&
                userData.value.surname1.isNotBlank() &&
                userData.value.surname2.isNotBlank() &&
                userData.value.phoneNumber.isNotBlank() &&
                userData.value.email.isNotBlank() &&
                userData.value.password.isNotBlank() &&
                _isCheckBoxChecked.value == true
    }

    fun updatePhoneNumber(phoneNumber:String){
        Log.d("AuthViewModel", "Updating phoneNumber to: $phoneNumber")
        _userData.update{it.copy(phoneNumber = phoneNumber)}
        updateSignUpButtonState()
    }
    fun updateSelectedCountry(country: CountriesModel) {
        _userData.update{it.copy(countryCode=country.code)}
    }
    fun updateUserEmail(email:String){
        Log.d("AuthViewModel", "Updating email to: $email")
        val updateUserData= userData.value.copy(email = email)
        _userData.value=updateUserData
        updateSignUpButtonState()
    }
    fun updateUserPassword(password:String){
        Log.d("AuthViewModel", "Updating password to: $password")
        val updateUserData= userData.value.copy(password = password)
        _userData.value=updateUserData
        updateSignUpButtonState()
    }

    fun updateUserName(name: String){
        Log.d("AuthViewModel", "Updating name to: $name")
        val updateUserData= userData.value.copy(name = name)
        _userData.value=updateUserData
        updateSignUpButtonState()
    }
    fun updateUserSurname2(surname2: String){
        Log.d("AuthViewModel", "Updating surname2 to: $surname2")
        val updateUserData= userData.value.copy(surname2 = surname2)
        _userData.value=updateUserData
        updateSignUpButtonState()
    }
    fun updateUserSurname1(surname1: String){
        Log.d("AuthViewModel", "Updating surname1 to: $surname1")
        val updateUserData= userData.value.copy(surname1 = surname1)
        _userData.value=updateUserData
        updateSignUpButtonState()
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
        updateSignUpButtonState()
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