package puzzle.myntra.com.sample.di;

import android.app.Application;
import puzzle.myntra.com.sample.ui.MainActivity;
import puzzle.myntra.com.sample.NewsApplication;
import puzzle.myntra.com.sample.di.flickr.DaggerFlickrComponent;
import puzzle.myntra.com.sample.di.flickr.FlickrModule;

public class DaggerInjector implements Injector {
  private AppComponent appComponent;

  @Override public void init(Application application) {
    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule((NewsApplication) application))
        .build();
  }

  @Override public void inject(MainActivity mainActivity) {
    DaggerFlickrComponent.builder()
        .appComponent(appComponent)
        .flickrModule(new FlickrModule(mainActivity))
        .build()
        .inject(mainActivity);
  }
}
