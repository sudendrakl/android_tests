package puzzle.myntra.com.sample.presenter.puzzle;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import java.util.List;
import puzzle.myntra.com.sample.base.BasePresenter;
import puzzle.myntra.com.sample.model.entity.PhotoEntity;
import puzzle.myntra.com.sample.model.entity.PhotoListEntity;
import puzzle.myntra.com.sample.model.manager.FlickManager;
import puzzle.myntra.com.sample.model.manager.HttpErrorManager;
import rx.Subscription;

public class PuzzlesPresenter extends BasePresenter {

  public static String TAG = "PuzzlesPresenter";
  private final String SAVE_PAGE_STATE = "SAVE_PAGE_STATE";
  private final PuzzlesView view;
  private final FlickManager flickManager;

  @VisibleForTesting PageState pageState;

  enum PageState {
    PROGRESS,
    SHOWING_NEWS,
    ERROR_NO_NEWS,
    ERROR_NO_NETWORK,
    ERROR_SOMETHING_WENT_WRONG
  }

  public PageState getPageState() {
    return pageState;
  }

  public void setPageState(PageState pageState) {
    this.pageState = pageState;
  }

  public PuzzlesPresenter(PuzzlesView view, FlickManager flickManager) {
    super(view);
    this.view = view;
    this.flickManager = flickManager;
  }

  @Override public void onViewCreated(boolean isNewLaunch) {
    view.setupNewsView();
    setPageState(PageState.SHOWING_NEWS);

    if (isNewLaunch) {
      showProgress();

      Subscription subscription = flickManager.getApiNewsObservable()
          .subscribe(this::onNewsListLoaded, this::handleNewsLoadingErrors);
      addSubscription(subscription);
    }
  }

  private void onNewsListLoaded(PhotoListEntity photoListEntity) {
    if (photoListEntity == null || photoListEntity.getPhotoEntities() == null
        || photoListEntity.getPhotoEntities().isEmpty()) {
      showNoNewsAvailableError();
      return;
    }

    showNewsList(photoListEntity.getPhotoEntities());
  }

  private void handleNewsLoadingErrors(Throwable e) {
    // Handle general API errors
    boolean isErrorHandled = HttpErrorManager.handleErrors(view, e);
    setPageState(PageState.ERROR_SOMETHING_WENT_WRONG);

    if (isErrorHandled) {
      view.hideProgress();
      if (!view.isNewsListAvailable()) {
        showNoNewsAvailableError();
      }
      return;
    }
    // Unhandled errors
    throw new IllegalStateException(e);
  }

  private void showProgress() {
    setPageState(PageState.PROGRESS);
    view.showProgress();
    view.hideNewsList();
    view.hideError();
  }

  private void showNewsList(List<PhotoEntity> newsEntities) {
    setPageState(PageState.SHOWING_NEWS);
    view.showNewsList(newsEntities);
    view.hideError();
    view.hideProgress();
  }

  private void showNoNewsAvailableError() {
    setPageState(PageState.ERROR_NO_NEWS);
    view.showNoNewsAvailableError();
    view.hideProgress();
    view.hideNewsList();
  }

  public void onSaveState(Bundle outState) {
    Log.d(TAG, "onSaveState" + getPageState().name());
    outState.putString(SAVE_PAGE_STATE, getPageState().name());
  }

  public void onRestoreState(Bundle savedInstanceState) {
    if (savedInstanceState.get(SAVE_PAGE_STATE) != null) {
      setPageState(PageState.valueOf((String) savedInstanceState.get(SAVE_PAGE_STATE)));
      Log.d(TAG, "onRestoreState" + getPageState().name());
    }

    switch (getPageState()) {
      case PROGRESS:
        showProgress();
        break;
      case ERROR_NO_NEWS:
        showNoNewsAvailableError();
        break;
      case SHOWING_NEWS:
        break;
    }
  }
}
