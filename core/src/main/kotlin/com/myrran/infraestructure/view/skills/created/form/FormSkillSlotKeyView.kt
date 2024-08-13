package com.myrran.infraestructure.view.skills.created.form

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Color.GRAY
import com.badlogic.gdx.graphics.Color.ORANGE
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.Align
import com.myrran.domain.skills.created.form.FormSkill
import com.myrran.domain.skills.created.form.FormSkillSlot
import com.myrran.domain.skills.created.form.FormSkillSlotContent.NoFormSkill
import com.myrran.domain.skills.lock.LockType
import com.myrran.infraestructure.assets.PURPLE_LIGHT
import com.myrran.infraestructure.controller.skills.FormSkillController
import com.myrran.infraestructure.view.skills.SkillViewAssets
import com.myrran.infraestructure.view.ui.TextView
import com.myrran.infraestructure.view.ui.UIClickListener
import com.myrran.infraestructure.view.ui.UIClickListener.Button.RIGHT_BUTTON

@Suppress("DuplicatedCode")
class FormSkillSlotKeyView(

    private val model: FormSkillSlot,
    private val assets: SkillViewAssets,
    private val controller: FormSkillController,

): Table()
{
    private var runesLabel: TextView<String> = TextView(model.toName(), assets.font14, model.toColor(), 1f)
    private var keys: List<TextView<String>> = createKeys()

    // LAYOUT:
    //--------------------------------------------------------------------------------------------------------

    init {

        touchable = Touchable.enabled
        addListener(UIClickListener(RIGHT_BUTTON) { controller.removeFormSkill() })
        top().center()
        setBackground(assets.tableBackground)
        rebuildTable()
    }

    private fun rebuildTable() {

        clearChildren()
        val runesRow = Table()
        keys.forEach{ runesRow.add(it) }
        add(runesLabel.align(Align.center)).padLeft(1f).padTop(-3f).row()
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

        keys = createKeys()
        runesLabel = createRunesLabel()
        rebuildTable()
    }

    // HELPER:
    //--------------------------------------------------------------------------------------------------------

    private fun createRunesLabel(): TextView<String> =

        TextView(model.toName(), assets.font14, model.toColor(), 1f)

    private fun createKeys() =

        model.lock.openedBy
            .map { TextView(it.value, assets.font14, it.toColor(), 1f) }

    private fun FormSkillSlot.toName(): String =

        when (val formSkill = model.content) {

            is NoFormSkill -> this.name.value
            is FormSkill -> formSkill.name.value
        }

    private fun FormSkillSlot.toColor(): Color =

        when (model.content) {

            is NoFormSkill -> GRAY
            is FormSkill -> ORANGE
        }

    private fun LockType.toColor(): Color =

        when (val formSkill = model.content) {

            is NoFormSkill -> GRAY
            is FormSkill -> when (formSkill.keys.contains(this)) {
                true -> PURPLE_LIGHT
                false -> GRAY
            }
        }
}
