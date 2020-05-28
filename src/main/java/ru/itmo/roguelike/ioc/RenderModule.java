package ru.itmo.roguelike.ioc;

import com.google.inject.AbstractModule;
import ru.itmo.roguelike.render.JexerRenderEngine;
import ru.itmo.roguelike.render.RenderEngine;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class RenderModule extends AbstractModule {
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.PARAMETER})
    public @interface Jexer {
    }

    @Override
    protected void configure() {
        bind(RenderEngine.class)
                .annotatedWith(Jexer.class)
                .to(JexerRenderEngine.class);
    }
}
