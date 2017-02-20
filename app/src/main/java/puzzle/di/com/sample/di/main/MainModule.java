package puzzle.di.com.sample.di.main;

import dagger.Module;
import dagger.Provides;
import puzzle.di.com.sample.di.ActivityScope;
import puzzle.di.com.sample.model.manager.HttpErrorManager;
import puzzle.di.com.sample.presenter.main.MainPresenter;
import puzzle.di.com.sample.ui.MainActivity;

@Module public class MainModule {

  private final MainActivity mainActivity;

  public MainModule(MainActivity mainActivity) {
    this.mainActivity = mainActivity;
  }

  @ActivityScope @Provides MainPresenter provideMainPresenter(HttpErrorManager httpErrorManager) {
    return new MainPresenter(mainActivity, httpErrorManager);
  }

}
