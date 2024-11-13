package com.alejandro.helphub.presentation.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.helphub.data.source.remote.server.response.UserId
import com.alejandro.helphub.domain.models.ProfileUIState
import com.alejandro.helphub.domain.models.SkillData
import com.alejandro.helphub.domain.models.UserAuthData
import com.alejandro.helphub.domain.models.UserProfileData
import com.alejandro.helphub.domain.usecase.auth.GetUserByIdUseCase
import com.alejandro.helphub.domain.usecase.profile.CreateProfileUseCase
import com.alejandro.helphub.domain.usecase.profile.GetProfileByIdUseCase
import com.alejandro.helphub.domain.usecase.profile.GetUserInfoUseCase
import com.alejandro.helphub.domain.usecase.skill.CreateSkillUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val getProfileByIdUseCase: GetProfileByIdUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val createProfileUseCase: CreateProfileUseCase,
    private val createSkillUseCase: CreateSkillUseCase
) : ViewModel() {

    private val _userProfileData = MutableStateFlow(UserProfileData())
    val userProfileData: StateFlow<UserProfileData> =
        _userProfileData.asStateFlow()

    private val _skillData = MutableStateFlow(SkillData())
    val skillData: StateFlow<SkillData> = _skillData.asStateFlow()

    //<!--------------------Profile ---------------->

    private val _userAuthData = MutableStateFlow(UserAuthData())
    val userAuthData: StateFlow<UserAuthData> =
        _userAuthData.asStateFlow()

    private val _profileUIState = MutableStateFlow<ProfileUIState>(
        ProfileUIState.Idle)
    val profileUIState: StateFlow<ProfileUIState> = _profileUIState.asStateFlow()

    fun getUserById(userId: String) {
        viewModelScope.launch {
            _profileUIState.value = ProfileUIState.Loading
            Log.d("ProfileViewModel", "Fetching user for userId: $userId")

            try {
                val response = getUserByIdUseCase(userId)
                // Verifica si la respuesta fue exitosa
                if (response.isSuccessful) {
                    response.body()?.let { userResponse ->

                        _userAuthData.value = UserAuthData(
                            nameUser = userResponse.nameUser,
                            surnameUser = userResponse.surnameUser,
                        )

                        _profileUIState.value = ProfileUIState.Success(userResponse)
                        Log.i("ProfileViewModel", "Profile loaded successfully $userResponse")
                    } ?: run {
                        _profileUIState.value = ProfileUIState.ProfileNotFound
                        Log.w("ProfileViewModel", "Profile not found")
                    }
                } else {
                    _profileUIState.value = ProfileUIState.Error(500) // Or another error code
                    Log.e("ProfileViewModel", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _profileUIState.value = ProfileUIState.Error(500) // Or another error code
                Log.e("ProfileViewModel", "Error loading profile: ${e.message}")
            }
        }
    }





    fun getProfileById(id: String) {
        viewModelScope.launch {
            _profileUIState.value = ProfileUIState.Loading
            Log.d("ProfileViewModel", "Fetching profile for userId: $id")

            try {
                val response = getProfileByIdUseCase(id)

                // Verifica si la respuesta fue exitosa
                if (response.isSuccessful) {
                    response.body()?.let { profileResponse ->

                        _userProfileData.value = UserProfileData(
                            profilePicture = profileResponse.profilePicture,
                            description = profileResponse.description,
                            location = profileResponse.location
                        )

                        _profileUIState.value = ProfileUIState.Success(profileResponse)
                        Log.i("ProfileViewModel", "Profile loaded successfully $profileResponse")
                    } ?: run {
                        _profileUIState.value = ProfileUIState.ProfileNotFound
                        Log.w("ProfileViewModel", "Profile not found")
                    }
                } else {
                    _profileUIState.value = ProfileUIState.Error(500) // Or another error code
                    Log.e("ProfileViewModel", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _profileUIState.value = ProfileUIState.Error(500) // Or another error code
                Log.e("ProfileViewModel", "Error loading profile: ${e.message}")
            }
        }
    }


    //<!--------------------Profile Setup Step 1 ---------------->

    fun updateUserDescription(userDescription: String) {
        Log.d("ProfileViewModel", "Updating postTitle to: $userDescription")
        val updateUserData =
            userProfileData.value.copy(description = userDescription)
        _userProfileData.value = updateUserData
        navigateToStep2()
    }

    fun updatePostalCode(postalCode: String) {
        Log.d("ProfileViewModel", "Updating postalCode to: $postalCode")
        val updateUserData = userProfileData.value.copy(location = postalCode)
        _userProfileData.value = updateUserData
        navigateToStep2()
    }

    private val _isNavigationToStep2Enabled = MutableStateFlow(false)
    val isNavigationToStep2Enabled: StateFlow<Boolean> =
        _isNavigationToStep2Enabled.asStateFlow()

    fun navigateToStep2() {
        _isNavigationToStep2Enabled.value =
            userProfileData.value.description.isNotBlank() &&
                    userProfileData.value.location.isNotBlank()
    }

//<!--------------------Profile Setup Step 2 ---------------->

    private val _isNavigationToStep3Enabled = MutableStateFlow(false)
    val isNavigationToStep3Enabled: StateFlow<Boolean> =
        _isNavigationToStep3Enabled.asStateFlow()

    fun updateUserPhotoUri(photoUri: Uri) {
        val updateUserData = userProfileData.value.copy(profilePicture = photoUri.toString())
        _userProfileData.value = updateUserData
        navigateToStep3()
    }

    fun navigateToStep3() {
        _isNavigationToStep3Enabled.value =
            userProfileData.value.profilePicture != null
    }

    //<!--------------------Profile Setup Step 3 ---------------->

    private val _isNavigationToStep4PostEnabled = MutableStateFlow(false)
    val isNavigationToStep4PostEnabled: StateFlow<Boolean> =
        _isNavigationToStep4PostEnabled.asStateFlow()

    private val _expanded = MutableStateFlow(false)
    val expanded: StateFlow<Boolean> = _expanded.asStateFlow()

    private val _selectedDays = MutableStateFlow<List<String>>(emptyList())
    val selectedDays: StateFlow<List<String>> = _selectedDays

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
        _userProfileData.value =
            _userProfileData.value.copy(selectedDays = days)
        navigateToStep4Post()
    }

    fun updateAvailability(availability: String) {
        Log.d("ProfileViewModel", "Updating selectedLevel to: $availability")
        val updateUserData =
            _userProfileData.value.copy(preferredTimeRange = availability)
        _userProfileData.value = updateUserData
        navigateToStep4Post()
    }

    fun navigateToStep4Post() {
        _isNavigationToStep4PostEnabled.value =
            userProfileData.value.selectedDays.isNotEmpty() &&
                    userProfileData.value.preferredTimeRange != null
    }
    //<!--------------------Profile Setup Step 4a ---------------->

    private val _isNavigationToStep4SkillEnabled = MutableStateFlow(false)
    val isNavigationToStep4SkillEnabled: StateFlow<Boolean> =
        _isNavigationToStep4SkillEnabled.asStateFlow()

    fun updatePostTitle(title: String) {
        Log.d("ProfileViewModel", "Updating postTitle to: $title")
        val updateSkillData = skillData.value.copy(title = title)
        _skillData.value = updateSkillData
        navigateToStep4Skill()
    }

    fun updateSelectedLevel(level: String) {
        Log.d("ProfileViewModel", "Updating selectedLevel to: $level")
        val updateSkillData = skillData.value.copy(level = level)
        _skillData.value = updateSkillData
        navigateToStep4Skill()
    }

    fun updateLearningMode(mode: String) {
        Log.d("ProfileViewModel", "Updating learning mode to: $mode")
        val updateSkillData = skillData.value.copy(mode = mode)
        _skillData.value = updateSkillData
        navigateToStep4Skill()
    }

    fun navigateToStep4Skill() {
        _isNavigationToStep4SkillEnabled.value =
            skillData.value.title.isNotBlank() &&
                    skillData.value.level != null &&
                    skillData.value.mode != null
    }

    //<!--------------------Profile Setup Step 4b ---------------->
    private val _isNavigationToStep5Enabled = MutableStateFlow(false)
    val isNavigationToStep5Enabled: StateFlow<Boolean> =
        _isNavigationToStep5Enabled.asStateFlow()

    private val _selectedCategories =
        MutableStateFlow<List<String>>(emptyList())
    val selectedCategories: StateFlow<List<String>> =
        _selectedCategories.asStateFlow()

    private val _createSkillResult = MutableStateFlow<String?>(null)
    val createSkillResult: StateFlow<String?> get() = _createSkillResult

    fun updateSkillDescription(skillDescription: String) {
        Log.d(
            "ProfileViewModel",
            "Updating skillDescription to: $skillDescription"
        )
        val updateSkillData =
            skillData.value.copy(description = skillDescription)
        _skillData.value = updateSkillData
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
        Log.d("ProfileViewModel", "Updating selectedCategories to: $categories")
        _skillData.value =
            skillData.value.copy(category = categories)
        navigateToStep5()
    }

    fun createSkill() {
        viewModelScope.launch {
            try {
                val result = createSkillUseCase(skillData.value)
                _createSkillResult.value = result
                Log.i("CreateSkill","Skill Created successfully")

            } catch (e: Exception) {
                Log.e("CreateSkill", "Error creating skill: ${e.message}")
                _createSkillResult.value = null

            }
        }

    }

    fun navigateToStep5() {
        Log.d(
            "ProfileViewModel",
            "Skill Description: ${skillData.value.description}"
        )
        Log.d(
            "ProfileViewModel",
            "Selected Categories: ${skillData.value.category}"
        )
        _isNavigationToStep5Enabled.value =
            skillData.value.description.isNotBlank() &&
                    skillData.value.category.isNotEmpty()
    }


    //<!--------------------Profile Setup Step 5 ---------------->

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
        _userProfileData.value =
            userProfileData.value.copy(interestedSkills = categories)
        navigateToHome()
    }

    private val _createProfileResult = MutableStateFlow<String?>(null)
    val createProfileResult: StateFlow<String?> get() = _createProfileResult

    fun createProfile() {
        viewModelScope.launch {
            try {
                val result = createProfileUseCase(userProfileData.value)
                _createProfileResult.value = result
                Log.i("CreateProfile","Profile Created successfully")

            } catch (e: Exception) {
                Log.e("CreateProfile", "Error creating profile: ${e.message}")
                _createProfileResult.value = null

            }
        }

    }

    fun navigateToHome() {
        Log.d(
            "ProfileViewModel",
            "Categories Of Interest: ${userProfileData.value.interestedSkills}"
        )
        _isNavigationToHomeEnabled.value =
            userProfileData.value.interestedSkills.isNotEmpty()
    }
}