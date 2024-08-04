package com.myrran.badlogic;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

/**
 * A source where a payload can be dragged from.
 *
 * @author Nathan Sweet
 */
abstract public class Source {
    final Actor actor;

    public Source(Actor actor) {
        if (actor == null) throw new IllegalArgumentException("actor cannot be null.");
        this.actor = actor;
    }

    /**
     * Called when a drag is started on the source. The coordinates are in the source's local coordinate system.
     *
     * @return If null the drag will not affect any targets.
     */
    abstract public Payload dragStart(InputEvent event, float x, float y, int pointer);

    /**
     * Called when a drag for the source is stopped. The coordinates are in the source's local coordinate system.
     *
     * @param payload null if dragStart returned null.
     * @param target  null if not dropped on a valid target.
     */
    public void dragStop(InputEvent event, float x, float y, int pointer, Payload payload, Target target) {
    }

    public Actor getActor() {
        return actor;
    }
}
