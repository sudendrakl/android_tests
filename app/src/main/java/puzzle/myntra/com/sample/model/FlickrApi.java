package puzzle.myntra.com.sample.model;

import puzzle.myntra.com.sample.model.entity.PhotoListEntity;
import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

public interface FlickrApi {

  //"http://www.mocky.io/v2/573c89f31100004a1daa8adb"
  //https://api.flickr.com/services/feeds/photos_public.gne?format=json&tags=cat,cute&tagmode=all&nojsoncallback=1
  @GET("v2/573c89f31100004a1daa8adb") Observable<Response<PhotoListEntity>> getNews();
}
