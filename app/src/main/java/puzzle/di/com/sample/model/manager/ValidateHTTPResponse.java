package puzzle.di.com.sample.model.manager;

import android.net.ConnectivityManager;
import puzzle.di.com.sample.model.exceptions.HttpException;
import retrofit2.Response;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

class ValidateHTTPResponse<T> implements Func1<Response<T>, T> {

  private ConnectivityManager connectivityManager;
  ValidateHTTPResponse(ConnectivityManager connectivityManager) {
    this.connectivityManager = connectivityManager;
  }

  @Override public T call(Response<T> response) {
    //check common api error's....401,404....
    if(!connectivityManager.getActiveNetworkInfo().isConnected())
      throw Exceptions.propagate(new HttpException(-1, null));

    if (response.isSuccessful()) {
      return response.body();
    }

    throw Exceptions.propagate(new HttpException(response.code(), response.errorBody()));
  }


}