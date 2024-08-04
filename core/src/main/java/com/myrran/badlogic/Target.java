package com.myrran.badlogic;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * A target where a payload can be dropped to.
 *
 * @author Nathan Sweet
 */
abstract public class Target {
    final Actor actor;

    public Target(Actor actor) {
        if (actor == null) throw new IllegalArgumentException("actor cannot be null.");
        this.actor = actor;
        Stage stage = actor.getStage();
        if (stage != null && actor == stage.getRoot())
            throw new IllegalArgumentException("The stage root cannot be a drag and drop target.");
    }

    /**
     * Called when the payload is dragged over the target. The coordinates are in the target's local coordinate system.
     *
     * @return true if this is a valid target for the payload.
     */
    abstract public boolean drag(Source source, Payload payload, float x, float y, int pointer);

    /**
     * Called when the payload is no longer over the target, whether because the touch was moved or a drop occurred.
     */
    public void reset(Source source, Payload payload) {
    }

    /**
     * Called when the payload is dropped on the target. The coordinates are in the target's local coordinate system.
     */
    abstract public void drop(Source source, Payload payload, float x, float y, int pointer);

    public Actor getActor() {
        return actor;
    }

    /**
     * Called when a new payload is dragged
     **/
    public abstract void notifyNewPayload(Payload payload);

    /**
     * Called when a payload is dropped anywhere
     **/
    public abstract void notifyNoPayload();
}
