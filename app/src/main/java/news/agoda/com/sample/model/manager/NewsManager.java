package news.agoda.com.sample.model.manager;

import android.net.ConnectivityManager;
import android.support.annotation.NonNull;
import news.agoda.com.sample.model.NewsApi;
import news.agoda.com.sample.model.entity.NewsListEntity;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewsManager {

  public static String TAG = "NewsManager";
  private final NewsApi newsApi;

  public NewsManager(NewsApi newsApi) {
    this.newsApi = newsApi;
  }

  @NonNull public Observable<NewsListEntity> getApiNewsObservable() {
    return newsApi.getNews()
        .map(new ValidateHTTPResponse<>())
        .compose(observable ->
            observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread(), true)
        );
  }
}
