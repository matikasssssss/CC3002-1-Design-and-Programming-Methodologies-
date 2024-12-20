package api

/**
 * The `Observer` trait defines a basic contract for implementing the observer pattern.
 * It provides a method to receive updates with messages.
 *
 * The observer pattern allows objects (observers) to be notified of changes in another object (the subject).
 * This trait can be used to implement classes that need to react to changes in a subject.
 */
trait Observer {

  /**
   * This method is called to notify the observer of a change or update.
   *
   * @param message The message or information to be passed to the observer.
   */
  def update(message: String): Unit
}
