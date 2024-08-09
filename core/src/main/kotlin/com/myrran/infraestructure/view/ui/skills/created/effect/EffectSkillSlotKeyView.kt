package com.myrran.infraestructure.view.ui.skills.created.effect

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.GRAY
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.skills.created.effect.EffectSkill
import com.myrran.domain.skills.created.effect.EffectSkillSlot
import com.myrran.domain.skills.created.effect.EffectSkillSlotContent.NoEffectSkill
import com.myrran.domain.skills.lock.LockType
import com.myrran.infraestructure.assets.PURPLE_LIGHT
import com.myrran.infraestructure.controller.EffectSKillController
import com.myrran.infraestructure.view.ui.misc.TextView
import com.myrran.infraestructure.view.ui.misc.UIClickListener
import com.myrran.infraestructure.view.ui.misc.UIClickListener.Button.RIGHT_BUTTON
import com.myrran.infraestructure.view.ui.skills.SkillViewAssets

@Suppress("DuplicatedCode")
class EffectSkillSlotKeyView(

    private val model: EffectSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: EffectSKillController

): Table()
{
    private val runesLabel: TextView<String> = TextView(model.toName(), assets.font14, model.toColor(), 1f)
    private var keys: List<TextView<String>> = getKeys()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        touchable = Touchable.enabled
        addListener(UIClickListener(RIGHT_BUTTON) { controller.removeEffectSkill() })
        top().center()
        setBackground(assets.tableBackground)
        rebuildTable()
    }

    private fun rebuildTable() {

        clearChildren()
        val runesRow = Table()
        keys.forEach{ runesRow.add(it) }
        add(runesLabel.align(Align.center)).left().padLeft(1f).padTop(-3f).row()
        add(runesRow).padLeft(1f).padTop(-6f).padBottom(-1f)
    }

    // DRAG AND DROP NOTIFICATIONS
    //--------------------------------------------------------------------------------------------------------

    fun dontHighlight() {

        runesLabel.setTextColor(model.toColor())
        runesLabel.clearActions()
        runesLabel.addAction(Actions.fadeIn(0.4f))
    }

    fun highlightWithColor(color: Color) {

        runesLabel.setTextColor(color)
        runesLabel.addAction(Actions.forever(Actions.sequence(
            Actions.fadeOut(0.2f, Interpolation.circleIn),
            Actions.fadeIn(1.0f, Interpolation.circleOut)
        )))
    }

    // UPDATE:
    //--------------------------------------------------------------------------------------------------------

    fun update() {

        runesLabel.setText(model.toName())
        runesLabel.setTextColor(model.toColor())
        keys = getKeys()
        rebuildTable()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun getKeys() =

        model.lock.openedBy
            .map { TextView(it.value, assets.font14, it.toColor(), 1f) }

    private fun EffectSkillSlot.toName(): String =

        when (val effectSkill = model.content) {

            is NoEffectSkill -> this.name.value
            is EffectSkill -> effectSkill.name.value
        }

    private fun EffectSkillSlot.toColor(): Color =

        when (model.content) {

            is NoEffectSkill -> GRAY
            is EffectSkill -> ORANGE
        }

    private fun LockType.toColor(): Color =

        when (val effectSkill = model.content)
        {
            is NoEffectSkill -> GRAY
            is EffectSkill -> when (effectSkill.keys.contains(this)) {
                true -> PURPLE_LIGHT
                false -> GRAY
            }
        }
}
