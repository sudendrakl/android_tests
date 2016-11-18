package news.agoda.com.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.drawee.backends.pipeline.Fresco;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import news.agoda.com.sample.base.BaseActivity;
import news.agoda.com.sample.di.Injector;
import news.agoda.com.sample.model.entity.NewsEntity;
import news.agoda.com.sample.presenter.news.NewsPresenter;
import news.agoda.com.sample.presenter.news.NewsView;
import news.agoda.com.sample.util.Constants;

public class MainActivity extends BaseActivity<NewsPresenter> implements NewsView {

  private static final String TAG = MainActivity.class.getSimpleName();

  @Inject NewsPresenter newsPresenter;
  @Inject NewsListAdapter newsListAdapter;
  @Inject LinearLayoutManager linearLayoutManager;

  @Bind(R.id.recycler_view) RecyclerView recyclerView;
  @Bind(R.id.no_news) View noNewsView;
  @Bind(R.id.error_text_view) TextView errorTextView;

  private static final String SAVE_RECYCLER_VIEW_STATE = "save_recycler_view_state";

  @Override public void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_main);
    Fresco.initialize(this);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);
  }

  @Override protected void onStart() {
    super.onStart();
    IntentFilter filter = new IntentFilter(Constants.BroadcastEvents.NewsItemListClick);
    LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
  }

  @Override protected void inject(Injector appComponent) {
    appComponent.inject(this);
  }

  @NonNull @Override public NewsPresenter getPresenter() {
    return newsPresenter;
  }

  @Override public void showNewsList(List<NewsEntity> newsEntities) {
    newsListAdapter.setList(newsEntities);
    newsListAdapter.notifyDataSetChanged();
  }

  @Override public void setupNewsView() {
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setAdapter(newsListAdapter);
  }

  @Override public void showProgress() {
    showProgressDialog("Loading...");
  }

  @Override public void showNoNewsAvailableError() {
    recyclerView.setVisibility(View.GONE);
    noNewsView.setVisibility(View.VISIBLE);
    errorTextView.setText("No news available, please check again later :-(");
  }

  @Override public void hideNewsList() {

  }

  @Override public void hideProgress() {
    hideProgressDialog();
  }

  @Override public void hideError() {
    recyclerView.setVisibility(View.VISIBLE);
    noNewsView.setVisibility(View.GONE);
  }

  @Override public boolean isNewsListAvailable() {
    return newsListAdapter.getList().size() > 0;
  }

  @Override public void showNetworkError() {
    recyclerView.setVisibility(View.GONE);
    noNewsView.setVisibility(View.VISIBLE);
    errorTextView.setText("Please check n/w");
  }

  @Override public void showHttpError(int errorBody) {
    recyclerView.setVisibility(View.GONE);
    noNewsView.setVisibility(View.VISIBLE);
    errorTextView.setText("Something went wrong, please try again later");
  }


  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    newsPresenter.onSaveState(outState);
    if (newsListAdapter != null) {
      ArrayList<NewsEntity> newsEntities = newsListAdapter.getList();
      if (newsEntities != null && !newsEntities.isEmpty()) {
        outState.putParcelableArrayList(SAVE_RECYCLER_VIEW_STATE, newsEntities);
      }
    }
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    newsPresenter.onRestoreState(savedInstanceState);
    ArrayList<NewsEntity> newsEntities =
        savedInstanceState.getParcelableArrayList(SAVE_RECYCLER_VIEW_STATE);
    if (newsEntities != null && !newsEntities.isEmpty() && newsListAdapter != null) {
      newsListAdapter.setList(newsEntities);
    }
  }

  @Override protected void onStop() {
    super.onStop();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
  }

  BroadcastReceiver receiver = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      int listItem = intent.getIntExtra(Constants.IntentExtras.NewsListItem, -1);
      NewsEntity newsEntity = newsListAdapter.getList().get(listItem);

      Intent detailIntent = new Intent(MainActivity.this, DetailViewActivity.class);
      detailIntent.putExtra(Constants.IntentExtras.NewsListItem, newsEntity);
      startActivity(detailIntent);
    }
  };
}
