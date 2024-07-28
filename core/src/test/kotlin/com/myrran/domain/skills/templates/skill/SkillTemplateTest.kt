package com.myrran.domain.skills.templates.skill

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.myrran.domain.skills.book.PlayerSkillBook
import com.myrran.domain.skills.book.WorldSkillBook
import com.myrran.domain.skills.skills.buff.BuffSkillName
import com.myrran.domain.skills.skills.buff.BuffSkillSlotId
import com.myrran.domain.skills.skills.buff.BuffSkillSlotName
import com.myrran.domain.skills.skills.skill.SkillName
import com.myrran.domain.skills.skills.stat.BonusPerUpgrade
import com.myrran.domain.skills.skills.stat.StatBonus
import com.myrran.domain.skills.skills.stat.StatId
import com.myrran.domain.skills.skills.stat.StatName
import com.myrran.domain.skills.skills.stat.UpgradeCost
import com.myrran.domain.skills.skills.stat.Upgrades
import com.myrran.domain.skills.skills.subskill.SubSkillName
import com.myrran.domain.skills.skills.subskill.SubSkillSlotId
import com.myrran.domain.skills.skills.subskill.SubSkillSlotName
import com.myrran.domain.skills.templates.Lock
import com.myrran.domain.skills.templates.LockTypes
import com.myrran.domain.skills.templates.buff.BuffSkillSlotTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplate
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.stat.StatUpgradeableTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillSlotTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillTemplate
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.domain.spells.buff.BuffType
import com.myrran.domain.spells.spell.SkillType
import com.myrran.domain.spells.subspell.SubSkillType
import com.myrran.infraestructure.PlayerSkillBookEntity
import com.myrran.infraestructure.SkillAdapter
import com.myrran.infraestructure.SkillBookAdapter
import com.myrran.infraestructure.SkillTemplateAdapter
import com.myrran.utils.QuantityMap
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SkillTemplateTest {


    @Test
    fun pim() {

        val bolt = SkillTemplate(
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
                    id = StatId("Cooldown"),
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
                    lock = Lock(listOf(LockTypes.ALPHA, LockTypes.BETA))
                ),
                SubSkillSlotTemplate(
                    id = SubSkillSlotId("TRAIL"),
                    name = SubSkillSlotName("trail"),
                    lock = Lock(listOf(LockTypes.ALPHA, LockTypes.BETA))
                )
            )
        )

        val explosion = SubSkillTemplate(
            id = SubSkillTemplateId("EXPLOSION_1"),
            type = SubSkillType.EXPLOSION,
            name = SubSkillName("Explosion"),
            stats = listOf(
                StatUpgradeableTemplate(
                    id = StatId("RADIUS"),
                    name = StatName("radius"),
                    baseBonus = StatBonus(10.0f),
                    maximum = Upgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                )
            ),
            slots = listOf(
                BuffSkillSlotTemplate(
                    id = BuffSkillSlotId("DEBUFF_1"),
                    name = BuffSkillSlotName("Debuff 1"),
                    lock = Lock(listOf(LockTypes.GAMMA, LockTypes.EPSILON)),
                ),
                BuffSkillSlotTemplate(
                    id = BuffSkillSlotId("DEBUFF_2"),
                    name = BuffSkillSlotName("Debuff 2"),
                    lock = Lock(listOf(LockTypes.GAMMA, LockTypes.EPSILON)),
                )
            ),
            keys = listOf(LockTypes.ALPHA)
        )

        val fire = BuffSkillTemplate(
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
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                ),
                StatUpgradeableTemplate(
                    id = StatId("DURATION"),
                    name = StatName("duration"),
                    baseBonus = StatBonus(10.0f),
                    maximum = Upgrades(20),
                    upgradeCost = UpgradeCost(2.0f),
                    bonusPerUpgrade = BonusPerUpgrade(1.0f)
                )
            ),
            keys = listOf(LockTypes.GAMMA)
        )

        val worldSkill = WorldSkillBook(
            listOf(bolt).associateBy { it.id },
            listOf(explosion).associateBy { it.id },
            listOf(fire).associateBy { it.id },
        )

        val skillBook = PlayerSkillBook(
            QuantityMap(),
            QuantityMap(),
            QuantityMap(),
            mutableMapOf()
        )

        skillBook.learn(bolt.id)
        skillBook.learn(bolt.id)
        skillBook.learn(explosion.id)
        skillBook.learn(explosion.id)
        skillBook.learn(fire.id)
        skillBook.learn(fire.id)


        val boltSkill = worldSkill.createSkill(SkillTemplateId("FIREBOLT_1"))
        val explosionSkill = worldSkill.createSubSkill(SubSkillTemplateId("EXPLOSION_1"))
        val fireSkill = worldSkill.createBuffSkill(BuffSkillTemplateId("FIRE_1"))

        skillBook.addSkill(boltSkill)
        skillBook.addSubSkillTo(boltSkill.id, SubSkillSlotId("IMPACT"), explosionSkill)
        skillBook.addBuffSKillTo(boltSkill.id, SubSkillSlotId("IMPACT") ,BuffSkillSlotId("DEBUFF_1"), fireSkill)

        boltSkill.upgrade(StatId("SPEED"), Upgrades(10))
        boltSkill.upgrade(SubSkillSlotId("IMPACT"), StatId("RADIUS"), Upgrades(10))
        boltSkill.upgrade(SubSkillSlotId("IMPACT"), BuffSkillSlotId("DEBUFF_1"), StatId("DAMAGE"), Upgrades(10))

        val skillAdapter = SkillAdapter()
        val skillTemplateAdapter = SkillTemplateAdapter()
        val skillBookAdapter = SkillBookAdapter(skillAdapter, skillTemplateAdapter)

        val objectMapper = ObjectMapper()
            .registerModule(KotlinModule.Builder().build())

        val entity = skillBookAdapter.fromDomain(skillBook)
        val json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(entity)

        println(json)

        val jsonObject = objectMapper.readValue(json, PlayerSkillBookEntity::class.java)
        val domain = skillBookAdapter.toDomain(jsonObject)

        assertThat(domain).usingRecursiveComparison().isEqualTo(skillBook)
        assertThat(boltSkill.totalCost()).isEqualTo(UpgradeCost(60.0f))
        assertThat(domain).usingRecursiveComparison().isEqualTo(domain.copy())

        objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(skillBookAdapter.fromDomain(worldSkill)).also { println(it) }
    }
}
