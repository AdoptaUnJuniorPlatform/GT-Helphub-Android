package com.alejandro.helphub.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.helphub.domain.models.ProfileListUIState
import com.alejandro.helphub.domain.models.ProfileUIState
import com.alejandro.helphub.domain.models.SkillData
import com.alejandro.helphub.domain.models.SkillUIState
import com.alejandro.helphub.domain.models.UserAuthData
import com.alejandro.helphub.domain.models.UserProfileData
import com.alejandro.helphub.domain.usecase.auth.GetAllUsersUseCase
import com.alejandro.helphub.domain.usecase.auth.GetUserByIdUseCase
import com.alejandro.helphub.domain.usecase.profile.GetProfileByUserIdUseCase
import com.alejandro.helphub.domain.usecase.profile.GetProfileImageByUserIdUseCase
import com.alejandro.helphub.domain.usecase.skill.GetSkillsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase, //For future needs
    private val getSkillsByCategoryUseCase: GetSkillsByCategoryUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getProfileByUserIdUseCase: GetProfileByUserIdUseCase,
    private val getProfileImageByUserIdUseCase: GetProfileImageByUserIdUseCase

) : ViewModel() {
    private val _userAuthData = MutableStateFlow(UserAuthData())
    val userAuthData: StateFlow<UserAuthData> =
        _userAuthData.asStateFlow()

    private val _userProfileData = MutableStateFlow(UserProfileData())
    val userProfileData: StateFlow<UserProfileData> =
        _userProfileData.asStateFlow()

    private val _skillDataList =
        MutableStateFlow<List<SkillData>>(emptyList())
    val skillDataList: StateFlow<List<SkillData>> = _skillDataList.asStateFlow()

    private val _profileDataList =
        MutableStateFlow<List<UserProfileData>>(emptyList())
    val profileDataList: StateFlow<List<UserProfileData>> =
        _profileDataList.asStateFlow()

    private val _skillUIState = MutableStateFlow<SkillUIState>(
        SkillUIState.Idle
    )
    val skillUIState: StateFlow<SkillUIState> = _skillUIState.asStateFlow()
    private val _profileUIState = MutableStateFlow<ProfileUIState>(
        ProfileUIState.Idle
    )
    val profileUIState: StateFlow<ProfileUIState> =
        _profileUIState.asStateFlow()

    private val _profileListUIState = MutableStateFlow<ProfileListUIState>(
        ProfileListUIState.Idle
    )
    val profileListUIState: StateFlow<ProfileListUIState> =
        _profileListUIState.asStateFlow()

    private val _userListState =
        MutableStateFlow<List<UserAuthData>>(emptyList())
    val userListState: StateFlow<List<UserAuthData>> =
        _userListState.asStateFlow()

    private val _profileListState =
        MutableStateFlow<List<UserProfileData>>(emptyList())
    val profileListState: StateFlow<List<UserProfileData>> =
        _profileListState.asStateFlow()

    private val _profileImageMap =
        MutableStateFlow<Map<String, ByteArray?>>(emptyMap())
    val profileImageMap: StateFlow<Map<String, ByteArray?>> =
        _profileImageMap.asStateFlow()

    fun getSkillsForAllCategories(categories: List<String>) {
        viewModelScope.launch {
            _skillUIState.value = SkillUIState.Loading
            Log.d("HomeViewModel", "Fetching skills for all categories.")

            val allSkills = mutableListOf<SkillData>()
            val userIds = mutableSetOf<String>()
            val userList = mutableListOf<UserAuthData>()
            val profileList = mutableListOf<UserProfileData>()
            val newProfileImageMap = mutableMapOf<String, ByteArray?>()
            categories.forEach { category ->
                try {
                    val response = getSkillsByCategoryUseCase(category)
                    if (response.isSuccessful) {
                        response.body()?.let { skillResponseList ->
                            val categorySkills =
                                skillResponseList.map { skill ->
                                    userIds.add(skill.userId)
                                    SkillData(
                                        id = skill.id,
                                        title = skill.title,
                                        level = skill.level,
                                        mode = skill.mode,
                                        description = skill.description,
                                        category = skill.category,
                                        userId = skill.userId
                                    )
                                }
                            allSkills.addAll(categorySkills)
                            Log.i(
                                "HomeViewModel",
                                "Loaded ${skillResponseList.size} skills for category: $category"
                            )
                        }
                    } else {
                        Log.e(
                            "HomeViewModel",
                            "Error fetching skills for category $category: ${response.code()}"
                        )
                    }
                } catch (e: Exception) {
                    Log.e(
                        "HomeViewModel",
                        "Exception while fetching skills for category $category: ${e.message}"
                    )
                }
            }
            val distinctSkills = allSkills.distinctBy { it.id }
            if (distinctSkills.isNotEmpty()) {
                _skillDataList.value = distinctSkills
            }
            val distinctUserIds = userIds.toList()
            Log.d("HomeViewModel", "User IDs: $distinctUserIds")
            distinctUserIds.forEach { userId ->
                try {
                    val userResponse = getUserByIdUseCase(userId)
                    if (userResponse.isSuccessful) {
                        userResponse.body()?.let { user ->
                            userList.add(
                                UserAuthData(
                                    nameUser = user.nameUser,
                                    surnameUser = user.surnameUser,
                                    id = user.id
                                )
                            )
                        }
                    }
                    val profileResponse = getProfileByUserIdUseCase(userId)
                    if (profileResponse.isSuccessful) {
                        profileResponse.body()?.let { profile ->
                            profileList.add(
                                UserProfileData(
                                    userId = profile.userId.id,
                                    profileImage = profile.profilePicture,
                                    description = profile.description,
                                    location = profile.location,
                                    preferredTimeRange = profile.preferredTimeRange,
                                    interestedSkills = profile.interestedSkills,
                                    selectedDays = profile.selectedDays
                                )
                            )
                        }
                    }
                    val imageResponse = getProfileImageByUserIdUseCase(userId)
                    if (imageResponse.isSuccessful) {
                        imageResponse.body()?.byteStream()?.let { inputStream ->
                            val byteArray = inputStream.readBytes()
                            newProfileImageMap[userId] = byteArray
                        }
                    }
                } catch (e: Exception) {
                    Log.e(
                        "HomeViewModel",
                        "Error fetching user or profile: ${e.message}"
                    )
                }
            }
            _profileImageMap.value = newProfileImageMap
            _skillDataList.value = allSkills.distinctBy { it.id }
            _userListState.value = userList
            _profileListState.value = profileList
            Log.d(
                "HomeViewModel",
                "Final profileList size: ${profileList.size}"
            )
        }
    }
}