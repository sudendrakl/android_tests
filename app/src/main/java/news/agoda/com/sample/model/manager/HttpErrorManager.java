package news.agoda.com.sample.model.manager;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import news.agoda.com.sample.base.BaseView;
import news.agoda.com.sample.model.exceptions.HttpException;

public class HttpErrorManager {

  public static boolean handleErrors(BaseView view, Throwable e) {
    if (e instanceof UnknownHostException || e instanceof SocketTimeoutException) {
      view.showNetworkError();
      return true;
    }

    if (e instanceof HttpException) {
      view.showHttpError(((HttpException) e).getErrorCode());
      return true;
    }
    return false;
  }
}

