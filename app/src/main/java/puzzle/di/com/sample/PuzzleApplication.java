package puzzle.di.com.sample;

import android.app.Application;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.*;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import puzzle.di.com.sample.di.DaggerInjector;
import puzzle.di.com.sample.di.Injector;
import puzzle.di.com.sample.util.Constants;

public class PuzzleApplication extends Application {

  private Injector daggerInjector;

  @Override public void onCreate() {
    super.onCreate();
    initDagger();
    initFresco();
  }

  public void initFresco() {
    DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this).setMaxCacheSize(Constants.DISK_CACHE_SIZE).build();
    ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this).setMainDiskCacheConfig(diskCacheConfig).build();
    Fresco.initialize(this, imagePipelineConfig);
  }

  public void initDagger() {
    getDaggerInjector().init(this);
  }

  public Injector getDaggerInjector() {
    if (daggerInjector == null) {
      daggerInjector = new DaggerInjector();
    }
    return daggerInjector;
  }
}
