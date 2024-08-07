package com.myrran.infraestructure.view.ui.skills.created.effect

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.effect.EffectSkillSlot
import com.myrran.domain.skills.created.effect.EffectSkillSlotContent.NoEffectSkill
import com.myrran.domain.skills.created.stat.Stat
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.infraestructure.assets.SkillViewAssets
import com.myrran.infraestructure.controller.EffectSKillController
import com.myrran.infraestructure.view.ui.skills.SkillViewId
import com.myrran.infraestructure.view.ui.skills.created.stat.StatsView

class EffectSkillSlotView(

    override val id: SkillViewId,
    val model: EffectSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: EffectSKillController

): Table(), Identifiable<SkillViewId>
{
    val effectSkillSlotKeyView: EffectSkillSlotKeyView = EffectSkillSlotKeyView(model, assets, controller)
    private var stats: StatsView = createStatsView()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        left()
        rebuildTable()
    }

    private fun rebuildTable() {

        clearChildren()

        add(effectSkillSlotKeyView).expandY().fillY()
        if (model.content is EffectSkill)
            add(stats)
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update(statId: StatId) {

        stats.update(statId)
    }

    fun update() {

        effectSkillSlotKeyView.update()
        stats = createStatsView()
        rebuildTable()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createStatsView(): StatsView =

        StatsView( { getStats() }, assets, controller )

    private fun getStats(): Collection<Stat> =

        when (val effectSkill = model.content) {

            is NoEffectSkill -> emptyList()
            is EffectSkill -> effectSkill.stats.getStats()
        }
}
