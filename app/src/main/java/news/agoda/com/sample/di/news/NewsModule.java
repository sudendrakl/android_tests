package news.agoda.com.sample.di.news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import news.agoda.com.sample.MainActivity;
import news.agoda.com.sample.NewsListAdapter;
import news.agoda.com.sample.di.ActivityScope;
import news.agoda.com.sample.model.manager.NewsManager;
import news.agoda.com.sample.presenter.news.NewsPresenter;

@Module public class NewsModule {

  private final MainActivity mainActivity;

  public NewsModule(MainActivity mainActivity) {
    this.mainActivity = mainActivity;
  }

  @ActivityScope @Provides NewsPresenter provideNewsPresenter(NewsManager newsManager) {
    return new NewsPresenter(mainActivity, newsManager);
  }

  @ActivityScope @Provides NewsListAdapter provideContactsAdapter() {
    return new NewsListAdapter();
  }

  @ActivityScope @Provides LinearLayoutManager provideLinearLayoutManager() {
    return new LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false);
  }
}
