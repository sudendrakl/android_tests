package puzzle.di.com.sample.model.manager;

import android.net.ConnectivityManager;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import puzzle.di.com.sample.base.BaseView;
import puzzle.di.com.sample.model.exceptions.HttpException;

public class HttpErrorManager {

  private ConnectivityManager connectivityManager;

  public HttpErrorManager(ConnectivityManager connectivityManager) {
    this.connectivityManager = connectivityManager;
  }

  public boolean handleErrors(BaseView view, Throwable e) {
    boolean connected = connectivityManager.getActiveNetworkInfo() != null
        && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    view.showNetworkError(connected);

    if (!connected) {
      return true;
    }

    if (e == null) {
      return false;
    }
    if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
      view.showNetworkError(true);
      return true;
    }

    if (e instanceof HttpException) {
      view.showHttpError(((HttpException) e).getErrorCode());
      return true;
    }
    return false;
  }
}