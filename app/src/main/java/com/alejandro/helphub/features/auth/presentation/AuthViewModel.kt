package com.alejandro.helphub.features.auth.presentation

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.helphub.features.auth.domain.CountriesModel
import com.alejandro.helphub.features.auth.domain.UserData
import com.alejandro.helphub.features.auth.domain.usecases.GetUserUseCase
import com.alejandro.helphub.features.auth.domain.usecases.SendTwoFaRegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val sendTwoFaRegisterUseCase: SendTwoFaRegisterUseCase, private val getUserUseCase: GetUserUseCase) :
    ViewModel() {


    private val _userData = MutableStateFlow(UserData())
    val userData: StateFlow<UserData> = _userData.asStateFlow()



    //<!--------------------Credentials Screen ---------------->
    private val _isCheckBoxChecked = MutableStateFlow(false)
    val isCheckBoxChecked: StateFlow<Boolean> = _isCheckBoxChecked.asStateFlow()

    private val _isExpanded = MutableStateFlow(false)
    val isExpanded: StateFlow<Boolean> = _isExpanded.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isSwitchChecked = MutableStateFlow(false)
    val isSwitchChecked: StateFlow<Boolean> = _isSwitchChecked.asStateFlow()

    private val _isSignUpButtonEnabled = MutableStateFlow(false)
    val isSignUpButtonEnabled: StateFlow<Boolean> =
        _isSignUpButtonEnabled.asStateFlow()

    private val _twoFaCode = MutableStateFlow<String>("")
    val twoFaCode: StateFlow<String> get() = _twoFaCode

    val isPasswordValid: StateFlow<Boolean> = _userData.map { isPasswordValid(it.password) }
        .stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun toggleExpanded() {
        _isExpanded.value = !(_isExpanded.value ?: false)
    }

    fun onSwitchCheckedChanged(isChecked: Boolean) {
        _isSwitchChecked.value = isChecked
        _userData.value=_userData.value.copy(optionCall = isChecked, showPhone = isChecked)
    }


    fun updateSignUpButtonState() {
        _isSignUpButtonEnabled.value = userData.value.nameUser.isNotBlank() &&
                userData.value.surnameUser.isNotBlank() &&
                userData.value.phone.isNotBlank() &&
                userData.value.email.isNotBlank() &&
                userData.value.password.isNotBlank() &&
                _isCheckBoxChecked.value == true
    }

    fun updatePhoneNumber(phoneNumber: String) {
        Log.d("AuthViewModel", "Updating phoneNumber to: $phoneNumber")
        _userData.update { it.copy(phone = phoneNumber) }
        updateSignUpButtonState()
    }

    fun updateSelectedCountry(country: CountriesModel) {
        _userData.update { it.copy(countryCode = country.code) }
    }

    fun updateUserEmail(email: String) {
        Log.d("AuthViewModel", "Updating email to: $email")
        val updateUserData = userData.value.copy(email = email)
        _userData.value = updateUserData
        updateSignUpButtonState()
    }

    fun updateUserPassword(password: String) {
        Log.d("AuthViewModel", "Updating password to: $password")
        val updateUserData = userData.value.copy(password = password)
        _userData.value = updateUserData
        updateSignUpButtonState()
    }

    fun updateUserName(name: String) {
        Log.d("AuthViewModel", "Updating name to: $name")
        val updateUserData = userData.value.copy(nameUser = name)
        _userData.value = updateUserData
        updateSignUpButtonState()
    }

    fun updateUserSurname2(surname2: String) {
        Log.d("AuthViewModel", "Updating surname2 to: $surname2")
        val updateUserData = userData.value.copy(surname2 = surname2)
        _userData.value = updateUserData
        updateSignUpButtonState()
    }

    fun updateUserSurname1(surname1: String) {
        Log.d("AuthViewModel", "Updating surname1 to: $surname1")
        val updateUserData = userData.value.copy(surnameUser = surname1)
        _userData.value = updateUserData
        updateSignUpButtonState()
    }

    fun onCheckBoxCheckedChanged(isChecked: Boolean) {
        _isCheckBoxChecked.value = isChecked
        updateSignUpButtonState()
    }

    fun isPasswordValid(password: String):Boolean{
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }
        val isValidLength = password.length >= 6

        return hasUpperCase && hasDigit && hasSpecialChar && isValidLength
    }
    fun onPasswordChanged(newPassword: String) {
        Log.d("AuthViewModel", "Updating password to: $newPassword")
        _userData.value = _userData.value.copy(password = newPassword)
        updateSignUpButtonState()
    }

    fun generateTwoFaCode():String {
        // Genera un número aleatorio de 6 cifras
        val code= (100000..999999).random().toString()
        Log.i("2FA Code", "Código generado: $code") // opcional, solo para depuración
        return code
    }

    fun generateAndSendTwoFaCode(){
        viewModelScope.launch {
            _isLoading.value=true
            val generatedTwoFa= generateTwoFaCode()
            val updatedUserData=userData.value.copy(twoFa = generatedTwoFa)
            val result=sendTwoFaRegisterUseCase(updatedUserData)
            if(result.isNotEmpty()){Log.i("this", "funciona")}
        }
        _isLoading.value=false
    }

    //<!--------------------SignUpStep1 Screen ---------------->

    fun updateUserDescription(userDescription: String) {
        Log.d("AuthViewModel", "Updating postTitle to: $userDescription")
        val updateUserData =
            userData.value.copy(userDescription = userDescription)
        _userData.value = updateUserData
        navigateToStep2()
    }

    fun updatePostalCode(postalCode: String) {
        Log.d("AuthViewModel", "Updating postalCode to: $postalCode")
        val updateUserData = userData.value.copy(postalCode = postalCode)
        _userData.value = updateUserData
        navigateToStep2()
    }

    private val _isNavigationToStep2Enabled = MutableStateFlow(false)
    val isNavigationToStep2Enabled: StateFlow<Boolean> =
        _isNavigationToStep2Enabled.asStateFlow()

    fun navigateToStep2() {
        _isNavigationToStep2Enabled.value =
            userData.value.userDescription.isNotBlank() &&
                    userData.value.postalCode.isNotBlank()
    }

    //<!--------------------SignUpStep2 Screen ---------------->

    private val _isNavigationToStep3Enabled = MutableStateFlow(false)
    val isNavigationToStep3Enabled: StateFlow<Boolean> =
        _isNavigationToStep3Enabled.asStateFlow()

    fun updateUserPhotoUri(photoUri: Uri) {
        val updateUserData = userData.value.copy(userPhotoUri = photoUri)
        _userData.value = updateUserData
        navigateToStep3()
    }

    fun navigateToStep3() {
        _isNavigationToStep3Enabled.value = userData.value.userPhotoUri != null
    }

    //<!--------------------SignUpStep3 Screen ---------------->

    private val _isNavigationToStep4PostEnabled = MutableStateFlow(false)
    val isNavigationToStep4PostEnabled: StateFlow<Boolean> =
        _isNavigationToStep4PostEnabled.asStateFlow()

    private val _expanded = MutableStateFlow(false)
    val expanded: StateFlow<Boolean> = _expanded.asStateFlow()

    private val _selectedDays = MutableStateFlow<List<String>>(emptyList())
    val selectedDays: StateFlow<List<String>> = _selectedDays

    val daysOfWeek = listOf(
        "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"
    )

    fun toggleDropdown() {
        _expanded.value = !_expanded.value
    }

    fun onDayChecked(day: String, isChecked: Boolean) {
        val currentDays = _selectedDays.value.toMutableList()
        if (isChecked) {
            currentDays.add(day)
        } else {
            currentDays.remove(day)
        }
        _selectedDays.value = currentDays
        updateUserDays(currentDays)
    }

    fun updateUserDays(days: List<String>) {
        _userData.value = _userData.value.copy(selectedDays = days)
        navigateToStep4Post()
    }

    fun updateAvailability(availability: String) {
        Log.d("AuthViewModel", "Updating selectedLevel to: $availability")
        val updateUserData = _userData.value.copy(availability = availability)
        _userData.value = updateUserData
        navigateToStep4Post()
    }

    fun navigateToStep4Post() {
        _isNavigationToStep4PostEnabled.value =
            userData.value.selectedDays.isNotEmpty() &&
                    userData.value.availability != null
    }

    //<!--------------------SignUpStep4Post Screen ---------------->

    private val _isNavigationToStep4SkillEnabled = MutableStateFlow(false)
    val isNavigationToStep4SkillEnabled: StateFlow<Boolean> =
        _isNavigationToStep4SkillEnabled.asStateFlow()

    fun updatePostTitle(postTitle: String) {
        Log.d("AuthViewModel", "Updating postTitle to: $postTitle")
        val updateUserData = userData.value.copy(postTitle = postTitle)
        _userData.value = updateUserData
        navigateToStep4Skill()
    }

    fun updateSelectedLevel(level: String) {
        Log.d("AuthViewModel", "Updating selectedLevel to: $level")
        val updateUserData = _userData.value.copy(selectedLevel = level)
        _userData.value = updateUserData
        navigateToStep4Skill()
    }

    fun updateLearningMode(mode: String) {
        Log.d("AuthViewModel", "Updating learning mode to: $mode")
        val updateUserData = userData.value.copy(mode = mode)
        _userData.value = updateUserData
        navigateToStep4Skill()
    }

    fun navigateToStep4Skill() {
        _isNavigationToStep4SkillEnabled.value =
            userData.value.postTitle.isNotBlank() &&
                    userData.value.selectedLevel != null &&
                    userData.value.mode != null
    }

    //<!--------------------SignUpStep4Skill Screen ---------------->
    private val _isNavigationToStep5Enabled = MutableStateFlow(false)
    val isNavigationToStep5Enabled: StateFlow<Boolean> =
        _isNavigationToStep5Enabled.asStateFlow()

    private val _selectedCategories =
        MutableStateFlow<List<String>>(emptyList())
    val selectedCategories: StateFlow<List<String>> =
        _selectedCategories.asStateFlow()

    fun updateSkillDescription(skillDescription: String) {
        Log.d(
            "AuthViewModel",
            "Updating skillDescription to: $skillDescription"
        )
        val updateUserData =
            userData.value.copy(skillDescription = skillDescription)
        _userData.value = updateUserData
        navigateToStep5()
    }

    fun onCategoryChecked(category: String, isChecked: Boolean) {
        val currentCategories = _selectedCategories.value.toMutableList()
        when {
            isChecked && currentCategories.size < 2 -> currentCategories.add(
                category
            )

            !isChecked -> currentCategories.remove(category)
        }
        _selectedCategories.value = currentCategories
        updateSelectedCategories(currentCategories)
    }

    fun updateSelectedCategories(categories: List<String>) {
        Log.d("AuthViewModel", "Updating selectedCategories to: $categories")
        _userData.value = _userData.value.copy(selectedCategories = categories)
        navigateToStep5()
    }

    fun navigateToStep5() {
        Log.d(
            "AuthViewModel",
            "Skill Description: ${userData.value.skillDescription}"
        )
        Log.d(
            "AuthViewModel",
            "Selected Categories: ${userData.value.selectedCategories}"
        )
        _isNavigationToStep5Enabled.value =
            userData.value.skillDescription.isNotBlank() &&
                    userData.value.selectedCategories.isNotEmpty()
    }

    //<!--------------------SignUpStep5 Screen ---------------->

    private val _isNavigationToHomeEnabled = MutableStateFlow(false)
    val isNavigationToHomeEnabled: StateFlow<Boolean> =
        _isNavigationToHomeEnabled.asStateFlow()

    private val _selectedCategoriesOfInterest =
        MutableStateFlow<List<String>>(emptyList())
    val selectedCategoriesOfInterest: StateFlow<List<String>> =
        _selectedCategoriesOfInterest.asStateFlow()

    fun onCategoriesOfInterestChecked(category: String, isSelected: Boolean) {
        val currentCategories =
            _selectedCategoriesOfInterest.value.toMutableList()
        when {
            isSelected && currentCategories.size < 3 -> currentCategories.add(
                category
            )

            !isSelected -> currentCategories.remove(category)
        }
        _selectedCategoriesOfInterest.value = currentCategories
        updateCategoriesOfInterest(currentCategories)
    }

    fun updateCategoriesOfInterest(categories: List<String>) {
        _userData.value =
            _userData.value.copy(categoriesOfInterest = categories)
        navigateToHome()
    }

    fun navigateToHome() {
        Log.d(
            "AuthViewModel",
            "Categories Of Interest: ${userData.value.categoriesOfInterest}"
        )
        _isNavigationToHomeEnabled.value =
            userData.value.categoriesOfInterest.isNotEmpty()
    }

    //Otros

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isLoginEnable = MutableLiveData<Boolean>()
    val isLoginEnable: LiveData<Boolean> = _isLoginEnable



    fun updateUserPhotoBitmap(bitmap: Bitmap) {
        _userData.value = _userData.value.copy(photoBitmap = bitmap)
    }

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _isLoginEnable.value = enableLogin(email, password)
    }

    private fun enableLogin(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6


    private val _userState = MutableStateFlow<UserState>(UserState.Initial)
    val userState = _userState.asStateFlow()

    fun testDatabaseQuery(email:String){
        viewModelScope.launch {
            try {
                val userData = getUserUseCase(email)
                if (userData != null) {
                    _userState.value = UserState.Success(userData)
                    Log.i("DB", "Datos de usuario: $userData")
                } else {
                    _userState.value = UserState.Error("No se encontraron datos")
                    Log.e("DB", "No se encontraron datos para el usuario con ID: $email")
                }
            } catch (e: Exception) {
                _userState.value = UserState.Error(e.message ?: "Error desconocido")
                Log.e("DB", "Error al acceder a la base de datos: ${e.message}")
            }
        }

        }
    sealed class UserState {
        object Initial : UserState()
        object Loading : UserState()
        data class Success(val userData: UserData) : UserState()
        data class Error(val message: String) : UserState()
    }
}
