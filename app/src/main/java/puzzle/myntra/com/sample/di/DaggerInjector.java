package puzzle.myntra.com.sample.di;

import android.app.Application;
import puzzle.myntra.com.sample.MainActivity;
import puzzle.myntra.com.sample.NewsApplication;
import puzzle.myntra.com.sample.di.news.DaggerNewsComponent;
import puzzle.myntra.com.sample.di.news.NewsModule;

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
