package com.myrran.view.ui.skills.created.buff

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.GRAY
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.controller.BuffSKillController
import com.myrran.domain.skills.created.BuffSkill
import com.myrran.domain.skills.created.BuffSkillSlotContent.NoBuffSkill
import com.myrran.domain.skills.created.buff.BuffSkillSlot
import com.myrran.domain.skills.lock.LockType
import com.myrran.view.ui.misc.TextView
import com.myrran.view.ui.misc.UIClickListener
import com.myrran.view.ui.misc.UIClickListener.Button.RIGHT_BUTTON
import com.myrran.view.ui.skills.assets.PURPLE_LIGHT
import com.myrran.view.ui.skills.assets.SkillViewAssets

@Suppress("DuplicatedCode")
class BuffSlotKeyView(

    private val model: BuffSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: BuffSKillController

): Table()
{
    private val runesLabel: TextView<String> = TextView(model.toName(), assets.font12, model.toColor(), 1f)
    private var keys: List<TextView<String>> = getKeys()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        touchable = Touchable.enabled
        addListener(UIClickListener(RIGHT_BUTTON) { controller.removeBuffSkill() })
        top().center()
        setBackground(assets.tableBackgroundLight)
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
            .map { TextView(it.value, assets.font12, it.toColor(), 1f) }

    private fun BuffSkillSlot.toName(): String =

        when (val buffSkill = model.content) {

            is NoBuffSkill -> this.name.value
            is BuffSkill -> buffSkill.name.value
        }

    private fun BuffSkillSlot.toColor(): Color =

        when (model.content) {

            is NoBuffSkill -> GRAY
            is BuffSkill -> ORANGE
        }

    private fun LockType.toColor(): Color =

        when (val buffSkill = model.content)
        {
            is NoBuffSkill -> GRAY
            is BuffSkill -> when (buffSkill.keys.contains(this)) {
                true -> PURPLE_LIGHT
                false -> GRAY
            }
        }
}
