package puzzle.di.com.sample.di;

import dagger.Component;
import javax.inject.Singleton;
import puzzle.di.com.sample.model.manager.FlickManager;
import puzzle.di.com.sample.model.manager.HttpErrorManager;

@Singleton @Component(modules = { AppModule.class }) public interface AppComponent {
  FlickManager provideFlickrManager();
  HttpErrorManager provideHttpErrorManager();
}
