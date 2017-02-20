package puzzle.di.com.sample.di;

import android.app.Application;
import puzzle.di.com.sample.ui.GameActivity;
import puzzle.di.com.sample.ui.MainActivity;

public interface Injector {
  void init(Application application);

  void inject(GameActivity gameActivity);

  void inject(MainActivity mainActivity);
}
