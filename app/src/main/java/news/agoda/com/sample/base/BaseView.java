package news.agoda.com.sample.base;

public interface BaseView {
  void showNetworkError();

  void showHttpError(int errorCode);
}
