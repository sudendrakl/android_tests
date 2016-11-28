package puzzle.myntra.com.sample.di.flickr;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;
import puzzle.myntra.com.sample.model.manager.HttpErrorManager;
import puzzle.myntra.com.sample.ui.GameActivity;
import puzzle.myntra.com.sample.ui.ImageGridAdapter;
import puzzle.myntra.com.sample.di.ActivityScope;
import puzzle.myntra.com.sample.model.manager.FlickManager;
import puzzle.myntra.com.sample.presenter.puzzle.PuzzlesPresenter;
import puzzle.myntra.com.sample.util.Constants;

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
