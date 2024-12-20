package model.player

import api.GameObject
import model.entity.Global
import model.entity.character.Entity
import util.SprayJson.{JsArr, JsObj}

import scala.collection.mutable.ListBuffer

/**
 * Class `Player` represents a player in the game, holding a list of character units.
 *
 * @param id        The unique identifier for the player.
 * @param unitsList A mutable list of character entities associated with the player.
 */
class Player(val id: String, private val unitsList: ListBuffer[Global]) extends GameObject {

  /**
   * Retrieves the unique identifier for the player.
   *
   * @return The unique ID of the player.
   */
  def getId: String = id

  /**
   * Retrieves the list of character units associated with the player.
   *
   * @return A mutable list of `Global` objects representing the player's units.
   */
  def getUnits: ListBuffer[Global] = unitsList

  /**
   * Checks if the player has been defeated.
   *
   * A player is considered defeated if there are no units left in their units list.
   *
   * @return `true` if the player is defeated (units list is empty); otherwise, `false`.
   */
  def isDefeated: Boolean = unitsList.isEmpty

  /**
   * Removes a specific unit from the player's units list.
   *
   * @param unit The `Global` unit to remove from the list.
   */
  def removeUnit(unit: Global): Unit = {
    unitsList -= unit
  }

  /**
   * Converts the player and their units into a JSON object for serialization.
   *
   * @return A `JsObj` representing the player's data and their characters.
   */
  override def toJson: JsObj = JsObj(
    "characters" -> JsArr(
      unitsList.map(_.toJson)
    )
  )
}
