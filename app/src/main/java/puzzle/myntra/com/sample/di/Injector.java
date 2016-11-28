package puzzle.myntra.com.sample.di;

import android.app.Application;
import puzzle.myntra.com.sample.ui.GameActivity;
import puzzle.myntra.com.sample.ui.MainActivity;

public interface Injector {
  void init(Application application);

  void inject(GameActivity gameActivity);

  void inject(MainActivity mainActivity);
}
