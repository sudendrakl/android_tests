package puzzle.myntra.com.sample.presenter.puzzle;

import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import puzzle.myntra.com.sample.base.BasePresenter;
import puzzle.myntra.com.sample.model.entity.PhotoEntity;
import puzzle.myntra.com.sample.model.entity.PhotoListEntity;
import puzzle.myntra.com.sample.model.manager.FlickManager;
import puzzle.myntra.com.sample.model.manager.HttpErrorManager;
import puzzle.myntra.com.sample.util.Constants;
import rx.Subscription;

public class PuzzlesPresenter extends BasePresenter {

  public static String TAG = "MainPresenter";
  private final String SAVE_PAGE_STATE = "SAVE_PAGE_STATE";
  private final String SAVE_HINT_BUCKET = "SAVE_HINT_BUCKET";
  private final String SAVE_HINT_ITEM = "SAVE_HINT_ITEM";
  private final String SAVE_HIT_COUNT = "SAVE_HIT_COUNT";

  private final PuzzlesView view;
  private final FlickManager flickManager;
  private final HttpErrorManager httpErrorManager;
  private TimerTask counterTimerTask;
  private Timer countdownTimer;
  private int gridSize = Constants.GRID_COLS * Constants.GRID_COLS;

  private final int COUNTER_DELAY = 1000; //1s
  private final int INITIAL_DELAY = 500;  //.5s
  private final int COUNTER_DURATION = 15; //15s
  @VisibleForTesting private PageState pageState;

  private ArrayList<PhotoEntity> hintBucket;
  private int hintItem;

  private int hitCount;

  public boolean isGameInProgress() {
    return getPageState() == PageState.SHOWING_IMAGES || getPageState() == PageState.FLIP_IMAGES;
  }

  private enum PageState {
    PROGRESS,
    SHOWING_IMAGES,
    ERROR_NO_IMAGES,
    ERROR_NO_NETWORK,
    FLIP_IMAGES,
    COMPLETE
  }

  private PageState getPageState() {
    return pageState;
  }

  private void setPageState(PageState pageState) {
    this.pageState = pageState;
  }

  public PuzzlesPresenter(PuzzlesView view, FlickManager flickManager, HttpErrorManager httpErrorManager) {
    super(view);
    this.view = view;
    this.flickManager = flickManager;
    this.httpErrorManager = httpErrorManager;
  }

  @Override public void onViewCreated(boolean isNewLaunch) {

    view.setupView();
    setPageState(PageState.SHOWING_IMAGES);

    if (isNewLaunch) {
      showProgress();

      Subscription subscription = flickManager.getApiFlickrImagesObservable()
          .subscribe(this::onImagesListLoaded, this::handleApiLoadingErrors);
      addSubscription(subscription);
    }
    if(countdownTimer != null) {
      countdownTimer.cancel();
      countdownTimer=null;
    }
  }

  @Override public void handleNetworkStateChange() {
    httpErrorManager.handleErrors(view, null);
  }

  private void onImagesListLoaded(PhotoListEntity photoListEntity) {
    if (photoListEntity == null || photoListEntity.getPhotoEntities() == null
        || photoListEntity.getPhotoEntities().isEmpty()) {
      showNoImagesAvailableError();
      return;
    }

    if(photoListEntity.getPhotoEntities().size() > gridSize) {
      showImagesList(photoListEntity.getPhotoEntities().subList(0, gridSize));
    } else if(photoListEntity.getPhotoEntities().size() == gridSize) {
      showImagesList(photoListEntity.getPhotoEntities());
    } else {
      showNoImagesAvailableError();
    }
  }

  private void handleApiLoadingErrors(Throwable e) {
    // Handle general API errors
    boolean isErrorHandled = httpErrorManager.handleErrors(view, e);
    setPageState(PageState.ERROR_NO_NETWORK);

    if (isErrorHandled) {
      view.hideProgress();
      return;
    } else if (!view.isImagesListAvailable()) {
      showNoImagesAvailableError();
    }
    // Unhandled errors
    throw new IllegalStateException(e);
  }

  private void showProgress() {
    setPageState(PageState.PROGRESS);
    view.showProgress();
    view.hideImagesList();
    view.hideError();
  }

  private void showImagesList(List<PhotoEntity> photoEntitiesEntities) {
    setPageState(PageState.SHOWING_IMAGES);
    view.showImagesList(photoEntitiesEntities);
    view.hideError();
    view.hideProgress();
  }

  private void showNoImagesAvailableError() {
    setPageState(PageState.ERROR_NO_IMAGES);
    view.showNoImagesAvailableError();
    view.hideProgress();
    view.hideImagesList();
  }

  private void shuffle() {
    //shuffle the images order & keep original & randomised list
    setPageState(PageState.FLIP_IMAGES);
    List<PhotoEntity> list = view.getImagesList();
    Collections.shuffle(list);
    hintBucket = new ArrayList<>(list);
  }

  private void showNextHint() {
    //get a random image from list & show as hint
    if(hintBucket.size()>0) {
      Random random = new Random();
      hintItem = random.nextInt(hintBucket.size());
      view.nextHint(hintBucket.get(hintItem), hintBucket.size());
    } else {
      setPageState(PageState.COMPLETE);
      view.congrats();
    }
  }

  public void validate(int clickedPosition) {
    if(pageState == PageState.SHOWING_IMAGES || pageState == PageState.COMPLETE) {
      //ignore clicks
      return;
    }
    //compare showing image hint position & clickedPosition
    if(hintBucket.get(hintItem).getMedia().getUrl().equals(view.getImagesList().get(clickedPosition).getMedia().getUrl())) { //success
      hintBucket.remove(hintItem);
      view.awesome();
      showNextHint();
    } else {
      view.poorChoice();
    }
  }

  public void startCountDown() {
    counterTimerTask = new TimerTask() {
      int count = COUNTER_DURATION;
      @Override public void run() {
        view.updateTimer(count--);
        if(count < 0) {
          countdownTimer.cancel();
          //flip images & show a hint random image
          shuffle();
          view.flip();
          showNextHint();
        }
      }
    };
    countdownTimer = new Timer();
    countdownTimer.scheduleAtFixedRate(counterTimerTask, INITIAL_DELAY, COUNTER_DELAY);
  }

  public int incrementHitCount() {
    return ++hitCount;
  }

  public void onSaveState(Bundle outState) {
    Log.d(TAG, "onSaveState" + getPageState().name());
    outState.putString(SAVE_PAGE_STATE, getPageState().name());
    outState.putParcelableArrayList(SAVE_HINT_BUCKET, hintBucket);
    outState.putInt(SAVE_HINT_ITEM, hintItem);
    outState.putInt(SAVE_HIT_COUNT, hitCount);
  }

  public void onRestoreState(Bundle savedInstanceState) {
    if (savedInstanceState.get(SAVE_PAGE_STATE) != null) {
      setPageState(PageState.valueOf((String) savedInstanceState.get(SAVE_PAGE_STATE)));
      Log.d(TAG, "onRestoreState" + getPageState().name());
    }
    if(savedInstanceState.get(SAVE_HINT_BUCKET) != null) {
      hintBucket = savedInstanceState.getParcelableArrayList(SAVE_HINT_BUCKET);
      hintItem = savedInstanceState.getInt(SAVE_HINT_ITEM);
      hitCount = savedInstanceState.getInt(SAVE_HIT_COUNT);
    }

    switch (getPageState()) {
      case PROGRESS:
        showProgress();
        break;
      case ERROR_NO_IMAGES:
        showNoImagesAvailableError();
        break;
      case ERROR_NO_NETWORK:
        showNoImagesAvailableError();
        break;
      case SHOWING_IMAGES:
      break;
    }
  }
}
