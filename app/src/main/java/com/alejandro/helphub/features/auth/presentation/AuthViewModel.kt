package com.alejandro.helphub.features.auth.presentation

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alejandro.helphub.features.auth.data.CountryProvider
import com.alejandro.helphub.features.auth.domain.CountriesModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {
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

    private val _selectedCountry = MutableLiveData(CountryProvider.countries.first())
    val selectedCountry: LiveData<CountriesModel> = _selectedCountry

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _surname = MutableLiveData<String>()
    val surname: LiveData<String> = _surname

    private val _isExpanded=MutableLiveData<Boolean>(false)
    val isExpanded:LiveData<Boolean> = _isExpanded

    private val _phoneNumber=MutableLiveData<String>("")
    val phoneNumber:LiveData<String> = _phoneNumber

    private val _isCheckBoxChecked=MutableLiveData<Boolean>(false)
    val isCheckBoxChecked:LiveData<Boolean> = _isCheckBoxChecked

    private val _isSwitchChecked=MutableLiveData<Boolean>(false)
    val isSwitchChecked:LiveData<Boolean> = _isSwitchChecked



    init {
        _countries.value =
            CountryProvider.countries // Asignamos la lista del provider
        _selectedCountry.value =
            CountryProvider.countries.first() // El primer país como predeterminado
    }

    fun onSwitchCheckedChanged(isChecked: Boolean) {
        _isSwitchChecked.value = isChecked
    }

    fun onCheckBoxCheckedChanged(isChecked:Boolean){
        _isCheckBoxChecked.value= isChecked
    }

    fun toggleExpanded() {
        _isExpanded.value = !(_isExpanded.value ?: false)
    }

    fun updatePhoneNumber(number: String) {
        _phoneNumber.value = number
    }

    fun onSignUp(name:String, surname:String){
        _name.value=name
        _surname.value=surname
    }

    fun updateSelectedCountry(country:CountriesModel){
        _selectedCountry.value =country
    }

    fun onLoginChanged(email: String, password: String) {
        _email.value = email
        _password.value = password
        _isLoginEnable.value = enableLogin(email, password)
    }

    private fun enableLogin(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 6
}