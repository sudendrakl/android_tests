package puzzle.di.com.sample.base;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import puzzle.di.com.sample.PuzzleApplication;
import puzzle.di.com.sample.di.Injector;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity
    implements BaseView {

  private ProgressDialog progressDialog;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject(((PuzzleApplication) getApplication()).getDaggerInjector());
    getPresenter().onViewCreated(savedInstanceState == null);
  }

  @Override protected void onDestroy() {
    unregisterReceiver(networkChangeReceiver);
    super.onDestroy();
    getPresenter().unsubscribe();
  }

  protected abstract void inject(Injector appComponent);

  public abstract @NonNull P getPresenter();

  public View getRootView() {
    return findViewById(android.R.id.content);
  }

  protected void showProgressDialog() {
    if (progressDialog == null) progressDialog = new ProgressDialog(this);
    if (!progressDialog.isShowing()) {
      progressDialog.show();
    }
  }

  protected void showProgressDialog(String message) {
    if (progressDialog == null) progressDialog = new ProgressDialog(this);
    progressDialog.setMessage(message);
    if (!progressDialog.isShowing()) {
      progressDialog.show();
    }
  }

  protected void hideProgressDialog() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.hide();
    }
  }

  @Override protected void onStart() {
    super.onStart();
    registerReceiver(networkChangeReceiver, getNetworkChangeFilter());
  }

  private IntentFilter getNetworkChangeFilter() {
    IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
    filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
    return filter;
  }

  private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
    @Override public void onReceive(Context context, Intent intent) {
      getRootView().postDelayed(() -> getPresenter().handleNetworkStateChange(), 1000L);
    }
  };
}
