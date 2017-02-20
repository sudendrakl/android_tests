package puzzle.di.com.sample.di.main;

import dagger.Component;
import puzzle.di.com.sample.di.ActivityScope;
import puzzle.di.com.sample.di.AppComponent;
import puzzle.di.com.sample.ui.MainActivity;

@ActivityScope
@Component(modules = { MainModule.class }, dependencies = { AppComponent.class })
public interface MainComponent {
  void inject(MainActivity mainActivity);
}
