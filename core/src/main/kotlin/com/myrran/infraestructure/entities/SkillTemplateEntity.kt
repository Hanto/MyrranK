package com.myrran.infraestructure.entities

import com.myrran.domain.skills.templates.LockTypes
import com.myrran.domain.spells.buff.BuffType
import com.myrran.domain.spells.spell.SkillType
import com.myrran.domain.spells.subspell.SubSkillType

data class SkillTemplateEntity(
    val id: String,
    val type: SkillType,
    val name: String,
    val stats: List<StatTemplateEntity>,
    val slots: List<SlotTemplateEntity>
)

data class SubSkillTemplateEntity(
    val id: String,
    val type: SubSkillType,
    val name: String,
    val stats: List<StatTemplateEntity>,
    val slots: List<SlotTemplateEntity>,
    val keys: List<LockTypes>
)

data class BuffSKillTemplateEntity(
    val id: String,
    val type: BuffType,
    val name: String,
    val stats: List<StatTemplateEntity>,
    val keys: Collection<LockTypes>
)

data class SlotTemplateEntity(
    val id: String,
    val name: String,
    val lock: List<LockTypes>
)

data class StatTemplateEntity(
    val id: String,
    val name: String,
    val baseBonus: Float,
    val maximum: Int? = null,
    val upgradeCost: Float? = null,
    val bonusPerUpgrade: Float? = null
)
