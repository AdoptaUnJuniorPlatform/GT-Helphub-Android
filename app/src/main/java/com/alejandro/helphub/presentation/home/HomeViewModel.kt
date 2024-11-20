package com.alejandro.helphub.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alejandro.helphub.data.source.remote.server.response.SkillResponse
import com.alejandro.helphub.domain.models.SkillData
import com.alejandro.helphub.domain.models.SkillUIState
import com.alejandro.helphub.domain.models.UserAuthData
import com.alejandro.helphub.domain.usecase.auth.GetAllUsersUseCase
import com.alejandro.helphub.domain.usecase.skill.GetSkillsByCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val getSkillsByCategoryUseCase: GetSkillsByCategoryUseCase
) : ViewModel() {

    private val _skillDataList =
        MutableStateFlow<List<SkillData>>(emptyList())
    val skillDataList: StateFlow<List<SkillData>> = _skillDataList.asStateFlow()

    private val _skillUIState = MutableStateFlow<SkillUIState>(
        SkillUIState.Idle
    )
    val skillUIState: StateFlow<SkillUIState> = _skillUIState.asStateFlow()


    fun getSkillsForAllCategories(categories: List<String>) {
        viewModelScope.launch {
            _skillUIState.value = SkillUIState.Loading
            Log.d("HomeViewModel", "Fetching skills for all categories.")

            val allSkills = mutableListOf<SkillData>()

            categories.forEach { category ->
                try {
                    val response = getSkillsByCategoryUseCase(category)
                    if (response.isSuccessful) {
                        response.body()?.let { skillResponseList ->
                            val categorySkills =
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
            if (allSkills.isNotEmpty()) {
                _skillDataList.value = allSkills
            }
        }
    }
}


/*
    fun getSkillsByCategory(category: String) {
        viewModelScope.launch {
            _skillUIState.value = SkillUIState.Loading
            Log.d("ProfileViewModel", "Fetching skills for category: $category")

            try {
                val response = getSkillsByCategoryUseCase(category)
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
                                "No skills found for userId: $category"
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
    }*/
