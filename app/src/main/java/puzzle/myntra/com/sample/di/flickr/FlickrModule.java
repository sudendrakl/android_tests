package puzzle.myntra.com.sample.di.flickr;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;
import puzzle.myntra.com.sample.ui.ImageGridAdapter;
import puzzle.myntra.com.sample.ui.MainActivity;
import puzzle.myntra.com.sample.di.ActivityScope;
import puzzle.myntra.com.sample.model.manager.FlickManager;
import puzzle.myntra.com.sample.presenter.puzzle.PuzzlesPresenter;
import puzzle.myntra.com.sample.util.Constants;

@Module public class FlickrModule {

  private final MainActivity mainActivity;

  public FlickrModule(MainActivity mainActivity) {
    this.mainActivity = mainActivity;
  }

  @ActivityScope @Provides PuzzlesPresenter provideNewsPresenter(FlickManager flickManager) {
    return new PuzzlesPresenter(mainActivity, flickManager);
  }

  @ActivityScope @Provides ImageGridAdapter provideImagesAdapter() {
    return new ImageGridAdapter();
  }

  @ActivityScope @Provides LinearLayoutManager provideLinearLayoutManager() {
    return new LinearLayoutManager(mainActivity, LinearLayoutManager.VERTICAL, false);
  }

  @ActivityScope @Provides GridLayoutManager provideGridLayoutManager() {
    return new GridLayoutManager(mainActivity, Constants.GRID_COLS, LinearLayoutManager.VERTICAL, false);
  }
}
