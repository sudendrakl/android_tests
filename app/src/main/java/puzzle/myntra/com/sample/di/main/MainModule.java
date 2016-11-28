package puzzle.myntra.com.sample.di.main;

import dagger.Module;
import dagger.Provides;
import puzzle.myntra.com.sample.di.ActivityScope;
import puzzle.myntra.com.sample.model.manager.HttpErrorManager;
import puzzle.myntra.com.sample.presenter.main.MainPresenter;
import puzzle.myntra.com.sample.ui.MainActivity;

@Module public class MainModule {

  private final MainActivity mainActivity;

  public MainModule(MainActivity mainActivity) {
    this.mainActivity = mainActivity;
  }

  @ActivityScope @Provides MainPresenter provideMainPresenter(HttpErrorManager httpErrorManager) {
    return new MainPresenter(mainActivity, httpErrorManager);
  }

}
