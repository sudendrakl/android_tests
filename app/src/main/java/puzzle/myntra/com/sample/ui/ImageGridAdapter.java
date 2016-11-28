package puzzle.myntra.com.sample.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import puzzle.myntra.com.sample.R;
import puzzle.myntra.com.sample.model.entity.MediaEntity;
import puzzle.myntra.com.sample.model.entity.PhotoEntity;
import puzzle.myntra.com.sample.util.Constants;

public class ImageGridAdapter extends RecyclerView.Adapter<ImageGridAdapter.RVHolder> {

  public static final int ANIMATE_ALL = -2;
  public static final Boolean ORIGINAL = Boolean.TRUE;
  public static final Boolean FLIPPED = Boolean.FALSE;

  private ArrayList<PhotoEntity> list = new ArrayList<>();
  private boolean MODE = ORIGINAL; //Original = true, Flipped = false
  private int imageCounter = 0;
  private WeakReference<Context> contextWeakReference;
  public ImageGridAdapter() {
    super();
  }
  private int animate = -1; //-2 = animate all, -1

  @Override public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if(contextWeakReference==null) contextWeakReference = new WeakReference<Context>(parent.getContext());
    return new RVHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
  }

  @Override public void onBindViewHolder(RVHolder viewHolder, int position) {

    PhotoEntity photoEntity = list.get(position);
    MediaEntity mediaEntity = photoEntity.getMedia();
    String thumbnailURL = mediaEntity.getUrl();

    DraweeController draweeController = Fresco.newDraweeControllerBuilder()
        .setImageRequest(ImageRequest.fromUri(Uri.parse(thumbnailURL)))
        .setOldController(viewHolder.imageView.getController())
        .setControllerListener(controllerListener)
        .setTapToRetryEnabled(true)
        .build();
    viewHolder.imageView.setController(draweeController);

    if(animate == ANIMATE_ALL) {
      viewHolder.imageView.setAnimation(getSlideOutRightAnimation(viewHolder));
    } else if(animate == position) {
      viewHolder.imageView.setAnimation(getSlideInLeftAnimation(viewHolder));
      viewHolder.imageView.postDelayed(() -> viewHolder.imageView.setAnimation(getSlideOutRightAnimation(viewHolder)), 500L);
    }
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
    imageCounter = 0;
  }

  public void addList(ArrayList<PhotoEntity> list) {
    this.list.addAll(list);
  }

  public void setMode(boolean mode) {
    MODE = mode;
  }

  public void setAnimate(int position) {
    animate = position;
    if (animate == ANIMATE_ALL) { //animate all
      notifyDataSetChanged();
    } else {
      notifyItemChanged(position);
    }
  }

  private Animation getSlideOutRightAnimation(RVHolder viewHolder) {
    Animation animation = AnimationUtils.loadAnimation(viewHolder.imageView.getContext(), android.R.anim.slide_out_right);
    animation.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {}
      @Override public void onAnimationEnd(Animation animation) {
        if(MODE) {
          viewHolder.imageView.setVisibility(View.VISIBLE);
          viewHolder.flipImage.setVisibility(View.INVISIBLE);
        } else {
          viewHolder.imageView.setVisibility(View.INVISIBLE);
          viewHolder.flipImage.setVisibility(View.VISIBLE);
        }
      }
      @Override public void onAnimationRepeat(Animation animation) {}
    });
    return animation;
  }

  private Animation getSlideInLeftAnimation(RVHolder viewHolder) {
    Animation animation = AnimationUtils.loadAnimation(viewHolder.imageView.getContext(), android.R.anim.slide_in_left);
    animation.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {}
      @Override public void onAnimationEnd(Animation animation) {
        viewHolder.imageView.setVisibility(View.VISIBLE);
        viewHolder.flipImage.setVisibility(View.INVISIBLE);
      }
      @Override public void onAnimationRepeat(Animation animation) {}
    });
    return animation;
  }

  private ControllerListener controllerListener = new ControllerListener<ImageInfo>() {
    @Override public void onSubmit(String id, Object callerContext) {}
    @Override public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
      ++imageCounter;
      if(getItemCount() == imageCounter && contextWeakReference.get() != null) {
        Intent intent = new Intent(Constants.BroadcastEvents.AllImagesLoaded);
        LocalBroadcastManager.getInstance(contextWeakReference.get()).sendBroadcast(intent);
      }
    }
    @Override public void onIntermediateImageSet(String id, ImageInfo imageInfo) {}
    @Override public void onIntermediateImageFailed(String id, Throwable throwable) {}
    @Override public void onFailure(String id, Throwable throwable) {}
    @Override public void onRelease(String id) {}
  };

  static class RVHolder extends RecyclerView.ViewHolder {
    SimpleDraweeView imageView;
    SimpleDraweeView flipImage;

    RVHolder(View itemView) {
      super(itemView);
      imageView = (SimpleDraweeView) itemView.findViewById(R.id.item_image);
      flipImage = (SimpleDraweeView) itemView.findViewById(R.id.item_flip);
      itemView.setOnClickListener(v -> {
        Intent intent = new Intent(Constants.BroadcastEvents.ItemListClick);
        intent.putExtra(Constants.IntentExtras.ListItem, getAdapterPosition());
        LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(intent);
      });
    }
  }
}
