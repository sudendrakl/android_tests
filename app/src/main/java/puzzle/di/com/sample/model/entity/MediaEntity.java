package puzzle.di.com.sample.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.ToString;

/**
 * This class represents a media item
 */
@Data @ToString public class MediaEntity implements Parcelable {
  @SerializedName("m")
  String url;

  protected MediaEntity(Parcel in) {
    url = in.readString();
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(url);
  }

  @Override public int describeContents() {
    return 0;
  }

  public static final Creator<MediaEntity> CREATOR = new Creator<MediaEntity>() {
    @Override public MediaEntity createFromParcel(Parcel in) {
      return new MediaEntity(in);
    }

    @Override public MediaEntity[] newArray(int size) {
      return new MediaEntity[size];
    }
  };
}
