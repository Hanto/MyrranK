package com.myrran.infraestructure.view.mobs.common

import com.badlogic.gdx.scenes.scene2d.ui.Container
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.myrran.domain.entities.common.EntityId
import com.myrran.domain.entities.common.effectable.Effectable
import com.myrran.infraestructure.view.mobs.spells.SpellViewAssets

data class EffectsView(

    val model: Effectable,
    val assets: SpellViewAssets,

): Container<Table>()
{
    private val rootTable = Table().top().left()
    private var effects: MutableMap<EntityId, EffectView>

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        super.top().left()
        effects = createEffects()

        actor = rootTable
        setSize(prefWidth, prefHeight)
    }

    private fun rebuildTable() {

        rootTable.clearChildren()
        effects.values.forEach { rootTable.add(it).expandX().left().row() }
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update() {

        effects = createEffects()
        rebuildTable()
    }

    fun update(formId: EntityId) {

        effects[formId]?.update()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createEffects(): MutableMap<EntityId, EffectView> =

        model.retrieveEffects().associateBy ({ it.id }, { EffectView(it, assets) }).toMutableMap()
}
