package provider.src.threetrios.controller;

/**
 * Model status listeners are used to communicate between controllers.
 */
public interface ModelStatusListener {

  /**
   * This method is used to tell the other controller to refresh its own view, and begin playing.
   */
  void refreshAndPlay();
}
