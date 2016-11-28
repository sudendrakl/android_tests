package puzzle.myntra.com.sample.model;

import puzzle.myntra.com.sample.model.entity.PhotoListEntity;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface FlickrApi {

  //https://api.flickr.com/services/feeds/photos_public.gne?format=json&tags=cat,cute&tagmode=all&nojsoncallback=1
  @GET("services/feeds/photos_public.gne") Observable<Response<PhotoListEntity>> getImagesFeed(
                                                                                    @Query("format") String format,
                                                                                    @Query("tags") String tags,
                                                                                    @Query("tagmode") String tagmode,
                                                                                    @Query("nojsoncallback") int nojsoncallback
                                                                                );


}
