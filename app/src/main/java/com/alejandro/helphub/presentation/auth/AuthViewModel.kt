package com.alejandro.helphub.presentation.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.helphub.domain.models.CountriesModel
import com.alejandro.helphub.domain.models.UserAuthData
import com.alejandro.helphub.domain.usecase.auth.LoginUseCase
import com.alejandro.helphub.domain.usecase.auth.RegisterNewUserUseCase
import com.alejandro.helphub.domain.usecase.auth.RequestResetPasswordUseCase
import com.alejandro.helphub.domain.usecase.auth.sendtwofa.SendTwoFaLoginUseCase
import com.alejandro.helphub.domain.usecase.auth.sendtwofa.SendTwoFaRegisterUseCase
import com.alejandro.helphub.domain.usecase.auth.sendtwofa.SendTwoFaResetPasswordUseCase
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

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

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
            _userAuthData.value.copy(
                optionCall = isChecked,
                showPhone = isChecked
            )
    }

    fun updateSignUpButtonState() {
        _isSignUpButtonEnabled.value =
            userAuthData.value.nameUser.isNotBlank() &&
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

    private val _email = MutableStateFlow<String?>(null)
    val email: StateFlow<String?> get() = _email

    fun updateUserEmail(email: String) {
        Log.d("AuthViewModel", "Updating email to: $email")
        val updateUserData = userAuthData.value.copy(email = email)
        _userAuthData.value = updateUserData
        _email.value=email
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
            "Generated code: $code"
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
                Log.i("2FA", "Saved 2fa code: ${updatedUserData.twoFa}")
                val result = sendTwoFaRegisterUseCase(updatedUserData)
                if (result.isNotEmpty()) {
                    _twoFaStatus.value = ResultStatus.Success(Unit)
                    Log.i("this", "success")
                } else {
                    _twoFaStatus.value =
                        ResultStatus.Error("Credentials are not accepted")
                    Log.i("this", "Credentials are not accepted")
                }
                _userAuthData.value = updatedUserData
            } catch (e: Exception) {
                _twoFaStatus.value = ResultStatus.Error("Connection failed")
                Log.e("2FA", "Sending 2fa code failed: ${e.message}")
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
        Log.i("2FA", "2FA typed code: ${inputTwoFaCode.value}")
        Log.i("2FA", "2FA saved code: ${userAuthData.value.twoFa}")
        Log.i("2FA", "Saved email: ${userAuthData.value.email}")
        return userAuthData.value.twoFa == inputTwoFaCode.value
    }

    fun onTwoFaCodeChanged(newCode: String) {
        Log.i("2FA", "Typed new 2fa code: $newCode")
        _inputTwoFaCode.value = newCode
    }

    fun registerUser(callback: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val result = registerNewUserUseCase(userAuthData.value)
                _registrationResult.value = result
                callback(true)
            } catch (e: Exception) {
                Log.e("Register", "Failure during register: ${e.message}")
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

    private val _authToken = MutableStateFlow<String?>(null)
    val authToken: StateFlow<String?> get() = _authToken

    fun loginUser() {
        viewModelScope.launch {
            _isLoginLoading.value = true
            _loginStatus.value = ResultStatus.Idle
            try {
                val result = loginUseCase(userAuthData.value)
                _loginStatus.value = result.fold(
                    onSuccess = { token ->
                        _authToken.value = token
                        ResultStatus.Success(token)
                    },
                    onFailure = { e ->
                        ResultStatus.Error(
                            e.message ?: "Unknown error"
                        )
                    }
                )
            } catch (e: Exception) {
                _loginStatus.value =
                    ResultStatus.Error(e.message ?: "Sudden error")
                Log.e("LoginError", "Failed to login", e)
            } finally {
                _isLoginLoading.value = false
            }
        }
        _isAuthenticated.value = true
    }

//<!--------------------2fa Login Screen ---------------->

    fun generateAndSendTwoFaCodeLogin() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val generatedTwoFa = generateTwoFaCode()
                val updatedUserData =
                    userAuthData.value.copy(twoFa = generatedTwoFa)
                Log.i("2FA", "Saved 2fa code: ${updatedUserData.twoFa}")
                val result = sendTwoFaLoginUseCase(updatedUserData)
                if (result.isNotEmpty()) {
                    _twoFaStatus.value = ResultStatus.Success(Unit)
                    Log.i("this", "success")
                } else {
                    _twoFaStatus.value =
                        ResultStatus.Error("Credentials are not accepted")
                    Log.i("this", "Credentials are not accepted")
                }
                _userAuthData.value = updatedUserData
            } catch (e: Exception) {
                _twoFaStatus.value = ResultStatus.Error("Connection failure")
                Log.e("2FA", "Failure sending 2fa code: ${e.message}")
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
                Log.i("2FA", "Saved 2fa code: ${updatedUserData.twoFa}")
                val result = sendTwoFaResetPasswordUseCase(updatedUserData)
                if (result.isNotEmpty()) {
                    _twoFaStatus.value = ResultStatus.Success(Unit)
                    Log.i("this", "success")
                } else {
                    _twoFaStatus.value =
                        ResultStatus.Error("Credentials are not accepted")
                    Log.i("this", "Credentials are not accepted")
                }
                _userAuthData.value = updatedUserData
            } catch (e: Exception) {
                _twoFaStatus.value = ResultStatus.Error("Connection failure")
                Log.e("2FA", "Failure sending 2fa code: ${e.message}")
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