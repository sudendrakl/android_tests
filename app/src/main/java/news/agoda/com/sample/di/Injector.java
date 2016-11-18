package news.agoda.com.sample.di;

import android.app.Application;
import news.agoda.com.sample.MainActivity;

public interface Injector {
  void init(Application application);

  void inject(MainActivity mainActivity);
}
