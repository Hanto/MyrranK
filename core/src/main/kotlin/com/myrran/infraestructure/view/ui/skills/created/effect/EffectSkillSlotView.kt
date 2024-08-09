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
import com.myrran.infraestructure.view.ui.skills.SkillViewFactory
import com.myrran.infraestructure.view.ui.skills.SkillViewId
import com.myrran.infraestructure.view.ui.skills.created.stat.StatsView

class EffectSkillSlotView(

    override val id: SkillViewId,
    val model: EffectSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: EffectSKillController,
    private val factory: SkillViewFactory

): Table(), Identifiable<SkillViewId>
{
    private var statsView: StatsView = createStatsView()
    val keyView: EffectSkillSlotKeyView = EffectSkillSlotKeyView(model, assets, controller)

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        left()

        rebuildTable()
    }

    private fun rebuildTable() {

        clearChildren()

        // effect:
        add(keyView).expandY().fillY()
        if (model.content is EffectSkill)
            add(statsView)
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun updateStat(statId: StatId) {

        statsView.update(statId)
    }

    fun updateEffect() {

        keyView.update()
        statsView = createStatsView()
        rebuildTable()
    }

    fun dispose() {

        factory.disposeView(id)
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createStatsView(): StatsView =

        StatsView( { getStatsView() }, assets, controller )

    private fun getStatsView(): Collection<Stat> =

        when (val effectSkill = model.content) {

            is NoEffectSkill -> emptyList()
            is EffectSkill -> effectSkill.stats.getStats()
        }
}
