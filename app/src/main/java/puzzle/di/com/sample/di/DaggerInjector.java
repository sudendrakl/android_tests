package puzzle.di.com.sample.di;

import android.app.Application;
import puzzle.di.com.sample.di.main.DaggerMainComponent;
import puzzle.di.com.sample.di.main.MainModule;
import puzzle.di.com.sample.ui.GameActivity;
import puzzle.di.com.sample.PuzzleApplication;
import puzzle.di.com.sample.di.flickr.DaggerFlickrComponent;
import puzzle.di.com.sample.di.flickr.FlickrModule;
import puzzle.di.com.sample.ui.MainActivity;

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