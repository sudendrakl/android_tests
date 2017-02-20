package puzzle.di.com.sample.di.flickr;

import dagger.Component;
import puzzle.di.com.sample.ui.GameActivity;
import puzzle.di.com.sample.di.ActivityScope;
import puzzle.di.com.sample.di.AppComponent;

@ActivityScope
@Component(modules = { FlickrModule.class }, dependencies = { AppComponent.class })
public interface FlickrComponent {
  void inject(GameActivity gameActivity);
}
