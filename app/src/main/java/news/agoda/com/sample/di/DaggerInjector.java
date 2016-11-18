package news.agoda.com.sample.di;

import android.app.Application;
import news.agoda.com.sample.MainActivity;
import news.agoda.com.sample.NewsApplication;
import news.agoda.com.sample.di.news.DaggerNewsComponent;
import news.agoda.com.sample.di.news.NewsModule;

public class DaggerInjector implements Injector {
  private AppComponent appComponent;

  @Override public void init(Application application) {
    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule((NewsApplication) application))
        .build();
  }

  @Override public void inject(MainActivity mainActivity) {
    DaggerNewsComponent.builder()
        .appComponent(appComponent)
        .newsModule(new NewsModule(mainActivity))
        .build()
        .inject(mainActivity);
  }
}
