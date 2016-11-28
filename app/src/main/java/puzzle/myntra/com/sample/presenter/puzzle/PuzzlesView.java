package puzzle.myntra.com.sample.presenter.puzzle;

import java.util.List;
import puzzle.myntra.com.sample.base.BaseView;
import puzzle.myntra.com.sample.model.entity.PhotoEntity;

public interface PuzzlesView extends BaseView {

  List<PhotoEntity> getImagesList();

  void showImagesList(List<PhotoEntity> contacts);

  void setupView();

  void showProgress();

  void showNoImagesAvailableError();

  void hideImagesList();

  void hideProgress();

  void hideError();

  boolean isImagesListAvailable();

  void showHttpError(int errorBody);

  void updateTimer(int i);

  void reset();

  void flip();

  void nextHint(PhotoEntity photoEntity, int size);

  void congrats();

  void awesome();

  void poorChoice();
}
