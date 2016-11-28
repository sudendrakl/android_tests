package puzzle.myntra.com.sample.ui;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import javax.inject.Inject;
import puzzle.myntra.com.sample.R;
import puzzle.myntra.com.sample.base.BaseActivity;
import puzzle.myntra.com.sample.di.Injector;
import puzzle.myntra.com.sample.presenter.main.MainPresenter;
import puzzle.myntra.com.sample.presenter.main.MainView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {

  @Inject MainPresenter mainPresenter;

  @Bind(R.id.no_network) View noNetwork;

  @Override public void onCreate(Bundle savedInstanceState) {
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    super.onCreate(savedInstanceState);
  }

  @Override protected void inject(Injector appComponent) {
    appComponent.inject(this);
  }

  @NonNull @Override public MainPresenter getPresenter() {
    return mainPresenter;
  }

  @Override public void setupView() {

  }

  @Override public void showProgress() {

  }

  @Override public void hideProgress() {

  }

  @Override public void hideError() {

  }

  @Override public void showNetworkError(boolean connected) {
    noNetwork.setVisibility(!connected ? View.VISIBLE : View.GONE);
  }

  @Override public void showHttpError(int errorCode) {
    //INFO: no n/w calls here, nothing to do
  }

  @OnClick(R.id.start_button)
  @Override public void startGame() {
    // The user gets 15 seconds to remember the images
    // Once 15 seconds pass by, the images are flipped over
    if(noNetwork.getVisibility() == View.VISIBLE) {
      AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme).setMessage(
          "Click yes to go to network settings")
          .setTitle("No network")
          .setNegativeButton("Cancel", (dialog, which) -> {})
          .setPositiveButton("Yes", (dialog, which) -> noNetwork.performClick())
          .create();
      alertDialog.show();
    } else {
      startActivity(new Intent(this, GameActivity.class));
    }
  }

  @OnClick(R.id.no_network) public void openNetworkSetting() {
    startActivity(new Intent(Settings.ACTION_SETTINGS));
  }

}
