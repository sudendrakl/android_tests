package puzzle.myntra.com.sample.di;

import android.app.Application;
import puzzle.myntra.com.sample.di.main.DaggerMainComponent;
import puzzle.myntra.com.sample.di.main.MainModule;
import puzzle.myntra.com.sample.ui.GameActivity;
import puzzle.myntra.com.sample.PuzzleApplication;
import puzzle.myntra.com.sample.di.flickr.DaggerFlickrComponent;
import puzzle.myntra.com.sample.di.flickr.FlickrModule;
import puzzle.myntra.com.sample.ui.MainActivity;

public class DaggerInjector implements Injector {
  private AppComponent appComponent;

  @Override public void init(Application application) {
    appComponent = DaggerAppComponent.builder()
        .appModule(new AppModule((PuzzleApplication) application))
        .build();
  }

  @Override public void inject(GameActivity gameActivity) {
    DaggerFlickrComponent.builder()
        .appComponent(appComponent)
        .flickrModule(new FlickrModule(gameActivity))
        .build()
        .inject(gameActivity);
  }

  @Override public void inject(MainActivity mainActivity) {
    DaggerMainComponent.builder()
        .appComponent(appComponent)
        .mainModule(new MainModule(mainActivity))
        .build()
        .inject(mainActivity);
  }
}