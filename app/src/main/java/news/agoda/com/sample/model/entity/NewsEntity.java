package news.agoda.com.sample.model.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import lombok.Data;
import lombok.ToString;

/**
 * This represents a news item
 */
@Data @ToString public class NewsEntity implements Parcelable{
  static final String TAG = NewsEntity.class.getSimpleName();

  String section;
  String subsection;
  String title;
  @SerializedName("abstract") String description;
  String url;
  String byline;
  String itemType;
  String updatedDate;
  String createdDate;
  String publishedDate;
  String materialTypeFacet;
  String kicker;
  ArrayList<String> desFacet;
  ArrayList<String> orgFacet;
  ArrayList<String> perFacet;
  ArrayList<String> geoFacet;
  @SerializedName("multimedia") ArrayList<MediaEntity> mediaEntities;

  protected NewsEntity(Parcel in) {
    section = in.readString();
    subsection = in.readString();
    title = in.readString();
    description = in.readString();
    url = in.readString();
    byline = in.readString();
    itemType = in.readString();
    updatedDate = in.readString();
    createdDate = in.readString();
    publishedDate = in.readString();
    materialTypeFacet = in.readString();
    kicker = in.readString();
    desFacet = in.createStringArrayList();
    orgFacet = in.createStringArrayList();
    perFacet = in.createStringArrayList();
    geoFacet = in.createStringArrayList();
    mediaEntities = in.createTypedArrayList(MediaEntity.CREATOR);
  }

  public static final Creator<NewsEntity> CREATOR = new Creator<NewsEntity>() {
    @Override public NewsEntity createFromParcel(Parcel in) {
      return new NewsEntity(in);
    }

    @Override public NewsEntity[] newArray(int size) {
      return new NewsEntity[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(section);
    dest.writeString(subsection);
    dest.writeString(title);
    dest.writeString(description);
    dest.writeString(url);
    dest.writeString(byline);
    dest.writeString(itemType);
    dest.writeString(updatedDate);
    dest.writeString(createdDate);
    dest.writeString(publishedDate);
    dest.writeString(materialTypeFacet);
    dest.writeString(kicker);
    dest.writeStringList(desFacet);
    dest.writeStringList(orgFacet);
    dest.writeStringList(perFacet);
    dest.writeStringList(geoFacet);
    dest.writeTypedList(mediaEntities);
  }
}
