package puzzle.di.com.sample.presenter.main;

import puzzle.di.com.sample.base.BaseView;

public interface MainView extends BaseView {

  void setupView();

  void showProgress();

  void hideProgress();

  void hideError();

  void startGame();

}
