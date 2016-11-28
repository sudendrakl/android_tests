package puzzle.myntra.com.sample.model.manager;

import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import puzzle.myntra.com.sample.model.FlickrApi;
import puzzle.myntra.com.sample.model.entity.PhotoListEntity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FlickManager {

  public static String TAG = "FlickManager";
  private final FlickrApi flickrApi;
  private ConnectivityManager connectivityManager;

  public FlickManager(FlickrApi flickrApi, ConnectivityManager connectivityManager) {
    this.flickrApi = flickrApi;
    this.connectivityManager = connectivityManager;
  }

  private String TAGS1[] = { "cat", "kitty", "purr" };
  private String TAGS2[] = { "cute", "sleep", "evil" };
  private String TAG_MODE[] = { "all", "any" };

  @NonNull public Observable<PhotoListEntity> getApiFlickrImagesObservable() {
    // format=json&tags=cat,cute&tagmode=all&nojsoncallback=1
    return flickrApi.getImagesFeed("json", getMeTags(), getMeTagMode(), 1)
        .map(new ValidateHTTPResponse<>(connectivityManager))
        .compose(observable -> observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread(), true));
  }

  private String getMeTags() {
    return String.format("%s,%s,%s,%s", TAGS1[Math.abs((int) System.nanoTime() % TAGS1.length)],
        TAGS1[Math.abs((int) System.nanoTime() % TAGS1.length)],
        TAGS2[Math.abs((int) System.nanoTime() % TAGS2.length)],
        TAGS2[Math.abs((int) System.nanoTime() % TAGS2.length)]);
  }

  private String getMeTagMode() {
    return TAG_MODE[(int) (System.currentTimeMillis() % 2)];
  }
}
