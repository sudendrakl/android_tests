package puzzle.myntra.com.sample.model.entity;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * Created by sudendra.kamble on 05/11/16.
 */
@Data @ToString public class PhotoListEntity {

  String title;
  String link;
  String description;
  String modified;
  String generator;

  @SerializedName("items") List<PhotoEntity> photoEntities = new ArrayList<>();
}
