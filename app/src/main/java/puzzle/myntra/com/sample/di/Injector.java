package puzzle.myntra.com.sample.di;

import android.app.Application;
import puzzle.myntra.com.sample.MainActivity;

public interface Injector {
  void init(Application application);

  void inject(MainActivity mainActivity);
}
