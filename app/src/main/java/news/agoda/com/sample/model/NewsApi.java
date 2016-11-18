package news.agoda.com.sample.model;

import news.agoda.com.sample.model.entity.NewsListEntity;
import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

public interface NewsApi {

  //"http://www.mocky.io/v2/573c89f31100004a1daa8adb"
  @GET("v2/573c89f31100004a1daa8adb") Observable<Response<NewsListEntity>> getNews();
}
