package com.myrran.badlogic;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;

/** Manages drag and drop operations through registered drag sources and drop targets.
 * @author Nathan Sweet */

public class DaD<SOURCE extends DaDSource, TARGET extends DaDTarget> {

    static final Vector2 tmpVector = new Vector2();
    Payload payload;
    Actor dragActor;
    TARGET target;
    boolean isValidTarget;
    Array<TARGET> targets = new Array<>();
    ObjectMap<SOURCE, DragListener> sourceListeners = new ObjectMap<>();
    private float tapSquareSize = 8;
    private int button;
    float dragActorX = 0, dragActorY = 0;
    float touchOffsetX, touchOffsetY;
    long dragStartTime;
    int dragTime = 250;
    int activePointer = -1;
    boolean cancelTouchFocus = true;
    boolean keepWithinStage = true;

    public void addSource (final SOURCE source) {

        DragListener listener = new DragListener() {

            public void dragStart (InputEvent event, float x, float y, int pointer) {
                if (activePointer != -1) {
                    event.stop();
                    return;
                }

                activePointer = pointer;

                dragStartTime = System.currentTimeMillis();
                payload = source.dragStart(event, getTouchDownX(), getTouchDownY(), pointer);
                event.stop();

                if (cancelTouchFocus && payload != null) source.getActor().getStage().cancelTouchFocusExcept(this, source.getActor());

                targets.forEach(o -> o.notifyNewPayload(payload));
            }

            public void drag (InputEvent event, float x, float y, int pointer) {
                if (payload == null) return;
                if (pointer != activePointer) return;

                Stage stage = event.getStage();

                if (dragActor != null) {
                    dragActor.remove(); // Remove so it cannot be hit (Touchable.disabled isn't enough).
                    dragActor = null;
                }

                // Find target.
                TARGET newTarget = null;
                isValidTarget = false;
                float stageX = event.getStageX() + touchOffsetX, stageY = event.getStageY() + touchOffsetY;
                Actor hit = event.getStage().hit(stageX, stageY, true); // Prefer touchable actors.
                if (hit == null) hit = event.getStage().hit(stageX, stageY, false);
                if (hit != null) {
                    for (int i = 0, n = targets.size; i < n; i++) {
                        TARGET target = targets.get(i);
                        if (!target.getActor().isAscendantOf(hit)) continue;
                        newTarget = target;
                        target.getActor().stageToLocalCoordinates(tmpVector.set(stageX, stageY));
                        break;
                    }
                }
                // If over a new target, notify the former target that it's being left behind.
                if (newTarget != target) {
                    if (target != null) target.reset(source, payload);
                    target = newTarget;
                }
                // Notify new target of drag.
                if (newTarget != null) isValidTarget = newTarget.drag(source, payload, tmpVector.x, tmpVector.y, pointer);

                // Add and position the drag actor.
                Actor actor = null;
                if (target != null) actor = isValidTarget ? payload.validDragActor : payload.invalidDragActor;
                if (actor == null) actor = payload.dragActor;
                dragActor = actor;
                if (actor == null) return;
                stage.addActor(actor);
                float actorX = event.getStageX() - actor.getWidth()/2 + dragActorX;
                float actorY = event.getStageY() + dragActorY;
                if (keepWithinStage) {
                    if (actorX < 0) actorX = 0;
                    if (actorY < 0) actorY = 0;
                    if (actorX + actor.getWidth() > stage.getWidth()) actorX = stage.getWidth() - actor.getWidth();
                    if (actorY + actor.getHeight() > stage.getHeight()) actorY = stage.getHeight() - actor.getHeight();
                }
                actor.setPosition(actorX, actorY);
            }

            public void dragStop (InputEvent event, float x, float y, int pointer) {
                if (pointer != activePointer) return;
                activePointer = -1;
                if (payload == null) return;

                if (System.currentTimeMillis() - dragStartTime < dragTime) isValidTarget = false;
                if (dragActor != null) dragActor.remove();
                if (isValidTarget) {
                    float stageX = event.getStageX() + touchOffsetX, stageY = event.getStageY() + touchOffsetY;
                    target.getActor().stageToLocalCoordinates(tmpVector.set(stageX, stageY));
                    target.drop(source, payload, tmpVector.x, tmpVector.y, pointer);
                }
                source.dragStop(event, x, y, pointer, payload, isValidTarget ? target : null);
                if (target != null) target.reset(source, payload);
                payload = null;
                target = null;
                isValidTarget = false;
                dragActor = null;

                targets.forEach(DaDTarget::notifyNoPayload);
            }
        };
        listener.setTapSquareSize(tapSquareSize);
        listener.setButton(button);
        source.getActor().addCaptureListener(listener);
        sourceListeners.put(source, listener);
    }

    public void removeSource (SOURCE source) {
        DragListener dragListener = sourceListeners.remove(source);
        source.getActor().removeCaptureListener(dragListener);
    }

    public void addTarget (TARGET target) {
        targets.add(target);
    }

    public void removeTarget (TARGET target) {
        targets.removeValue(target, true);
    }

    /** Removes all targets and sources. */
    public void clear () {
        targets.clear();
        for (Entry<SOURCE, DragListener> entry : sourceListeners.entries())
            entry.key.getActor().removeCaptureListener(entry.value);
        sourceListeners.clear();
    }

    /** Sets the distance a touch must travel before being considered a drag. */
    public void setTapSquareSize (float halfTapSquareSize) {
        tapSquareSize = halfTapSquareSize;
    }

    /** Sets the button to listen for, all other buttons are ignored. Default is {@link Buttons#LEFT}. Use -1 for any button. */
    public void setButton (int button) {
        this.button = button;
    }

    public void setDragActorPosition (float dragActorX, float dragActorY) {
        this.dragActorX = dragActorX;
        this.dragActorY = dragActorY;
    }

    /** Sets an offset in stage coordinates from the touch position which is used to determine the drop location. Default is
     * 0,0. */
    public void setTouchOffset (float touchOffsetX, float touchOffsetY) {
        this.touchOffsetX = touchOffsetX;
        this.touchOffsetY = touchOffsetY;
    }

    public boolean isDragging () {
        return payload != null;
    }

    /** Returns the current drag actor, or null. */
    public Actor getDragActor () {
        return dragActor;
    }

    /** Returns the current drag payload, or null. */
    public Payload getDragPayload () {
        return payload;
    }

    /** Time in milliseconds that a drag must take before a drop will be considered valid. This ignores an accidental drag and drop
     * that was meant to be a click. Default is 250. */
    public void setDragTime (int dragMillis) {
        this.dragTime = dragMillis;
    }

    /** When true (default), the {@link Stage#cancelTouchFocus()} touch focus} is cancelled if
     * {@link DragAndDrop.Source#dragStart(InputEvent, float, float, int) dragStart} returns non-null. This ensures the DragAndDrop is the only
     * touch focus listener, eg when the source is inside a {@link ScrollPane} with flick scroll enabled. */
    public void setCancelTouchFocus (boolean cancelTouchFocus) {
        this.cancelTouchFocus = cancelTouchFocus;
    }

    public void setKeepWithinStage (boolean keepWithinStage) {
        this.keepWithinStage = keepWithinStage;
    }

}
