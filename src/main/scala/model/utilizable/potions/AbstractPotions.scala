package model.utilizable.potions

import model.utilizable.weapons.Weapons
import util.Json.{*, given}
import model.exception.*

/**
 * The `AbstractPotions` class is an abstract implementation of the `Potions` trait.
 * It provides the common functionality for all potions, including the ability to retrieve the potion's 
 * name and ID, check equality based on the name, and convert the potion to JSON format.
 */
abstract class AbstractPotions extends Potions {

  /**
   * Gets the name of the potion.
   * This method returns the potion's name as a string.
   *
   * @return The name of the potion.
   */
  def getName: String = name

  /**
   * Gets the unique identifier of the potion.
   * This method returns the potion's ID as a string.
   *
   * @return The ID of the potion.
   */
  def getId: String = id

  /**
   * Checks if this potion is equal to another object.
   * Two potions are considered equal if they have the same name.
   *
   * @param other The object to compare with.
   * @return True if the other object is a potion with the same name; otherwise, false.
   */
  override def equals(other: Any): Boolean = {
    other match {
      case other: Potions => name == other.getName
      case _ => false
    }
  }

  /**
   * Converts the potion's properties to JSON format.
   * This method creates a JSON object containing the potion's ID and name.
   *
   * @return A JSON object representing the potion.
   */
  override def toJson: JsObj = JsObj(
    "id" -> id,
    "name" -> name
  )
}
