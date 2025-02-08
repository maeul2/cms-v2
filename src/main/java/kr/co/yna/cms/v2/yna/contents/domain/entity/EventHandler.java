package kr.co.yna.cms.v2.yna.contents.domain.entity;

import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class EventHandler {
    private final List<AbstractEvent> events;

    protected EventHandler() {
        this.events = new ArrayList<>();
    }

    public void apply(AbstractEvent event) {
        apply(event, true);
    }

    public void apply(AbstractEvent event, boolean isNew) {
        Assert.notNull(event, "event must not be null");

        Method handler;

        try {
            handler = this.getClass().getDeclaredMethod("handle", event.getClass());
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException(e);
        }

        try {
            handler.invoke(this, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new EventHandlingException(e);
        }

        if (isNew) {
            this.events.add(event);
        }
    }

    public List<AbstractEvent> getEventList() {
        return new ArrayList<>(this.events);
    }
}
