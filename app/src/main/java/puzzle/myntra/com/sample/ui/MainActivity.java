package puzzle.myntra.com.sample.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import puzzle.myntra.com.sample.R;
import puzzle.myntra.com.sample.base.BaseActivity;
import puzzle.myntra.com.sample.di.Injector;
import puzzle.myntra.com.sample.model.entity.PhotoEntity;
import puzzle.myntra.com.sample.presenter.puzzle.PuzzlesPresenter;
import puzzle.myntra.com.sample.presenter.puzzle.PuzzlesView;
import puzzle.myntra.com.sample.util.Constants;

public class MainActivity extends BaseActivity<PuzzlesPresenter> implements PuzzlesView {

  private static final String TAG = MainActivity.class.getSimpleName();

  @Inject PuzzlesPresenter puzzlesPresenter;
  @Inject ImageGridAdapter imageGridAdapter;
  // @Inject LinearLayoutManager linearLayoutManager;
  @Inject GridLayoutManager gridLayoutManager;

  @Bind(R.id.recycler_view) RecyclerView recyclerView;
  @Bind(R.id.no_images) View noNewsView;
  @Bind(R.id.error_text_view) TextView errorTextView;
  @Bind(R.id.find_image) SimpleDraweeView findImageView;
  @Bind(R.id.start_button) Button startButton;

  private static final String SAVE_RECYCLER_VIEW_STATE = "save_recycler_view_state";

  @Override public void onCreate(Bundle savedInstanceState) {
    setContentView(puzzle.myntra.com.sample.R.layout.activity_main);
    Fresco.initialize(this);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);
  }

  @Override protected void onStart() {
    super.onStart();
    IntentFilter filter = new IntentFilter(Constants.BroadcastEvents.ItemListClick);
    LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
  }

  @Override protected void inject(Injector appComponent) {
    appComponent.inject(this);
  }

  @NonNull @Override public PuzzlesPresenter getPresenter() {
    return puzzlesPresenter;
  }

  @Override public void showNewsList(List<PhotoEntity> newsEntities) {
    imageGridAdapter.setList(newsEntities);
    imageGridAdapter.notifyDataSetChanged();
  }

  @Override public void setupNewsView() {
    recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.setAdapter(imageGridAdapter);
  }

  @Override public void showProgress() {
    showProgressDialog("Loading...");
  }

  @Override public void showNoNewsAvailableError() {
    recyclerView.setVisibility(View.GONE);
    noNewsView.setVisibility(View.VISIBLE);
    errorTextView.setText("No images available, please check again later :-(");
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
    return imageGridAdapter.getList().size() > 0;
  }

  @Override public void showNetworkError() {
    recyclerView.setVisibility(View.GONE);
    noNewsView.setVisibility(View.VISIBLE);
    errorTextView.setText("Please check n/w");
  }

  @Override public void showHttpError(int errorCode) {
    recyclerView.setVisibility(View.GONE);
    noNewsView.setVisibility(View.VISIBLE);
    if(errorCode == 4) {
      errorTextView.setText("No internet. Please check n/w");
    } else {
      errorTextView.setText("Something went wrong, please try again later");
    }
  }

  @OnClick(R.id.start_button)
  @Override public void startGame() {
    // TODO: 20/11/16
    // The user gets 15 seconds to remember the images
    // Once 15 seconds pass by, the images are flipped over
  }

  @Override public void iGiveUp() {
    // TODO: 20/11/16
  }

  @Override public void reset() {
    // TODO: 20/11/16
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_retry) {
      reset();
    }
    return true;
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    puzzlesPresenter.onSaveState(outState);
    if (imageGridAdapter != null) {
      ArrayList<PhotoEntity> newsEntities = imageGridAdapter.getList();
      if (newsEntities != null && !newsEntities.isEmpty()) {
        outState.putParcelableArrayList(SAVE_RECYCLER_VIEW_STATE, newsEntities);
      }
    }
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    puzzlesPresenter.onRestoreState(savedInstanceState);
    ArrayList<PhotoEntity> newsEntities =
        savedInstanceState.getParcelableArrayList(SAVE_RECYCLER_VIEW_STATE);
    if (newsEntities != null && !newsEntities.isEmpty() && imageGridAdapter != null) {
      imageGridAdapter.setList(newsEntities);
    }
  }

  @Override protected void onStop() {
    super.onStop();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
  }

  BroadcastReceiver receiver = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      //TODO: capture clicked item, compare to previous item

    }
  };
}
