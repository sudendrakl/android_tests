package news.agoda.com.sample.presenter.news;

import java.util.List;
import news.agoda.com.sample.base.BaseView;
import news.agoda.com.sample.model.entity.NewsEntity;

public interface NewsView extends BaseView {
  void showNewsList(List<NewsEntity> contacts);

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
