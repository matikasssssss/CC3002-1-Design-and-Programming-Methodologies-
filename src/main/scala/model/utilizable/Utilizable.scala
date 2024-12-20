package model.utilizable

import api.{GameObject, Target}

/**
 * Trait `Utilizable` represents items or objects that can be used within the game.
 * It extends `GameObject` and requires implementations to provide an ID and name.
 */
trait Utilizable extends GameObject with Target {

  /** The name of the utilizable item */
  protected val name: String

  /**
   * The unique identifier for the utilizable item.
   *
   * @return A `String` representing the ID of the item.
   */
  def id: String

  /**
   * Retrieves the name of the utilizable item.
   *
   * @return A `String` representing the name of the item.
   */
  def getName: String

  /**
   * Retrieves the unique ID of the utilizable item.
   *
   * @return A `String` representing the ID of the item.
   */
  def getId: String
}
