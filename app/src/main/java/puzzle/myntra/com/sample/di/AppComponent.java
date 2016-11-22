package puzzle.myntra.com.sample.di;

import dagger.Component;
import javax.inject.Singleton;
import puzzle.myntra.com.sample.model.manager.NewsManager;

@Singleton @Component(modules = { AppModule.class }) public interface AppComponent {
  NewsManager provideNewsManager();
}
