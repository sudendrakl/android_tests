package news.agoda.com.sample.di.news;

import dagger.Component;
import news.agoda.com.sample.MainActivity;
import news.agoda.com.sample.di.ActivityScope;
import news.agoda.com.sample.di.AppComponent;

@ActivityScope
@Component(modules = { NewsModule.class }, dependencies = { AppComponent.class })
public interface NewsComponent {
  void inject(MainActivity mainActivity);
}
