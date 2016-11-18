package news.agoda.com.sample.di;

import android.content.Context;
import android.net.ConnectivityManager;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import news.agoda.com.sample.NewsApplication;
import news.agoda.com.sample.model.NewsApi;
import news.agoda.com.sample.model.manager.NewsManager;
import news.agoda.com.sample.util.GsonConverterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

@Module public class AppModule {

  private final NewsApplication application;
  private static final String BASE_API_URL = "http://www.mocky.io/";

  public AppModule(NewsApplication application) {
    this.application = application;
  }

  @Provides @Singleton public NewsApi provideNewsApiService(Retrofit retrofit) {
    return retrofit.create(NewsApi.class);
  }

  @Provides @Singleton public Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
    return new Retrofit.Builder().baseUrl(BASE_API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();
  }

  @Provides @Singleton
  public OkHttpClient provideOkHttpClient(HttpLoggingInterceptor httpLoggingInterceptor) {
    return new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
  }

  @Provides @Singleton public HttpLoggingInterceptor provideInterceptor() {
    return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
  }

  @Provides @Singleton public Gson provideGson() {
    return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .create();
  }

  @Provides @Singleton public ConnectivityManager provideConnectivityManager() {
    return (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);
  }

  @Provides @Singleton public NewsManager provideNewsManager(NewsApi newsApi) {
    return new NewsManager(newsApi);
  }
}
