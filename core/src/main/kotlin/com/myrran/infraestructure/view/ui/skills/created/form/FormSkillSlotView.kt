package com.myrran.infraestructure.view.ui.skills.created.form

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.skills.created.effect.EffectSkillSlotId
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.form.FormSkillSlot
import com.myrran.domain.skills.created.form.FormSkillSlotContent.NoFormSkill
import com.myrran.domain.skills.created.stat.Stat
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.infraestructure.assets.SkillViewAssets
import com.myrran.infraestructure.controller.FormSkillController
import com.myrran.infraestructure.view.ui.skills.SkillViewFactory
import com.myrran.infraestructure.view.ui.skills.SkillViewId
import com.myrran.infraestructure.view.ui.skills.created.effect.EffectSkillSlotView
import com.myrran.infraestructure.view.ui.skills.created.stat.StatsView

class FormSkillSlotView(

    override val id: SkillViewId,
    private val model: FormSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: FormSkillController,
    private val factory: SkillViewFactory,

): Table(), Identifiable<SkillViewId>
{
    private var stats: StatsView = StatsView( { getStats() }, assets, controller)
    val formSlotKeyView: FormSkillSlotKeyView = FormSkillSlotKeyView(model, assets, controller)
    var effectSlotsViews: Map<EffectSkillSlotId, EffectSkillSlotView> = createEffectSlotViews()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        left()
        setBackground(assets.tableBackground)
        rebuildTable()
    }

    private fun rebuildTable() {

        clearChildren()

        val keyAndStatsTable = Table()
        keyAndStatsTable.add(formSlotKeyView).expandY().fillY()
        if (model.content is FormSkill)
            keyAndStatsTable.add(stats)

        keyAndStatsTable.row()

        add(keyAndStatsTable).row()
        effectSlotsViews.values.forEach{ add(it).left().row() }
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update(statId: StatId) {

        stats.update(statId)
    }

    fun dispose() {

        factory.disposeView(id)
        effectSlotsViews.values.forEach { factory.disposeView(it.id) }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createEffectSlotViews(): Map<EffectSkillSlotId, EffectSkillSlotView> =

        when (val formSkill = model.content) {

            is NoFormSkill -> emptyMap()
            is FormSkill -> formSkill.getEffectSkillSlots()
                .associate { it.id to factory.createEffectSlotView(it, controller) }
        }

    private fun getStats(): Collection<Stat> =

        when (val formSkill = model.content) {

            is NoFormSkill -> emptyList()
            is FormSkill -> formSkill.stats.getStats()
        }
}
