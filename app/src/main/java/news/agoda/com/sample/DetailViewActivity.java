package news.agoda.com.sample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.List;
import news.agoda.com.sample.model.entity.MediaEntity;
import news.agoda.com.sample.model.entity.NewsEntity;
import news.agoda.com.sample.util.Constants;

/**
 * News detail view
 */
public class DetailViewActivity extends Activity {
  private NewsEntity newsEntity;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    Bundle extras = getIntent().getExtras();
    newsEntity = extras.getParcelable(Constants.IntentExtras.NewsListItem);

    TextView titleView = (TextView) findViewById(R.id.title);
    DraweeView imageView = (DraweeView) findViewById(R.id.news_image);
    TextView summaryView = (TextView) findViewById(R.id.summary_content);

    titleView.setText(newsEntity.getTitle());
    summaryView.setText(newsEntity.getDescription());

    MediaEntity chosenOne = findTheChosenOne();

    if (chosenOne != null) {
      DraweeController draweeController = Fresco.newDraweeControllerBuilder()
          .setImageRequest(ImageRequest.fromUri(Uri.parse(chosenOne.getUrl())))
          .setOldController(imageView.getController())
          .build();
      imageView.setController(draweeController);
    }
  }

  private MediaEntity findTheChosenOne() {
    MediaEntity theChosenOne = null;
    List<MediaEntity> mediaEntityList = newsEntity.getMediaEntities();
    if (mediaEntityList != null && mediaEntityList.size() > 0) {
      for (MediaEntity mediaEntity : mediaEntityList) {
        if (theChosenOne == null) {
          theChosenOne = mediaEntity;
          continue;
        }
        if (theChosenOne.getWidth() * theChosenOne.getHeight()
            < mediaEntity.getWidth() * mediaEntity.getHeight()) { //using largest image available
          theChosenOne = mediaEntity;
        }
      }
    }
    return theChosenOne;
  }

  public void onFullStoryClicked(View view) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse(newsEntity.getUrl()));
    startActivity(intent);
  }
}
