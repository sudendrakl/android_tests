package puzzle.myntra.com.sample.di.flickr;

import dagger.Component;
import puzzle.myntra.com.sample.ui.MainActivity;
import puzzle.myntra.com.sample.di.ActivityScope;
import puzzle.myntra.com.sample.di.AppComponent;

@ActivityScope
@Component(modules = { FlickrModule.class }, dependencies = { AppComponent.class })
public interface FlickrComponent {
  void inject(MainActivity mainActivity);
}
