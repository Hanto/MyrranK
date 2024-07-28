package com.myrran.infraestructure

import com.myrran.domain.skills.book.PlayerSkillBook
import com.myrran.domain.skills.book.WorldSkillBook
import com.myrran.domain.skills.templates.buff.BuffSkillTemplateId
import com.myrran.domain.skills.templates.skill.SkillTemplateId
import com.myrran.domain.skills.templates.subskill.SubSkillTemplateId
import com.myrran.utils.Quantity
import com.myrran.utils.QuantityMap

class SkillBookAdapter(

    private val skillAdapter: SkillAdapter,
    private val skillTemplateAdapter: SkillTemplateAdapter
) {

    fun fromDomain(domain: PlayerSkillBook): PlayerSkillBookEntity =

        PlayerSkillBookEntity(
            learnedSkills = domain.learnedSkills().map { it.toSkillLearnedEntity() },
            learnedSubSKills = domain.learnedSubSKills().map { it.toSubSkillLearnedEntity() },
            learnedBuffSkills = domain.learnedBuffSkills().map { it.toBuffSkillLearnedEntity() },
            createdSkills = domain.createdSkills().map { skillAdapter.fromDomain(it) }
        )

    fun toDomain(entity: PlayerSkillBookEntity): PlayerSkillBook =

        PlayerSkillBook(
            learnedSkills = QuantityMap(entity.learnedSkills.associate { SkillTemplateId(it.id) to Quantity(it.avaiable, it.total) }.toMutableMap()),
            learnedSubSkills = QuantityMap(entity.learnedSubSKills.associate { SubSkillTemplateId(it.id) to Quantity(it.avaiable, it.total) }.toMutableMap()),
            learnedBuffSkills = QuantityMap(entity.learnedBuffSkills.associate { BuffSkillTemplateId(it.id) to Quantity(it.avaiable, it.total) }.toMutableMap()),
            createdSkills = entity.createdSkills.map { skillAdapter.toDomain(it) }.associateBy { it.id }.toMutableMap()
        )

    fun fromFomain(domain: WorldSkillBook): WorldSkillBookEntity =

        WorldSkillBookEntity(
            skillTemplates = domain.skillTemplates().map { skillTemplateAdapter.fromDomain(it) },
            subSkillTemplates = domain.subSkillTemplates().map { skillTemplateAdapter.fromDomain(it) },
            buffSkillTemplates = domain.buffSkillTemplates().map { skillTemplateAdapter.fromDomain(it) },
        )

    fun toDomain(entity: WorldSkillBookEntity): WorldSkillBook =

        WorldSkillBook(
            skillTemplates = entity.skillTemplates.map { skillTemplateAdapter.toDomain(it) }.associateBy { it.id },
            subSkillTemplates = entity.subSkillTemplates.map { skillTemplateAdapter.toDomain(it) }.associateBy { it.id },
            buffSkillTemplates = entity.buffSkillTemplates.map { skillTemplateAdapter.toDomain(it) }.associateBy { it.id },
        )

    private fun MutableMap.MutableEntry<SkillTemplateId, Quantity>.toSkillLearnedEntity(): LearnedEntity =

        LearnedEntity(this.key.value, this.value.available, this.value.total)

    private fun MutableMap.MutableEntry<SubSkillTemplateId, Quantity>.toSubSkillLearnedEntity(): LearnedEntity =

        LearnedEntity(this.key.value, this.value.available, this.value.total)

    private fun MutableMap.MutableEntry<BuffSkillTemplateId, Quantity>.toBuffSkillLearnedEntity(): LearnedEntity =

        LearnedEntity(this.key.value, this.value.available, this.value.total)
}
