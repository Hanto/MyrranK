package com.myrran.infraestructure

import com.myrran.model.skills.templates.LockTypes
import com.myrran.model.spells.buffs.BuffType
import com.myrran.model.spells.spell.SkillType
import com.myrran.model.spells.subspells.SubSkillType

data class SkillEntity(
    val id: String,
    val templateId: String,
    val type: SkillType,
    val name: String,
    val stats: List<StatEntity>,
    val slots: List<SubSkillSlotEntity>
)

data class SubSkillSlotEntity(
    val id: String,
    val name: String,
    val lock: List<LockTypes>,
    val content: SubSkillEntity?
)

data class SubSkillEntity(
    val id: String,
    val templateId: String,
    val type: SubSkillType,
    val name: String,
    val stats: List<StatEntity>,
    val slots: List<BuffSKillSlotEntity>,
    val keys: Collection<LockTypes>
)

data class BuffSKillSlotEntity(
    val id: String,
    val name: String,
    val lock: List<LockTypes>,
    val content: BuffSkillEntity?
)

data class BuffSkillEntity(
    val id: String,
    val templateId: String,
    val type: BuffType,
    val name: String,
    val stats: List<StatEntity>,
    val keys: List<LockTypes>
)

data class StatEntity(
    val id: String,
    val name: String,
    val baseBonus: Float,
    val actual: Int? = null,
    val maximum: Int? = null,
    val upgradeCost: Float? = null,
    val bonusPerUpgrade: Float? = null,
)
