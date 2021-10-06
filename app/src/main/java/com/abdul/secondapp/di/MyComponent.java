package com.abdul.secondapp.di;

import com.abdul.secondapp.DaggerActivity;
import com.abdul.secondapp.MainActivity;

import javax.inject.Singleton;
import dagger.Component;

/**
 * broker or agent maps the producer with consumer
 */
@Singleton
@Component(modules = {SharedPrefModule.class})
public interface MyComponent {
    void inject(DaggerActivity activity);
}