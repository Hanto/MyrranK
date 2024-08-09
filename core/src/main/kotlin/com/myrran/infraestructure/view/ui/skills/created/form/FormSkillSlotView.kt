package com.myrran.infraestructure.view.ui.skills.created.form

import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.misc.Identifiable
import com.myrran.domain.skills.created.effect.EffectSkillSlotId
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.form.FormSkillSlot
import com.myrran.domain.skills.created.form.FormSkillSlotContent.NoFormSkill
import com.myrran.domain.skills.created.stat.Stat
import com.myrran.domain.skills.created.stat.StatId
import com.myrran.infraestructure.controller.FormSkillController
import com.myrran.infraestructure.view.ui.skills.SkillViewAssets
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
    private var statsView: StatsView = StatsView( { getStatsView() }, assets, controller)
    val keyView: FormSkillSlotKeyView = FormSkillSlotKeyView(model, assets, controller)
    var effectSlotViews: Map<EffectSkillSlotId, EffectSkillSlotView> = createEffectSlotViews()

    private val formTable = Table()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        left()

        setBackground(assets.tableBackground)
        rebuildTable()
    }

    private fun rebuildTable() {

        clearChildren()
        formTable.clearChildren()

        // form:
        formTable.add(keyView).expandY().fillY()
        if (model.content is FormSkill)
            formTable.add(statsView)
        formTable.row()

        // effects:
        add(formTable).row()
        effectSlotViews.values.forEach{ add(it).left().row() }
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun updateStat(statId: StatId) {

        statsView.update(statId)
    }

    fun updateForm() {

        keyView.update()
        statsView = createStatsView()
        effectSlotViews = createEffectSlotViews()
        rebuildTable()
    }

    fun dispose() {

        factory.disposeView(id)
        effectSlotViews.forEach { it.value.dispose() }
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createEffectSlotViews(): Map<EffectSkillSlotId, EffectSkillSlotView> {

        effectSlotViews?.forEach { it.value.dispose() }

        return when (val formSkill = model.content) {

            is NoFormSkill -> emptyMap()
            is FormSkill -> formSkill.getEffectSkillSlots()
                .associate { it.id to factory.createEffectSlotView(it, controller) }
        }
    }

    private fun createStatsView(): StatsView =

        StatsView( { getStatsView() }, assets, controller )

    private fun getStatsView(): Collection<Stat> =

        when (val formSkill = model.content) {

            is NoFormSkill -> emptyList()
            is FormSkill -> formSkill.stats.getStats()
        }
}
