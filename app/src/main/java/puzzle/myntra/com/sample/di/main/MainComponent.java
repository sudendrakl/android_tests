package puzzle.myntra.com.sample.di.main;

import dagger.Component;
import puzzle.myntra.com.sample.di.ActivityScope;
import puzzle.myntra.com.sample.di.AppComponent;
import puzzle.myntra.com.sample.ui.GameActivity;
import puzzle.myntra.com.sample.ui.MainActivity;

@ActivityScope
@Component(modules = { MainModule.class }, dependencies = { AppComponent.class })
public interface MainComponent {
  void inject(MainActivity mainActivity);
}
