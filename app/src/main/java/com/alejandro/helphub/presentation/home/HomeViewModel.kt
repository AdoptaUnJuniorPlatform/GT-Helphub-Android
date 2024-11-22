package com.alejandro.helphub.presentation.home

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.helphub.data.source.remote.server.response.SkillResponse
import com.alejandro.helphub.domain.models.ProfileListUIState
import com.alejandro.helphub.domain.models.ProfileUIState
import com.alejandro.helphub.domain.models.SkillData
import com.alejandro.helphub.domain.models.SkillUIState
import com.alejandro.helphub.domain.models.UserAuthData
import com.alejandro.helphub.domain.models.UserProfileData
import com.alejandro.helphub.domain.usecase.auth.GetAllUsersUseCase
import com.alejandro.helphub.domain.usecase.auth.GetUserByIdUseCase
import com.alejandro.helphub.domain.usecase.profile.GetProfileByIdUseCase
import com.alejandro.helphub.domain.usecase.profile.GetProfileByUserIdUseCase
import com.alejandro.helphub.domain.usecase.profile.GetProfileImageByUserIdUseCase
import com.alejandro.helphub.domain.usecase.profile.GetProfileImageUseCase
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
    private val getProfileImageUseCase: GetProfileImageUseCase,
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

    private val _profileImage = MutableStateFlow<ByteArray?>(null)
    val profileImage: StateFlow<ByteArray?> = _profileImage

    private val _profileImageBitmap = MutableStateFlow<Bitmap?>(null)
    val profileImageBitmap: StateFlow<Bitmap?> = _profileImageBitmap

    fun getUserById(userId: String) {
        viewModelScope.launch {
            _profileUIState.value = ProfileUIState.Loading
            Log.d(
                "ProfileViewModel",
                "Fetching user for userId: $userId"
            )

            try {
                val response = getUserByIdUseCase(userId)
                // Verifica si la respuesta fue exitosa
                if (response.isSuccessful) {
                    response.body()?.let { userResponse ->
                        _userAuthData.value = UserAuthData(
                            nameUser = userResponse.nameUser,
                            surnameUser = userResponse.surnameUser,
                            id = userResponse.id
                        )
                        _profileUIState.value =
                            ProfileUIState.Success(userResponse)
                        Log.i(
                            "ProfileViewModel",
                            "User loaded successfully $userResponse"
                        )
                        getProfileByUserId(userResponse.id)
                        Log.d(
                            "ProfileViewModel",
                            "getProfileById called with id: ${userResponse.id}"
                        )
                    } ?: run {
                        _profileUIState.value =
                            ProfileUIState.ProfileNotFound
                        Log.w("ProfileViewModel", "User not found")
                    }
                } else {
                    _profileUIState.value =
                        ProfileUIState.Error(500) // Or another error code
                    Log.e(
                        "ProfileViewModel",
                        "Error: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                _profileUIState.value =
                    ProfileUIState.Error(500) // Or another error code
                Log.e(
                    "ProfileViewModel",
                    "Error loading user: ${e.message}"
                )
            }
        }
    }

    fun getProfileByUserId(id: String) {
        viewModelScope.launch {
            _profileUIState.value = ProfileUIState.Loading
            Log.d(
                "ProfileViewModel",
                "Fetching profile for userId: $id"
            )

            try {
                val response = getProfileByUserIdUseCase(id)

                // Verifica si la respuesta fue exitosa
                if (response.isSuccessful) {
                    response.body()?.let { profileResponse ->

                        _userProfileData.value = UserProfileData(
                            profileImage = profileResponse.profilePicture,
                            description = profileResponse.description,
                            location = profileResponse.location,
                            preferredTimeRange = profileResponse.preferredTimeRange,
                            interestedSkills = profileResponse.interestedSkills,
                            selectedDays = profileResponse.selectedDays

                        )

                        _profileUIState.value =
                            ProfileUIState.Success(profileResponse)
                        Log.i(
                            "ProfileViewModel",
                            "Profile loaded successfully $profileResponse"
                        )
                    } ?: run {
                        _profileUIState.value =
                            ProfileUIState.ProfileNotFound
                        Log.w("ProfileViewModel", "Profile not found")
                    }
                } else {
                    _profileUIState.value =
                        ProfileUIState.Error(500) // Or another error code
                    Log.e(
                        "ProfileViewModel",
                        "Error: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                _profileUIState.value =
                    ProfileUIState.Error(500) // Or another error code
                Log.e(
                    "ProfileViewModel",
                    "Error loading profile: ${e.message}"
                )
            }
        }
    }

    fun getSkillsForAllCategories(categories: List<String>) {
        viewModelScope.launch {
            _skillUIState.value = SkillUIState.Loading
            Log.d("HomeViewModel", "Fetching skills for all categories.")

            val allSkills = mutableListOf<SkillData>()
            val userIds = mutableSetOf<String>()
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
                launch {
                    getUserById(userId)
                    getProfileByUserId(userId)
                    getProfileImageByUserId(userId)
                }
            }
        }
    }

    fun getBitmapFromByteArray(byteArray: ByteArray?): Bitmap? {
        return byteArray?.let {
            BitmapFactory.decodeByteArray(it, 0, it.size)
        }
    }

    fun getProfileImageByUserId(id: String) {
        viewModelScope.launch {
            try {
                val response = getProfileImageByUserIdUseCase(id)
                if (response.isSuccessful) {
                    // Si la respuesta es exitosa, obtenemos el flujo de la imagen
                    val inputStream = response.body()?.byteStream()

                    if (inputStream != null) {
                        // Aquí puedes convertir el InputStream en una imagen para mostrarla en la UI
                        // Por ejemplo, guardarla en un archivo o mostrarla con una librería como Glide
                        val byteArray = inputStream.readBytes()
                        val bitmap = getBitmapFromByteArray(byteArray)

                        _profileImage.value = byteArray
                        _profileImageBitmap.value = bitmap

                        Log.i(
                            "ProfileViewModel",
                            "Imagen recuperada correctamente."
                        )
                        // Aquí puedes usar el InputStream o guardarlo como un archivo
                    } else {
                        Log.e(
                            "ProfileViewModel",
                            "Error: El flujo de la imagen está vacío"
                        )
                    }
                } else {
                    Log.e(
                        "ProfileViewModel",
                        "Error al recuperar la imagen: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                Log.e(
                    "ProfileViewModel",
                    "Error al recuperar la imagen: ${e.message}"
                )
            }
        }
    }
}
