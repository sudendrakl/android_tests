package puzzle.di.com.sample.base;

public interface BaseView {
  void showNetworkError(boolean connected);

  void showHttpError(int errorCode);
}
