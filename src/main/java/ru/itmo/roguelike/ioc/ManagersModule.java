package ru.itmo.roguelike.ioc;

import com.google.inject.AbstractModule;
import ru.itmo.roguelike.manager.actormanager.ActorManager;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ManagersModule extends AbstractModule {
    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.FIELD, ElementType.PARAMETER})
    public @interface MobManager {
    }

    @Override
    protected void configure() {
        bind(ActorManager.class)
                .annotatedWith(MobManager.class)
                .to(ru.itmo.roguelike.manager.actormanager.MobManager.class);
    }
}
