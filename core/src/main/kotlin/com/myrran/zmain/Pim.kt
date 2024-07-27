package com.myrran.zmain

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.myrran.domain.skills.book.QuantityMap
import com.myrran.domain.skills.book.SkillBook
import com.myrran.domain.skills.skills.buffskill.BuffSkillName
import com.myrran.domain.skills.skills.buffskill.BuffSkillSlotId
import com.myrran.domain.skills.skills.buffskill.BuffSkillSlotName
import com.myrran.domain.skills.skills.skill.SkillName
import com.myrran.domain.skills.skills.subskill.SubSkillName
import com.myrran.domain.skills.skills.subskill.SubSkillSlotId
import com.myrran.domain.skills.skills.subskill.SubSkillSlotName
import com.myrran.domain.skills.stat.BonusPerUpgrade
import com.myrran.domain.skills.stat.StatBonus
import com.myrran.domain.skills.stat.StatId
import com.myrran.domain.skills.stat.StatName
import com.myrran.domain.skills.stat.UpgradeCost
import com.myrran.domain.skills.stat.Upgrades
import com.myrran.domain.skills.templates.Lock
import com.myrran.domain.skills.templates.LockTypes
import com.myrran.domain.skills.templates.skills.BuffSkillSlotTemplate
import com.myrran.domain.skills.templates.skills.BuffSkillTemplate
import com.myrran.domain.skills.templates.skills.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skills.SkillTemplate
import com.myrran.domain.skills.templates.skills.SkillTemplateId
import com.myrran.domain.skills.templates.skills.SubSkillSlotTemplate
import com.myrran.domain.skills.templates.skills.SubSkillTemplate
import com.myrran.domain.skills.templates.skills.SubSkillTemplateId
import com.myrran.domain.skills.templates.stat.StatUpgradeableTemplate
import com.myrran.domain.spells.buffspell.BuffType
import com.myrran.domain.spells.spell.SkillType
import com.myrran.domain.spells.subspell.SubSkillType

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
            skillTemplates = listOf(fireBolt).associateBy { it.id },
            subSkillTemplates = listOf(explosion).associateBy { it.id },
            buffSkillTemplates = listOf(fireDot).associateBy { it.id },
            QuantityMap(),
            QuantityMap(),
            QuantityMap(),
            mutableMapOf()
        )

        skillBook.learn(fireBolt.id)
        skillBook.learn(fireDot.id)
        skillBook.learn(explosion.id)

        skillBook.createSkill(fireBolt.id)


        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(skillBook)

        println(json)

        val skillBookRead = objectMapper.readValue(json, SkillBook::class.java)
    }
}
