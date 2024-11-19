package com.alejandro.helphub.domain.models

import com.alejandro.helphub.data.source.remote.server.response.SkillResponse

sealed class SkillUIState {
    object Idle : SkillUIState()
    object Loading : SkillUIState()
    object SkillNotFound: SkillUIState()
    data class Success(val skill: List<SkillResponse>) : SkillUIState()
    data class Error(val code: Int) : SkillUIState()
}


