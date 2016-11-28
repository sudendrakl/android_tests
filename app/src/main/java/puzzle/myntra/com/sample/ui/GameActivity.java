package puzzle.myntra.com.sample.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
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

public class GameActivity extends BaseActivity<PuzzlesPresenter> implements PuzzlesView {

  private static final String TAG = GameActivity.class.getSimpleName();

  @Inject PuzzlesPresenter puzzlesPresenter;
  @Inject ImageGridAdapter imageGridAdapter;
  // @Inject LinearLayoutManager linearLayoutManager;
  @Inject GridLayoutManager gridLayoutManager;

  @Bind(R.id.recycler_view) RecyclerView recyclerView;
  @Bind(R.id.lin_lay_1) View puzzleContainer;
  @Bind(R.id.no_images) View errorView;
  @Bind(R.id.error_text_view) TextView errorTextView;
  @Bind(R.id.find_image) SimpleDraweeView hintImageView;
  @Bind(R.id.counter) TextView counterTextView;
  @Bind(R.id.no_network) View noNetwork;
  @Bind(R.id.hit_count) AppCompatTextView hitCount;

  private static final String SAVE_RECYCLER_VIEW_STATE = "save_recycler_view_state";

  @Override public void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_game);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);
  }

  @Override protected void onStart() {
    super.onStart();
    IntentFilter filter = new IntentFilter(Constants.BroadcastEvents.ItemListClick);
    filter.addAction(Constants.BroadcastEvents.AllImagesLoaded);
    LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
  }

  @Override protected void inject(Injector appComponent) {
    appComponent.inject(this);
  }

  @NonNull @Override public PuzzlesPresenter getPresenter() {
    return puzzlesPresenter;
  }

  @Override public void showImagesList(List<PhotoEntity> photoEntities) {
    imageGridAdapter.setList(photoEntities);
    imageGridAdapter.notifyDataSetChanged();
  }

  @Override public void setupView() {
    recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.setAdapter(imageGridAdapter);
    RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();

  }

  @Override public void showProgress() {
    showProgressDialog("Loading...");
  }

  @Override public void showNoImagesAvailableError() {
    puzzleContainer.setVisibility(View.GONE);
    errorView.setVisibility(View.VISIBLE);
    errorTextView.setText("No images available, please check again later :-(");
  }

  @Override public void hideImagesList() {

  }

  @Override public void hideProgress() {
    hideProgressDialog();
  }

  @Override public void hideError() {
    puzzleContainer.setVisibility(View.VISIBLE);
    errorView.setVisibility(View.GONE);
  }

  @Override public boolean isImagesListAvailable() {
    return imageGridAdapter.getList().size() > 0;
  }

  @Override public void showNetworkError(boolean connected) {
    if(puzzlesPresenter.isGameInProgress())
      return;
    if (!connected) {
      noNetwork.setVisibility(View.VISIBLE);
    } else {
      noNetwork.setVisibility(View.GONE);
      puzzleContainer.setVisibility(View.GONE);
      errorView.setVisibility(View.VISIBLE);
      errorTextView.setText("Please check n/w");
    }
  }

  @Override public void showHttpError(int errorCode) {
    puzzleContainer.setVisibility(View.GONE);
    errorView.setVisibility(View.VISIBLE);
    if(errorCode == 4) {
      errorTextView.setText("No internet. Please check n/w");
    } else {
      errorTextView.setText("Something went wrong, please try again later");
    }
  }

  @Override public List<PhotoEntity> getImagesList() {
    return imageGridAdapter.getList();
  }

  @Override public void updateTimer(int count) {
    getRootView().post(() -> counterTextView.setText(count<=0?"GO":Integer.toString(count)));
  }

  @OnClick(R.id.retry_button)
  @Override public void reset() {
    AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogTheme).setMessage(
        "Are you sure you want to reset?\nYou will lose all progress")
        .setTitle("Confirm!!")
        .setNegativeButton("Cancel", null)
        .setPositiveButton("Yes", (dialog, which) -> puzzlesPresenter.onViewCreated(true))
        .create();
    alertDialog.show();
  }

  @Override public void flip() {
    // adapter flip images
    getRootView().post(() -> {
      imageGridAdapter.setMode(ImageGridAdapter.FLIPPED);
      imageGridAdapter.setAnimate(ImageGridAdapter.ANIMATE_ALL);
    });
  }

  @Override public void nextHint(PhotoEntity photoEntity, int size) {
    // show another image in hint
    // hintImageView.setImageURI();
    getRootView().post(() -> {
      DraweeController draweeController = Fresco.newDraweeControllerBuilder()
          .setImageRequest(ImageRequest.fromUri(Uri.parse(photoEntity.getMedia().getUrl())))
          .setOldController(hintImageView.getController())
          .build();
      hintImageView.setController(draweeController);
      hintImageView.setVisibility(View.VISIBLE);
      int displayListSize = getImagesList().size();
      counterTextView.setText(String.format("%d/%d", displayListSize - size, displayListSize));
      Animation animation = AnimationUtils.loadAnimation(GameActivity.this, android.R.anim.fade_out);
      counterTextView.startAnimation(animation);
    });
  }

  @Override public void congrats() {
    AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogTheme).setMessage(
        "Awesome, you did\n\nyou saved the world :)")
        .setTitle("Hero")
        .setPositiveButton("Yes", (dialog, which) -> finish())
        .setOnDismissListener((dialog) -> finish())
        .create();
    alertDialog.show();
  }

  @Override public void awesome() {
    Snackbar.make(getRootView(), "Great!!!", Snackbar.LENGTH_SHORT).show();
  }

  @Override public void poorChoice() {
    Snackbar.make(getRootView(), "nope....noooooope", Snackbar.LENGTH_SHORT).show();
  }

  @OnClick(R.id.no_network) public void openNetworkSetting() {
    startActivity(new Intent(Settings.ACTION_SETTINGS));
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    puzzlesPresenter.onSaveState(outState);
    if (imageGridAdapter != null) {
      ArrayList<PhotoEntity> photoEntities = imageGridAdapter.getList();
      if (photoEntities != null && !photoEntities.isEmpty()) {
        outState.putParcelableArrayList(SAVE_RECYCLER_VIEW_STATE, photoEntities);
      }
    }
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    puzzlesPresenter.onRestoreState(savedInstanceState);
    ArrayList<PhotoEntity> photoEntities =
        savedInstanceState.getParcelableArrayList(SAVE_RECYCLER_VIEW_STATE);
    if (photoEntities != null && !photoEntities.isEmpty() && imageGridAdapter != null) {
      imageGridAdapter.setList(photoEntities);
    }
  }

  @Override protected void onStop() {
    super.onStop();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
  }

  BroadcastReceiver receiver = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      // capture clicked item, compare to previous item
      // onclick flip that item
      String action = intent.getAction();
      switch (action) {
        case Constants.BroadcastEvents.ItemListClick:
          int position = intent.getIntExtra(Constants.IntentExtras.ListItem,-1);
          getRootView().post(() -> {
            imageGridAdapter.setAnimate(position);
            puzzlesPresenter.validate(position);
            hitCount.setText("Hit count:"+puzzlesPresenter.incrementHitCount());
          });
          break;
        case Constants.BroadcastEvents.AllImagesLoaded:
          Snackbar.make(getRootView(), "Game Begin!!!", Snackbar.LENGTH_SHORT).show();
          puzzlesPresenter.startCountDown();
          break;
      }
    }
  };
}
