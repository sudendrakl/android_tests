package puzzle.myntra.com.sample;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.ArrayList;
import java.util.List;
import puzzle.myntra.com.sample.model.entity.MediaEntity;
import puzzle.myntra.com.sample.model.entity.PhotoEntity;
import puzzle.myntra.com.sample.util.Constants;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.RVHolder> {

  private ArrayList<PhotoEntity> list = new ArrayList<>();

  public ImageGridAdapter() {
    super();
  }

  @Override public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new RVHolder(
        LayoutInflater.from(parent.getContext()).inflate(puzzle.myntra.com.sample.R.layout.list_item, parent, false));
  }

  @Override public void onBindViewHolder(RVHolder viewHolder, int position) {

    PhotoEntity newsEntity = list.get(position);
    MediaEntity mediaEntity = newsEntity.getMedia();
    String thumbnailURL = mediaEntity.getUrl();

    DraweeController draweeController = Fresco.newDraweeControllerBuilder()
        .setImageRequest(ImageRequest.fromUri(Uri.parse(thumbnailURL)))
        .setOldController(viewHolder.imageView.getController())
        .build();
    viewHolder.imageView.setController(draweeController);
  }

  @Override public int getItemCount() {
    return list.size();
  }

  public ArrayList<PhotoEntity> getList() {
    return list;
  }

  public void setList(List<PhotoEntity> list) {
    this.list.clear();
    this.list.addAll(list);
  }

  public void addList(ArrayList<PhotoEntity> list) {
    this.list.addAll(list);
  }

  static class RVHolder extends RecyclerView.ViewHolder {
    View progress;
    DraweeView imageView;

    public RVHolder(View itemView) {
      super(itemView);
      progress = itemView.findViewById(R.id.progress);
      imageView = (DraweeView) itemView.findViewById(R.id.news_item_image);
      itemView.setOnClickListener(v -> {
        Intent intent = new Intent(Constants.BroadcastEvents.ItemListClick);
        intent.putExtra(Constants.IntentExtras.ListItem, getAdapterPosition());
        LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
      });
    }
  }
}
