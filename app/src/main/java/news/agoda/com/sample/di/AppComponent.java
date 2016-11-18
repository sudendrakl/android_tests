package news.agoda.com.sample.di;

import dagger.Component;
import javax.inject.Singleton;
import news.agoda.com.sample.model.manager.NewsManager;

@Singleton @Component(modules = { AppModule.class }) public interface AppComponent {
  NewsManager provideNewsManager();
}
