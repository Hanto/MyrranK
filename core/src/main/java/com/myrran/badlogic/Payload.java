package com.myrran.badlogic;

import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * The payload of a drag and drop operation. Actors can be optionally provided to follow the cursor and change when over a
 * target. Such Actors will be added and removed from the stage automatically during the drag operation. Care should be taken
 * when using the source Actor as a payload drag actor.
 */
public class Payload {
    Actor dragActor, validDragActor, invalidDragActor;
    Object object;

    public void setDragActor(Actor dragActor) {
        this.dragActor = dragActor;
    }

    public Actor getDragActor() {
        return dragActor;
    }

    public void setValidDragActor(Actor validDragActor) {
        this.validDragActor = validDragActor;
    }

    public Actor getValidDragActor() {
        return validDragActor;
    }

    public void setInvalidDragActor(Actor invalidDragActor) {
        this.invalidDragActor = invalidDragActor;
    }

    public Actor getInvalidDragActor() {
        return invalidDragActor;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
