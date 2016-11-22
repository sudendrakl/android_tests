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

  @NonNull public Observable<PhotoListEntity> getApiNewsObservable() {
    // format=json&tags=cat,cute&tagmode=all&nojsoncallback=1
    return flickrApi.getNews("json", "cat,cute", "all", 1)
        .map(new ValidateHTTPResponse<>(connectivityManager))
        .compose(observable -> observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread(), true));
  }
}
