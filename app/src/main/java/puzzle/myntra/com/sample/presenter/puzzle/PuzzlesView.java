package puzzle.myntra.com.sample.presenter.puzzle;

import java.util.List;
import puzzle.myntra.com.sample.base.BaseView;
import puzzle.myntra.com.sample.model.entity.PhotoEntity;

public interface PuzzlesView extends BaseView {
  void showNewsList(List<PhotoEntity> contacts);

  void setupNewsView();

  void showProgress();

  void showNoNewsAvailableError();

  void hideNewsList();

  void hideProgress();

  void hideError();

  boolean isNewsListAvailable();

  void showNetworkError();

  void showHttpError(int errorBody);

}
