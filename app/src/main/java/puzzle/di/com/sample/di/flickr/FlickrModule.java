package puzzle.di.com.sample.di.flickr;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;
import puzzle.di.com.sample.model.manager.HttpErrorManager;
import puzzle.di.com.sample.ui.GameActivity;
import puzzle.di.com.sample.ui.ImageGridAdapter;
import puzzle.di.com.sample.di.ActivityScope;
import puzzle.di.com.sample.model.manager.FlickManager;
import puzzle.di.com.sample.presenter.puzzle.PuzzlesPresenter;
import puzzle.di.com.sample.util.Constants;

@Module public class FlickrModule {

  private final GameActivity gameActivity;

  public FlickrModule(GameActivity gameActivity) {
    this.gameActivity = gameActivity;
  }

  @ActivityScope @Provides PuzzlesPresenter providePuzzlesPresenter(FlickManager flickManager, HttpErrorManager httpErrorManager) {
    return new PuzzlesPresenter(gameActivity, flickManager, httpErrorManager);
  }

  @ActivityScope @Provides ImageGridAdapter provideImagesAdapter() {
    return new ImageGridAdapter();
  }

  @ActivityScope @Provides LinearLayoutManager provideLinearLayoutManager() {
    return new LinearLayoutManager(gameActivity, LinearLayoutManager.VERTICAL, false);
  }

  @ActivityScope @Provides GridLayoutManager provideGridLayoutManager() {
    return new GridLayoutManager(gameActivity, Constants.GRID_COLS, LinearLayoutManager.VERTICAL, false);
  }

}
