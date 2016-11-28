package puzzle.myntra.com.sample.base;

public interface BaseView {
  void showNetworkError(boolean connected);

  void showHttpError(int errorCode);
}
