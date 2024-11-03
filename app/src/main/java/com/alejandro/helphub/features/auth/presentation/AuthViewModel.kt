package com.alejandro.helphub.features.auth.presentation

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.helphub.features.auth.domain.CountriesModel
import com.alejandro.helphub.features.auth.domain.UserAuthData
import com.alejandro.helphub.features.auth.domain.usecases.LoginUseCase
import com.alejandro.helphub.features.auth.domain.usecases.RegisterNewUserUseCase
import com.alejandro.helphub.features.auth.domain.usecases.RequestResetPasswordUseCase
import com.alejandro.helphub.features.auth.domain.usecases.SendTwoFaLoginUseCase
import com.alejandro.helphub.features.auth.domain.usecases.SendTwoFaRegisterUseCase
import com.alejandro.helphub.features.auth.domain.usecases.SendTwoFaResetPasswordUseCase
import com.alejandro.helphub.utils.ResultStatus
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
class AuthViewModel @Inject constructor(
    private val sendTwoFaRegisterUseCase: SendTwoFaRegisterUseCase,
    private val registerNewUserUseCase: RegisterNewUserUseCase,
    private val loginUseCase: LoginUseCase,
    private val sendTwoFaResetPasswordUseCase: SendTwoFaResetPasswordUseCase,
    private val requestResetPasswordUseCase: RequestResetPasswordUseCase,
    private val sendTwoFaLoginUseCase: SendTwoFaLoginUseCase
) :
    ViewModel() {

    private val _userAuthData = MutableStateFlow(UserAuthData())
    val userAuthData: StateFlow<UserAuthData> = _userAuthData.asStateFlow()

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

    val isPasswordValid: StateFlow<Boolean> =
        _userAuthData.map { isPasswordValid(it.password) }
            .stateIn(viewModelScope, SharingStarted.Lazily, false)

    private val _twoFaStatus =
        MutableStateFlow<ResultStatus<Unit>>(ResultStatus.Idle)
    val twoFaStatus: StateFlow<ResultStatus<Unit>> = _twoFaStatus

    fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("^[\\w\\.-]+@[\\w\\.-]+\\.\\w+$"))
    }

    fun toggleExpanded() {
        _isExpanded.value = !(_isExpanded.value ?: false)
    }

    fun onSwitchCheckedChanged(isChecked: Boolean) {
        _isSwitchChecked.value = isChecked
        _userAuthData.value =
            _userAuthData.value.copy(optionCall = isChecked, showPhone = isChecked)
    }

    fun updateSignUpButtonState() {
        _isSignUpButtonEnabled.value = userAuthData.value.nameUser.isNotBlank() &&
                userAuthData.value.surnameUser.isNotBlank() &&
                userAuthData.value.phone.isNotBlank() &&
                userAuthData.value.email.isNotBlank() &&
                isValidEmail(userAuthData.value.email) &&
                userAuthData.value.password.isNotBlank() &&
                _isCheckBoxChecked.value == true
    }

    fun updatePhoneNumber(phoneNumber: String) {
        Log.d("AuthViewModel", "Updating phoneNumber to: $phoneNumber")
        _userAuthData.update { it.copy(phone = phoneNumber) }
        updateSignUpButtonState()
    }

    fun updateSelectedCountry(country: CountriesModel) {
        _userAuthData.update { it.copy(countryCode = country.code) }
    }

    fun updateUserEmail(email: String) {
        Log.d("AuthViewModel", "Updating email to: $email")
        val updateUserData = userAuthData.value.copy(email = email)
        _userAuthData.value = updateUserData
        updateSignUpButtonState()
    }

    fun updateUserPassword(password: String) {
        Log.d("AuthViewModel", "Updating password to: $password")
        val updateUserData = userAuthData.value.copy(password = password)
        _userAuthData.value = updateUserData
        updateSignUpButtonState()
    }

    fun updateUserName(name: String) {
        Log.d("AuthViewModel", "Updating name to: $name")
        val updateUserData = userAuthData.value.copy(nameUser = name)
        _userAuthData.value = updateUserData
        updateSignUpButtonState()
    }

    fun updateUserSurname2(surname2: String) {
        Log.d("AuthViewModel", "Updating surname2 to: $surname2")
        val updateUserData = userAuthData.value.copy(surname2 = surname2)
        _userAuthData.value = updateUserData
        updateSignUpButtonState()
    }

    fun updateUserSurname1(surname1: String) {
        Log.d("AuthViewModel", "Updating surname1 to: $surname1")
        val updateUserData = userAuthData.value.copy(surnameUser = surname1)
        _userAuthData.value = updateUserData
        updateSignUpButtonState()
    }

    fun onCheckBoxCheckedChanged(isChecked: Boolean) {
        _isCheckBoxChecked.value = isChecked
        updateSignUpButtonState()
    }

    fun isPasswordValid(password: String): Boolean {
        val hasUpperCase = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }
        val isValidLength = password.length >= 6
        return hasUpperCase && hasDigit && hasSpecialChar && isValidLength
    }

    fun onPasswordChanged(newPassword: String) {
        Log.d("AuthViewModel", "Updating password to: $newPassword")
        _userAuthData.value = _userAuthData.value.copy(password = newPassword)
        updateSignUpButtonState()
    }

    fun generateTwoFaCode(): String {
        val code = (100000..999999).random().toString()
        Log.i(
            "2FA Code",
            "Código generado: $code"
        )
        return code
    }

    fun resetTwoFaStatus() {
        _twoFaStatus.value = ResultStatus.Idle
    }

    fun generateAndSendTwoFaCode() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val generatedTwoFa = generateTwoFaCode()
                val updatedUserData =
                    userAuthData.value.copy(twoFa = generatedTwoFa)
                Log.i("2FA", "Código 2FA almacenado: ${updatedUserData.twoFa}")
                val result = sendTwoFaRegisterUseCase(updatedUserData)
                if (result.isNotEmpty()) {
                    _twoFaStatus.value = ResultStatus.Success(Unit)
                    Log.i("this", "funciona")
                } else {
                    _twoFaStatus.value =
                        ResultStatus.Error("Credenciales no válidas")
                    Log.i("this", "Credenciales no válidas")
                }
                _userAuthData.value = updatedUserData
            } catch (e: Exception) {
                _twoFaStatus.value = ResultStatus.Error("Error de conexión")
                Log.e("2FA", "Error al enviar el código 2FA: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    //<!--------------------DoubleAuthFactor Screen ---------------->

    private val _inputTwoFaCode = MutableStateFlow("")
    val inputTwoFaCode: StateFlow<String> = _inputTwoFaCode

    private val _registrationResult = MutableStateFlow<String?>(null)
    val registrationResult: StateFlow<String?> get() = _registrationResult

    fun isTwoFaCodeValid(): Boolean {
        Log.i("2FA", "Código 2FA ingresado: ${inputTwoFaCode.value}")
        Log.i("2FA", "Código 2FA almacenado: ${userAuthData.value.twoFa}")
        return userAuthData.value.twoFa == inputTwoFaCode.value
    }

    fun onTwoFaCodeChanged(newCode: String) {
        Log.i("2FA", "Nuevo código 2FA ingresado: $newCode")
        _inputTwoFaCode.value = newCode
    }

    fun registerUser(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val result = registerNewUserUseCase(userAuthData.value)
                _registrationResult.value = result
                callback(true)
            } catch (e: Exception) {
                Log.e("Registro", "Error durante el registro: ${e.message}")
                _registrationResult.value = null
                callback(false)
            }
        }
    }

    //<!--------------------Login Screen ---------------->

    private val _loginStatus =
        MutableStateFlow<ResultStatus<String>>(ResultStatus.Idle)
    val loginStatus: StateFlow<ResultStatus<String>> = _loginStatus

    private val _isLoginLoading = MutableStateFlow(false)
    val isLoginLoading: StateFlow<Boolean> = _isLoginLoading

    fun loginUser() {
        viewModelScope.launch {
            _isLoginLoading.value = true
            _loginStatus.value = ResultStatus.Idle
            try {
                val result = loginUseCase(userAuthData.value)
                _loginStatus.value = result.fold(
                    onSuccess = { token -> ResultStatus.Success(token) },
                    onFailure = { e ->
                        ResultStatus.Error(
                            e.message ?: "Error desconocido"
                        )
                    }
                )
            } catch (e: Exception) {
                _loginStatus.value =
                    ResultStatus.Error(e.message ?: "Error inesperado")
                Log.e("LoginError", "Failed to login", e)
            } finally {
                _isLoginLoading.value = false
            }
        }
    }

//<!--------------------2fa Login Screen ---------------->

    fun generateAndSendTwoFaCodeLogin() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val generatedTwoFa = generateTwoFaCode()
                val updatedUserData =
                    userAuthData.value.copy(twoFa = generatedTwoFa)
                Log.i("2FA", "Código 2FA almacenado: ${updatedUserData.twoFa}")
                val result = sendTwoFaLoginUseCase(updatedUserData)
                if (result.isNotEmpty()) {
                    _twoFaStatus.value = ResultStatus.Success(Unit)
                    Log.i("this", "funciona")
                } else {
                    _twoFaStatus.value =
                        ResultStatus.Error("Credenciales no válidas")
                    Log.i("this", "Credenciales no válidas")
                }
                _userAuthData.value = updatedUserData
            } catch (e: Exception) {
                _twoFaStatus.value = ResultStatus.Error("Error de conexión")
                Log.e("2FA", "Error al enviar el código 2FA: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

//<!--------------------ResetPassword Screen ---------------->

    private val _resetPasswordState =
        MutableStateFlow<ResultStatus<String>>(ResultStatus.Idle)
    val resetPasswordState: StateFlow<ResultStatus<String>> =
        _resetPasswordState.asStateFlow()

    fun generateAndSendTwoFaCodeResetPassword() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val generatedTwoFa = generateTwoFaCode()
                val updatedUserData =
                    userAuthData.value.copy(twoFa = generatedTwoFa)
                Log.i("2FA", "Código 2FA almacenado: ${updatedUserData.twoFa}")
                val result = sendTwoFaResetPasswordUseCase(updatedUserData)
                if (result.isNotEmpty()) {
                    _twoFaStatus.value = ResultStatus.Success(Unit)
                    Log.i("this", "funciona")
                } else {
                    _twoFaStatus.value =
                        ResultStatus.Error("Credenciales no válidas")
                    Log.i("this", "Credenciales no válidas")
                }
                _userAuthData.value = updatedUserData
            } catch (e: Exception) {
                _twoFaStatus.value = ResultStatus.Error("Error de conexión")
                Log.e("2FA", "Error al enviar el código 2FA: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    private val _inputNewPassword = MutableStateFlow("")
    val inputNewPassword: StateFlow<String> = _inputNewPassword

    private val _confirmNewPassword = MutableStateFlow("")
    val confirmNewPassword: StateFlow<String> = _confirmNewPassword

    fun onNewPasswordChanged(newPassword: String) {
        _inputNewPassword.value = newPassword
        enablePasswordReset()
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        _confirmNewPassword.value = newConfirmPassword
        enablePasswordReset()
    }

    fun isPasswordMatching(): Boolean {
        return inputNewPassword.value == confirmNewPassword.value
    }

    private val _isPasswordResetEnabled = MutableStateFlow(false)
    val isPasswordResetEnabled: StateFlow<Boolean> =
        _isPasswordResetEnabled.asStateFlow()

    fun enablePasswordReset() {
        _isPasswordResetEnabled.value =
            isPasswordMatching() && isTwoFaCodeValid()
    }

    fun requestResetPassword() {
        viewModelScope.launch {
            _resetPasswordState.value = ResultStatus.Idle
            _resetPasswordState.value = try {
                val updatedUserData =
                    userAuthData.value.copy(password = inputNewPassword.value)
                val result = requestResetPasswordUseCase(updatedUserData)
                if (result.isSuccess) {
                    ResultStatus.Success(result.getOrThrow())
                } else {
                    ResultStatus.Error(
                        result.exceptionOrNull()?.message ?: "An error occurred"
                    )
                }
            } catch (e: Exception) {
                ResultStatus.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun clearPasswordsField() {
        _userAuthData.value = _userAuthData.value.copy(password = "")
    }

    fun clearTwofaField() {
        _inputTwoFaCode.value = ""
    }
}