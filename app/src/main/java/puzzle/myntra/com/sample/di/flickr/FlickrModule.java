package puzzle.myntra.com.sample.di.flickr;

import android.support.v7.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;
import puzzle.myntra.com.sample.MainActivity;
import puzzle.myntra.com.sample.NewsListAdapter;
import puzzle.myntra.com.sample.di.ActivityScope;
import puzzle.myntra.com.sample.model.manager.FlickManager;
import puzzle.myntra.com.sample.presenter.news.NewsPresenter;

@Module public class FlickrModule {

  private final MainActivity mainActivity;

  public FlickrModule(MainActivity mainActivity) {
    this.mainActivity = mainActivity;
  }

  @ActivityScope @Provides NewsPresenter provideNewsPresenter(FlickManager flickManager) {
    return new NewsPresenter(mainActivity, flickManager);
  }

  @ActivityScope @Provides NewsListAdapter provideContactsAdapter() {
    return new NewsListAdapter();
  }

  @ActivityScope @Provides LinearLayoutManager provideLinearLayoutManager() {
    return new LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false);
  }
}
