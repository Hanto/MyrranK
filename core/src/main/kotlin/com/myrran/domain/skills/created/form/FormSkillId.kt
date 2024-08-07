package com.myrran.domain.skills.created.form

import java.util.UUID

data class FormSkillId(
    val value: UUID
)
{
    companion object {

        @JvmStatic
        fun new(): FormSkillId = FormSkillId(UUID.randomUUID())
    }
}
