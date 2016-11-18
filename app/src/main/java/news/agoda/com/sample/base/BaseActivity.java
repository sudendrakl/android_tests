package news.agoda.com.sample.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import news.agoda.com.sample.NewsApplication;
import news.agoda.com.sample.di.Injector;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity
    implements BaseView {

  private ProgressDialog progressDialog;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    inject(((NewsApplication) getApplication()).getDaggerInjector());
    getPresenter().onViewCreated(savedInstanceState == null);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    getPresenter().unsubscribe();
  }

  protected abstract void inject(Injector appComponent);

  public abstract @NonNull P getPresenter();

  @Override public void showNetworkError() {
    Snackbar.make(getRootView(), "Check your internet connection", Snackbar.LENGTH_LONG).show();
  }

  @Override public void showHttpError(int errorCode) {
    Snackbar.make(getRootView(), "Error connecting to server [ERR_CODE: " + errorCode + "]",
        Snackbar.LENGTH_LONG).show();
  }

  public View getRootView() {
    return findViewById(android.R.id.content);
  }

  protected void showProgressDialog() {
    if(progressDialog == null)
      progressDialog = new ProgressDialog(this);
    if(!progressDialog.isShowing()) {
      progressDialog.show();
    }
  }

  protected void showProgressDialog(String message) {
    if(progressDialog == null)
      progressDialog = new ProgressDialog(this);
    progressDialog.setMessage(message);
    if(!progressDialog.isShowing()) {
      progressDialog.show();
    }
  }

  protected void hideProgressDialog() {
    if (progressDialog != null && progressDialog.isShowing()) {
      progressDialog.hide();
    }
  }
}
