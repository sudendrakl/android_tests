package puzzle.myntra.com.sample.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.Data;
import lombok.ToString;

/**
 * This represents a news item
 */
@Data @ToString public class PhotoEntity implements Parcelable {
  static final String TAG = PhotoEntity.class.getSimpleName();

  String title;
  String link;
  MediaEntity media;
  String dateTaken;
  String description;
  String published;
  String author;
  String authorId;
  String tags;

  protected PhotoEntity(Parcel in) {
    title = in.readString();
    link = in.readString();
    media = in.readParcelable(MediaEntity.class.getClassLoader());
    dateTaken = in.readString();
    description = in.readString();
    published = in.readString();
    author = in.readString();
    authorId = in.readString();
    tags = in.readString();
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(title);
    dest.writeString(link);
    dest.writeParcelable(media, flags);
    dest.writeString(dateTaken);
    dest.writeString(description);
    dest.writeString(published);
    dest.writeString(author);
    dest.writeString(authorId);
    dest.writeString(tags);
  }

  @Override public int describeContents() {
    return 0;
  }

  public static final Creator<PhotoEntity> CREATOR = new Creator<PhotoEntity>() {
    @Override public PhotoEntity createFromParcel(Parcel in) {
      return new PhotoEntity(in);
    }

    @Override public PhotoEntity[] newArray(int size) {
      return new PhotoEntity[size];
    }
  };
}
