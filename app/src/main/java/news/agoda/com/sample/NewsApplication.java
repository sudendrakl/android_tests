package news.agoda.com.sample;

import android.app.Application;
import news.agoda.com.sample.di.DaggerInjector;
import news.agoda.com.sample.di.Injector;

public class NewsApplication extends Application {

  private Injector daggerInjector;

  @Override public void onCreate() {
    super.onCreate();
    initDagger();
  }

  public void initDagger() {
    getDaggerInjector().init(this);
  }

  public Injector getDaggerInjector() {
    if (daggerInjector == null) {
      daggerInjector = new DaggerInjector();
    }
    return daggerInjector;
  }
}
