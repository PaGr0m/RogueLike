package ru.itmo.roguelike.ioc;

import com.google.inject.AbstractModule;
import ru.itmo.roguelike.input.InputHandler;

import javax.inject.Qualifier;
import java.awt.event.KeyListener;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class IOModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(KeyListener.class)
                .annotatedWith(DefaultInputHandler.class)
                .to(InputHandler.class);
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.PARAMETER})
    public @interface DefaultInputHandler {
    }
}
