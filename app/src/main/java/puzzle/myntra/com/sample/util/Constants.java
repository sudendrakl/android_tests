package puzzle.myntra.com.sample.util;

/**
 * Created by sudendra.kamble on 05/11/16.
 */

public interface Constants {
  interface IntentExtras {
    String ListItem = "list_item_click_position";
  }
  interface BroadcastEvents {
    String NetworkStateChange = "NetworkStateChange";
    String ItemListClick = "item_click";
    String AllImagesLoaded = "AllImagesLoaded";
  }

  int GRID_COLS = 3;
  int DISK_CACHE_SIZE = 1024 * 1024 * 10; //10MB

}
