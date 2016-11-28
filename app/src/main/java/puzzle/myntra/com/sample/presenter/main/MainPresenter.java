package puzzle.myntra.com.sample.presenter.main;

import puzzle.myntra.com.sample.base.BasePresenter;
import puzzle.myntra.com.sample.model.manager.HttpErrorManager;

public class MainPresenter extends BasePresenter {

  public static String TAG = "MainPresenter";
  private final MainView view;
  private HttpErrorManager httpErrorManager;

  public MainPresenter(MainView view, HttpErrorManager httpErrorManager) {
    super(view);
    this.view = view;
    this.httpErrorManager = httpErrorManager;
  }

  @Override public void onViewCreated(boolean isNewLaunch) {
    view.setupView();
    httpErrorManager.handleErrors(view, null);
  }

  private void showProgress() {
    view.showProgress();
    view.hideError();
  }

  private void hideProgress() {
    view.hideProgress();
  }

  public void handleNetworkStateChange() {
    httpErrorManager.handleErrors(view, null);
  }
}
