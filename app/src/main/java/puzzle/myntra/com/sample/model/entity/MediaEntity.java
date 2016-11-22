package puzzle.myntra.com.sample.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.Data;
import lombok.ToString;

/**
 * This class represents a media item
 */
@Data @ToString public class MediaEntity implements Parcelable {
  String url;
  ImageSizeEnum format;
  int height;
  int width;
  String type;
  String subtype;
  String caption;
  String copyright;

  protected MediaEntity(Parcel in) {
    url = in.readString();
    format = in.readParcelable(ImageSizeEnum.class.getClassLoader());
    height = in.readInt();
    width = in.readInt();
    type = in.readString();
    subtype = in.readString();
    caption = in.readString();
    copyright = in.readString();
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(url);
    dest.writeParcelable(format, flags);
    dest.writeInt(height);
    dest.writeInt(width);
    dest.writeString(type);
    dest.writeString(subtype);
    dest.writeString(caption);
    dest.writeString(copyright);
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
