package news.agoda.com.sample.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sudendra.kamble on 05/11/16.
 */

public enum ImageSizeEnum implements Parcelable{
  Normal("Normal"),
  Medium("mediumThreeByTwo210"),
  Large("thumbLarge"),
  Standard("Standard Thumbnail");

  ImageSizeEnum(String value) {
    this.value = value;
  }

  String value;

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(value);
  }

  @Override public int describeContents() {
    return 0;
  }

  public static final Creator<ImageSizeEnum> CREATOR = new Creator<ImageSizeEnum>() {
    @Override public ImageSizeEnum createFromParcel(Parcel in) {
      return ImageSizeEnum.valueOf(in.readString());
    }

    @Override public ImageSizeEnum[] newArray(int size) {
      return new ImageSizeEnum[size];
    }
  };
}
