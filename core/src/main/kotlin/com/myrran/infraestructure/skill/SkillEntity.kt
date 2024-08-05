package com.myrran.infraestructure.skill

import com.myrran.domain.skills.lock.LockType
import com.myrran.domain.spells.buff.BuffType
import com.myrran.domain.spells.spell.SkillType
import com.myrran.domain.spells.subspell.SubSkillType

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
    val lock: List<LockType>,
    val content: SubSkillEntity?
)

data class SubSkillEntity(
    val id: String,
    val templateId: String,
    val type: SubSkillType,
    val name: String,
    val stats: List<StatEntity>,
    val slots: List<BuffSKillSlotEntity>,
    val keys: Collection<LockType>
)

data class BuffSKillSlotEntity(
    val id: String,
    val name: String,
    val lock: List<LockType>,
    val content: BuffSkillEntity?
)

data class BuffSkillEntity(
    val id: String,
    val templateId: String,
    val type: BuffType,
    val name: String,
    val stats: List<StatEntity>,
    val keys: List<LockType>
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
