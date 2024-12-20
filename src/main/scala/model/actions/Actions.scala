package model.actions

import api.{GameObject, Source, Target}

/**
 * Trait `Actions` represents an action in the game and extends the `GameObject` interface.
 * Each action has a name and a unique identifier.
 */

trait Actions extends GameObject{

  /**
   * The name of the action.
   * This field is protected, meaning it can only be accessed within the class that defines it or its subclasses.
   *
   * @return The name of the action.
   */
  protected def name: String

  /**
   * The unique identifier of the action.
   * This field is public and can be accessed externally.
   *
   * @return The unique identifier of the action.
   */
  def id: String

  /**
   * Returns the name of the action.
   *
   * @return The name of the action.
   */
  def getName: String

  /**
   * Returns the unique identifier of the action.
   *
   * @return The unique identifier of the action.
   */
  def getId: String

  /**
   * Abstract method to apply the action on a target.
   * This method will be implemented in concrete classes of `Actions` to define
   * specific behavior of each action type when it's applied on a target by a source.
   *
   * @param source The `Source` entity that initiates the action.
   * @param target The `Target` entity that receives the action.
   */
  def receiveTarget(source: Source, target: Target): Unit
  
}
