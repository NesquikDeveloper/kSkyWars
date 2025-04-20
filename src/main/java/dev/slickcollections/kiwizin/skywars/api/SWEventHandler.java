package dev.slickcollections.kiwizin.skywars.api;

import java.util.List;

public interface SWEventHandler {
  
  void handleEvent(SWEvent evt);
  
  List<Class<?>> getEventTypes();
}
