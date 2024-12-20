package api

import scala.collection.mutable.ListBuffer

/**
 * The `AbstractSubject` class implements the `Subject` trait and provides the core functionality
 * for managing a list of observers in the observer design pattern.
 * It allows for adding, removing, and notifying observers when a change occurs.
 * This class is typically used by entities that need to notify other components when their state changes.
 */
abstract class AbstractSubject extends Subject {

  // A mutable list of observers that are registered to this subject.
  private val observers: ListBuffer[Observer] = ListBuffer()

  /**
   * Adds an observer to the list of observers. The observer will be notified
   * when the subject's state changes.
   *
   * @param observer The observer to add.
   */
  override def addObserver(observer: Observer): Unit = {
    observers += observer
  }

  /**
   * Removes an observer from the list. The observer will no longer receive notifications
   * from the subject when its state changes.
   *
   * @param observer The observer to remove.
   */
  override def removeObserver(observer: Observer): Unit = {
    observers -= observer
  }

  /**
   * Notifies all registered observers with the provided message. Each observer's `update`
   * method is called with the message, informing them of a change in the subject's state.
   *
   * @param message The message to send to all observers.
   */
  override def notify(message: String): Unit = {
    observers.foreach(_.update(message))
  }
}
