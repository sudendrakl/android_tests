package news.agoda.com.sample.model.entity;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.ToString;

/**
 * Created by sudendra.kamble on 05/11/16.
 */
@Data @ToString public class NewsListEntity {

  String status;
  String copyright;
  String section;
  String lastUpdated;
  String numResults;
  @SerializedName("results") List<NewsEntity> newsEntities = new ArrayList<>();
}
