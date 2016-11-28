package puzzle.myntra.com.sample.presenter.main;

import java.util.List;
import puzzle.myntra.com.sample.base.BaseView;
import puzzle.myntra.com.sample.model.entity.PhotoEntity;

public interface MainView extends BaseView {

  void setupView();

  void showProgress();

  void hideProgress();

  void hideError();

  void startGame();

}
