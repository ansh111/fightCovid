package com.fight.covid.dagger;

import com.fight.covid.ui.FightCovidFragment;
import com.fight.covid.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;


@Component(modules = {AppModule.class, UtilsModule.class})
@Singleton
public interface AppComponent {

    void doInjection(MainActivity mainActivity);
    void doInjection(FightCovidFragment fightCovidFragment);

}
