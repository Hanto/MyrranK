package com.myrran.zmain

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.myrran.model.skills.book.SkillBook
import com.myrran.model.skills.skills.buffSkill.BuffSkillName
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotId
import com.myrran.model.skills.skills.buffSkill.BuffSkillSlotName
import com.myrran.model.skills.skills.skill.SkillName
import com.myrran.model.skills.skills.subskill.SubSkillName
import com.myrran.model.skills.skills.subskill.SubSkillSlotId
import com.myrran.model.skills.skills.subskill.SubSkillSlotName
import com.myrran.model.skills.stat.BonusPerUpgrade
import com.myrran.model.skills.stat.StatBonus
import com.myrran.model.skills.stat.StatId
import com.myrran.model.skills.stat.StatName
import com.myrran.model.skills.stat.UpgradeCost
import com.myrran.model.skills.stat.Upgrades
import com.myrran.model.skills.templates.Lock
import com.myrran.model.skills.templates.LockTypes
import com.myrran.model.skills.templates.skills.BuffSkillSlotTemplate
import com.myrran.model.skills.templates.skills.BuffSkillTemplate
import com.myrran.model.skills.templates.skills.BuffSkillTemplateId
import com.myrran.model.skills.templates.skills.SkillTemplate
import com.myrran.model.skills.templates.skills.SkillTemplateId
import com.myrran.model.skills.templates.skills.SubSkillSlotTemplate
import com.myrran.model.skills.templates.skills.SubSkillTemplate
import com.myrran.model.skills.templates.skills.SubSkillTemplateId
import com.myrran.model.skills.templates.stat.StatUpgradeableTemplate
import com.myrran.model.spells.buffs.BuffType
import com.myrran.model.spells.spell.SkillType
import com.myrran.model.spells.subspells.SubSkillType

class Pim {

    fun pam() {

        val objectMapper = ObjectMapper()
            .registerModule(KotlinModule.Builder().build())

        val fireBolt = SkillTemplate(
            id = SkillTemplateId("FIREBOLT_1"),
            type = SkillType.BOLT,
            name = SkillName("Fire bolt"),
            stats = listOf(
                StatUpgradeableTemplate(
                    id = StatId("SPEED"),
                    name = StatName("speed"),
                    baseBonus = StatBonus(10.0f),
                    maximum = Upgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                ),
                StatUpgradeableTemplate(
                    id = StatId("COOLDOWN"),
                    name = StatName("cooldown"),
                    baseBonus = StatBonus(10.0f),
                    maximum = Upgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                )
            ),
            slots = listOf(
                SubSkillSlotTemplate(
                    id = SubSkillSlotId("IMPACT"),
                    name = SubSkillSlotName("impact"),
                    lock = Lock(listOf(
                        LockTypes.ALPHA,
                        LockTypes.BETA)
                    )
                )
            )
        )

        val explosion = SubSkillTemplate(
            id = SubSkillTemplateId("EXPLOSION_1"),
            type = SubSkillType.EXPLOSION,
            name = SubSkillName("Explosion"),
            stats = listOf(
                StatUpgradeableTemplate(
                    id = StatId("SPEED"),
                    name = StatName("speed"),
                    baseBonus = StatBonus(10.0f),
                    maximum = Upgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                ),
            ),
            slots = listOf(
                BuffSkillSlotTemplate(
                    id = BuffSkillSlotId("DEBUF1"),
                    name = BuffSkillSlotName("debuff 1"),
                    lock = Lock(listOf(
                        LockTypes.GAMMA,
                        LockTypes.EPSILON)
                    )
                ),
            ),
            keys = listOf(
                LockTypes.ALPHA,
                LockTypes.BETA
            )
        )

        val fireDot = BuffSkillTemplate(
            id = BuffSkillTemplateId("FIRE_1"),
            type = BuffType.FIRE,
            name = BuffSkillName("Fire"),
            stats = listOf(
                StatUpgradeableTemplate(
                    id = StatId("DAMAGE"),
                    name = StatName("damage per second"),
                    baseBonus = StatBonus(10.0f),
                    maximum = Upgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f))
            ),
            keys = listOf(LockTypes.GAMMA)
        )

        val skillBook = SkillBook(
            skillTemplates = listOf(fireBolt),
            subSkillTemplates = listOf(explosion),
            buffSkillTemplates = listOf(fireDot)
        )

        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(skillBook)
        val skillBookRead = objectMapper.readValue(json, SkillBook::class.java)

        println(json)
    }
}
