package com.alejandro.helphub.presentation.profile

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.helphub.data.source.remote.dto.skill.CreateSkillDTO
import com.alejandro.helphub.domain.models.ProfileImageData
import com.alejandro.helphub.domain.models.ProfileListUIState
import com.alejandro.helphub.domain.models.ProfileUIState
import com.alejandro.helphub.domain.models.SkillData
import com.alejandro.helphub.domain.models.SkillUIState
import com.alejandro.helphub.domain.models.UserAuthData
import com.alejandro.helphub.domain.models.UserProfileData
import com.alejandro.helphub.domain.usecase.auth.GetUserByIdUseCase
import com.alejandro.helphub.domain.usecase.profile.CreateProfileUseCase
import com.alejandro.helphub.domain.usecase.profile.GetProfileByIdUseCase
import com.alejandro.helphub.domain.usecase.auth.GetUserByEmailUseCase
import com.alejandro.helphub.domain.usecase.profile.GetProfileImageUseCase
import com.alejandro.helphub.domain.usecase.profile.UploadProfileImageUseCase
import com.alejandro.helphub.domain.usecase.skill.CreateSkillUseCase
import com.alejandro.helphub.domain.usecase.skill.DeleteSkillUseCase
import com.alejandro.helphub.domain.usecase.skill.GetSkillsByUserIdUseCase
import com.alejandro.helphub.domain.usecase.skill.UpdateSkillUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserByEmailUseCase: GetUserByEmailUseCase,
    private val getProfileByIdUseCase: GetProfileByIdUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val createProfileUseCase: CreateProfileUseCase,
    private val createSkillUseCase: CreateSkillUseCase,
    private val getSkillsByUserIdUseCase: GetSkillsByUserIdUseCase,
    private val uploadProfileImageUseCase: UploadProfileImageUseCase,
    private val getProfileImageUseCase: GetProfileImageUseCase,
    private val deleteSkillUseCase: DeleteSkillUseCase,
    private val updateSkillUseCase: UpdateSkillUseCase
) : ViewModel() {

    private val _userProfileData = MutableStateFlow(UserProfileData())
    val userProfileData: StateFlow<UserProfileData> =
        _userProfileData.asStateFlow()

    private val _skillData = MutableStateFlow(SkillData())
    val skillData: StateFlow<SkillData> = _skillData.asStateFlow()

    private val _skillDataList =
        MutableStateFlow<List<SkillData>>(emptyList()) // Esto crea una lista vacía de SkillData
    val skillDataList: StateFlow<List<SkillData>> = _skillDataList.asStateFlow()

    //<!--------------------Update Skill Screen---------------->

    fun getSkillId(): String? {
        // Extraemos el primer Skill ID de la lista, o null si la lista está vacía
        return _skillDataList.value.firstOrNull()?.id
    }



    fun updateSkill(skillId: String, createSkillDTO: CreateSkillDTO) {
        viewModelScope.launch {
            try {
                // Llama al caso de uso y obtiene los datos actualizados
                val updatedSkill = updateSkillUseCase(skillId, createSkillDTO)

                // Actualiza el estado con la nueva información
                _skillData.value = updatedSkill
            } catch (e: Exception) {
                // En caso de error, maneja el estado del mensaje de error

            }
        }
    }



    //<!--------------------Profile ---------------->

    private val _userAuthData = MutableStateFlow(UserAuthData())
    val userAuthData: StateFlow<UserAuthData> =
        _userAuthData.asStateFlow()

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

    private val _skillUIState = MutableStateFlow<SkillUIState>(
        SkillUIState.Idle
    )
    val skillUIState: StateFlow<SkillUIState> = _skillUIState.asStateFlow()


    fun deleteSkill(skillId: String) {
        viewModelScope.launch {
            _skillUIState.value = SkillUIState.Loading
            try {
                val response = deleteSkillUseCase(skillId)
                if (response.isSuccessful) {
                    // Si la eliminación es exitosa, actualiza la lista de habilidades
                    _skillDataList.value = _skillDataList.value.filterNot { it.id == skillId }
                    _skillUIState.value = SkillUIState.Success(emptyList()) // O la lista actualizada
                } else {
                    _skillUIState.value = SkillUIState.Error(500) // Error en la respuesta
                }
            } catch (e: Exception) {
                _skillUIState.value = SkillUIState.Error(500) // Error desconocido
            }
        }
    }



    fun getSkillsByUserId(userId: String) {
        viewModelScope.launch {
            _skillUIState.value = SkillUIState.Loading
            Log.d("ProfileViewModel", "Fetching skills for userId: $userId")

            try {
                val response = getSkillsByUserIdUseCase(userId)
                // Verifica si la respuesta fue exitosa
                if (response.isSuccessful) {
                    response.body()?.let { skillResponseList ->
                        if (skillResponseList.isNotEmpty()) {
                            _skillDataList.value =
                                skillResponseList.map { skill ->
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

                            _skillUIState.value =
                                SkillUIState.Success(skillResponseList)
                            Log.i(
                                "ProfileViewModel",
                                "Skills loaded successfully: ${skillResponseList.size} skills"
                            )
                            skillResponseList.forEach { skill ->
                                Log.i(
                                    "ProfileViewModel",
                                    "Skill ID: ${skill.id}, Title: ${skill.title}, Level: ${skill.level}, " +
                                            "Mode: ${skill.mode}, Description: ${skill.description}, " +
                                            "Categories: ${skill.category.joinToString()}, UserId: ${skill.userId}"
                                )
                            }
                        } else {
                            _skillUIState.value = SkillUIState.SkillNotFound
                            Log.w(
                                "ProfileViewModel",
                                "No skills found for userId: $userId"
                            )
                        }
                    }
                } else {
                    _skillUIState.value =
                        SkillUIState.Error(500) // Error en la respuesta
                    Log.e("ProfileViewModel", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _skillUIState.value =
                    SkillUIState.Error(500) // Error desconocido
                Log.e("ProfileViewModel", "Error loading skills: ${e.message}")
            }
        }
    }


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
                        )

                        _profileUIState.value =
                            ProfileUIState.Success(userResponse)
                        Log.i(
                            "ProfileViewModel",
                            "User loaded successfully $userResponse"
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


    fun getProfileById(id: String) {
        viewModelScope.launch {
            _profileUIState.value = ProfileUIState.Loading
            Log.d(
                "ProfileViewModel",
                "Fetching profile for userId: $id"
            )

            try {
                val response = getProfileByIdUseCase(id)

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


    //<!--------------------Profile Setup Step 1 ---------------->

    fun updateUserDescription(userDescription: String) {
        Log.d(
            "ProfileViewModel",
            "Updating postTitle to: $userDescription"
        )
        val updateUserData =
            userProfileData.value.copy(description = userDescription)
        _userProfileData.value = updateUserData
        navigateToStep2()
    }

    fun updatePostalCode(postalCode: String) {
        Log.d("ProfileViewModel", "Updating postalCode to: $postalCode")
        val updateUserData =
            userProfileData.value.copy(location = postalCode)
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

    fun updateUserPhotoUri(photoUri: Uri, context: Context) {
        try {
            // Convierte el Uri a un ByteArray
            val byteArray = context.contentResolver.openInputStream(photoUri)?.readBytes()

            // Actualiza el perfil con el nuevo ByteArray de la imagen
            val updateUserData = userProfileData.value.copy(profileImage = byteArray)
            _userProfileData.value = updateUserData

            // Sube la imagen
            uploadProfileImage(photoUri, context)

            // Navega a la siguiente pantalla
            navigateToStep3()
        } catch (e: Exception) {
            Log.e("ProfileViewModel", "Error al convertir la imagen a ByteArray: ${e.message}")
        }
    }

    fun createTempFileFromUri(uri: Uri, context: Context): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val tempFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
            tempFile.outputStream().use { outputStream ->
                inputStream?.copyTo(outputStream)
            }
            tempFile
        } catch (e: Exception) {
            Log.e("UploadProfileImage", "Error creando archivo temporal: ${e.message}")
            null
        }
    }
    private val _profileImageId = MutableStateFlow<String?>(null)
    val profileImageId: StateFlow<String?> = _profileImageId

    private val _profileImage = MutableStateFlow<ByteArray?>(null)
    val profileImage: StateFlow<ByteArray?> = _profileImage

    private val _profileImageBitmap = MutableStateFlow<Bitmap?>(null)
    val profileImageBitmap: StateFlow<Bitmap?> = _profileImageBitmap

    fun uploadProfileImage(photoUri: Uri, context: Context) {
        viewModelScope.launch {
            val userId = userAuthDataList.value.firstOrNull()?.id ?: ""
            //  val imageProfile = userProfileData.value.profileImage
            if (userId.isNotEmpty() && photoUri != Uri.EMPTY) {

                try {
                    // Convierte la URI de la imagen a un archivo de tipo MultipartBody.Part
                    val imageFile = createTempFileFromUri(photoUri, context)
                        ?: throw Exception("No se pudo crear el archivo temporal")
                    val imagePart =
                        imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                    val imageProfilePart = MultipartBody.Part.createFormData(
                        "image_profile",
                        imageFile.name,
                        imagePart
                    )

                    // Crea el campo para el id_user
                    val idUserPart =
                        MultipartBody.Part.createFormData("id_user", userId)

                    // Realiza la llamada al caso de uso
                    val result =
                        uploadProfileImageUseCase(idUserPart, imageProfilePart)

                    if (result.idImage.isNotEmpty()) {
                        Log.d(
                            "ProfileViewModel",
                            "Imagen subida correctamente. ID de imagen: ${result.idImage}, Mensaje: ${result.message}"
                        )
                        _profileImageId.value = result.idImage
                        saveProfileImageId(context, result.idImage)
                    } else {
                        Log.e(
                            "ProfileViewModel",
                            "Error al subir la imagen: ${result.message}"
                        )
                    }
                } catch (e: Exception) {
                    Log.e(
                        "ProfileViewModel",
                        "Error al procesar la imagen: ${e.message}"
                    )
                }
            } else {
                Log.e(
                    "ProfileViewModel",
                    "Error: ID de usuario vacío o URI de imagen no válida."
                )
            }
        }
    }
    private fun saveProfileImageId(context: Context, idImage: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val sharedPreferences = context.getSharedPreferences("helphub_prefs", Context.MODE_PRIVATE)
                sharedPreferences.edit()
                    .putString("profile_image_id", idImage)
                    .apply()
                Log.d("ProfileViewModel", "ID de la imagen guardado en SharedPreferences: $idImage")

            }
        }
    }

    fun getProfileImageId(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences("helphub_prefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("profile_image_id", null)
    }

    fun getBitmapFromByteArray(byteArray: ByteArray?): Bitmap? {
        return byteArray?.let {
            BitmapFactory.decodeByteArray(it, 0, it.size)
        }
    }


    fun getProfileImage(context: Context) {
        viewModelScope.launch {
            val imageId = getProfileImageId(context)

            if (imageId != null) {
                try {
                    val response = getProfileImageUseCase(imageId)

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

                            Log.i("ProfileViewModel", "Imagen recuperada correctamente.")
                            // Aquí puedes usar el InputStream o guardarlo como un archivo
                        } else {
                            Log.e("ProfileViewModel", "Error: El flujo de la imagen está vacío")
                        }
                    } else {
                        Log.e("ProfileViewModel", "Error al recuperar la imagen: ${response.code()}")
                    }
                } catch (e: Exception) {
                    Log.e("ProfileViewModel", "Error al recuperar la imagen: ${e.message}")
                }
            } else {
                Log.i("ProfileViewModel", "ID de imagen no encontrado en SharedPreferences")
            }
        }
    }



    fun navigateToStep3() {
        _isNavigationToStep3Enabled.value =
            userProfileData.value.profileImage != null
    }

    //<!--------------------Profile Setup Step 3 ---------------->

    private val _isNavigationToStep4PostEnabled =
        MutableStateFlow(false)
    val isNavigationToStep4PostEnabled: StateFlow<Boolean> =
        _isNavigationToStep4PostEnabled.asStateFlow()

    private val _expanded = MutableStateFlow(false)
    val expanded: StateFlow<Boolean> = _expanded.asStateFlow()

    private val _selectedDays =
        MutableStateFlow<List<String>>(emptyList())
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
        Log.d(
            "ProfileViewModel",
            "Updating selectedLevel to: $availability"
        )
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

    private val _isNavigationToStep4SkillEnabled =
        MutableStateFlow(false)
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

    private val _userAuthDataList =
        MutableStateFlow<List<UserAuthData>>(emptyList()) // Esto crea una lista vacía de SkillData
    val userAuthDataList: StateFlow<List<UserAuthData>> =
        _userAuthDataList.asStateFlow()

    fun getUserByEmail(email: String) {
        viewModelScope.launch {
            _profileListUIState.value = ProfileListUIState.Loading
            Log.d(
                "ProfileViewModel",
                "Fetching user for email: $email"
            )
            try {
                val response = getUserByEmailUseCase(email)
                // Verifica si la respuesta fue exitosa
                if (response.isSuccessful) {
                    response.body()?.let { userResponseList ->
                        if (userResponseList.isNotEmpty()) {
                            _userAuthDataList.value =
                                userResponseList.map { user ->
                                    UserAuthData(
                                        nameUser = user.nameUser,
                                        surnameUser = user.surnameUser,
                                        id = user.id

                                    )

                                }
                            _profileListUIState.value =
                                ProfileListUIState.Success(userResponseList)
                            Log.i(
                                "ProfileViewModel",
                                "User loaded successfully ${userResponseList.size} users"
                            )
                            userResponseList.forEach { user ->
                                Log.i(
                                    "ProfileViewModel",
                                    "User auth: ${user.nameUser}, Title: ${user.surnameUser}"
                                )
                            }
                        }
                    }
                } else {
                    _profileListUIState.value =
                        ProfileListUIState.Error(500) // Or another error code
                    Log.e(
                        "ProfileViewModel",
                        "Error: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                _profileListUIState.value =
                    ProfileListUIState.Error(500) // Or another error code
                Log.e(
                    "ProfileViewModel",
                    "Error loading user: ${e.message}"
                )
            }
        }
    }


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
        val currentCategories =
            _selectedCategories.value.toMutableList()
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
        Log.d(
            "ProfileViewModel",
            "Updating selectedCategories to: $categories"
        )
        _skillData.value =
            skillData.value.copy(category = categories)
        navigateToStep5()
    }

    fun createSkill() {
        viewModelScope.launch {
            try {
                val result = createSkillUseCase(skillData.value)
                _createSkillResult.value = result
                Log.i("CreateSkill", "Skill Created successfully")

            } catch (e: Exception) {
                Log.e(
                    "CreateSkill",
                    "Error creating skill: ${e.message}"
                )
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

    fun onCategoriesOfInterestChecked(
        category: String,
        isSelected: Boolean
    ) {
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
                Log.i("CreateProfile", "Profile Created successfully")

            } catch (e: Exception) {
                Log.e(
                    "CreateProfile",
                    "Error creating profile: ${e.message}"
                )
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