package api

/**
 * The `Subject` trait defines the contract for entities that maintain a list of observers.
 * The `Subject` is responsible for notifying its observers about changes or events that occur.
 * This follows the **Observer Design Pattern**, where a subject maintains a list of observers
 * and notifies them when certain events or updates occur, allowing for a decoupled and reactive system.
 */
trait Subject {

  /**
   * Adds an observer to the list of observers.
   * This observer will be notified when changes or updates occur in the subject.
   *
   * @param observer The observer to be added.
   */
  def addObserver(observer: Observer): Unit

  /**
   * Removes an observer from the list of observers.
   * The observer will no longer receive notifications from the subject.
   *
   * @param observer The observer to be removed.
   */
  def removeObserver(observer: Observer): Unit

  /**
   * Notifies all registered observers with a message.
   * This method is called to inform the observers of any changes or events that should be handled.
   *
   * @param message The message to be sent to the observers.
   */
  def notify(message: String): Unit
}
