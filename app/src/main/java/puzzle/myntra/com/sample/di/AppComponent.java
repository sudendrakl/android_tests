package puzzle.myntra.com.sample.di;

import dagger.Component;
import javax.inject.Singleton;
import puzzle.myntra.com.sample.model.manager.FlickManager;
import puzzle.myntra.com.sample.model.manager.HttpErrorManager;

@Singleton @Component(modules = { AppModule.class }) public interface AppComponent {
  FlickManager provideFlickrManager();
  HttpErrorManager provideHttpErrorManager();
}
