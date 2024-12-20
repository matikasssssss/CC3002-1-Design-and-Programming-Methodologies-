package model.actions.magic

import api.{Source, Target}
import model.actions.Actions
import model.entity.Global
import model.entity.character.magic.IMagic
import model.panels.IPanel
import util.Json.{*, given}

/**
 * Abstract class `AbstractMagic` represents a magical action in the game.
 * It extends the `Actions` trait, which defines the structure for all actions in the game.
 * This class serves as a base for specific magic actions like `Healing`, `Meteor`, etc.
 *
 * @constructor Creates an instance of the `AbstractMagic` class with a given name and unique identifier.
 * @param id   The unique identifier for this magic action.
 */
abstract class AbstractMagic(val id: String)
  extends Actions {

  /**
   * Returns the name of the magic action.
   *
   * @return The name of the magic action.
   */
  def getName: String = name

  /**
   * Returns the unique identifier of the magic action.
   *
   * @return The unique identifier of the magic action.
   */
  def getId: String = id
  
  /**
   * Converts the magic action into a JSON object representation.
   * This allows the action to be serialized for use in external systems or saving game states.
   *
   * @return A `JsObj` containing the ID, action description in the form "Magic→name".
   */
  override def toJson: JsObj = JsObj(
    "id" -> id,
    "action" -> s"Magic→$name"
  )

  /**
   * Checks equality between this magic action and another object.
   * Two magic actions are considered equal if they have the same name.
   *
   * @param other The object to compare to.
   * @return `true` if the other object is an `Actions` with the same name, `false` otherwise.
   */
  override def equals(other: Any): Boolean = {
    other match {
      case other: Actions => id == other.getId
      case _ => false
    }
  }

}
