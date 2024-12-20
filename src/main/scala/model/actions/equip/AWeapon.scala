package model.actions.equip

import api.{Source, Target}
import model.actions.Actions
import model.exception.{CannotEquipWeapon, InvalidTarget}
import model.utilizable.weapons.Weapons
import util.Json.{*, given}

import scala.collection.mutable.ListBuffer

/**
 * Class `AWeapon` represents an action to equip weapons in the game.
 * It extends the `Actions` trait, which defines the structure for all actions in the game.
 * This class is used to equip one or more weapons to a target entity.
 *
 * @constructor Initializes an `AWeapon` instance with a given name, unique identifier, and a list of weapons.
 * @param name    The name of the equip action (protected visibility).
 * @param id      The unique identifier for the equip action.
 * @param targets A list of `Weapons` that represent the weapons to be equipped by the target entity.
 */
class AWeapon(protected val name: String, val id: String, private val targets: ListBuffer[Weapons])
  extends Actions {

  /**
   * Handles the process of equipping weapons to a target entity.
   * Validates the source and target states and attempts to equip each weapon to the target.
   *
   * @param source The entity initiating the equip action (must implement `Source`).
   * @param target The entity receiving the weapons (must implement `Target`).
   * @throws InvalidTarget If the source or target is in an invalid state for equipping weapons.
   * @throws CannotEquipWeapon If the target cannot equip a particular weapon.
   */
  override def receiveTarget(source: Source, target: Target): Unit = {
    if (source.getHp <= 0) {
      throw new InvalidTarget(s"${source.getName} has negative HP and cannot equip a weapon")
    }
    if (target.getHp <= 0) {
      throw new InvalidTarget(s"${target.getName} is already defeated and cannot receive a weapon")
    }

    for (weapon <- targets) {
      if (target.canEquipWeapon(weapon)) {
        target.equipWeapon(weapon)
        println(s"${target.getName} has equipped the weapon ${weapon.getName}")
      } else {
        throw new CannotEquipWeapon(s"${target.getName} cannot equip the weapon ${weapon.getName}")
      }
    }
  }

  /**
   * Retrieves the name of the equip action.
   *
   * @return The name of this equip action.
   */
  def getName: String = name

  /**
   * Retrieves the unique identifier for this equip action.
   *
   * @return The ID of the equip action.
   */
  def getId: String = id

  /**
   * Retrieves the list of weapon targets associated with this equip action.
   *
   * @return A `ListBuffer` of `Weapons` that are equipped through this action.
   */
  def getTargets: ListBuffer[Weapons] = targets

  /**
   * Converts the equip action into a JSON object representation.
   * This allows the action to be serialized for use in external systems or saving game states.
   *
   * @return A `JsObj` containing the ID, action description, and a JSON array of weapon targets.
   */
  override def toJson: JsObj = JsObj(
    "id" -> id,
    "action" -> s"Weapon→$name",
    "targets" -> JsArr(targets.map(_.toJson)) // Converts each weapon in `targets` to JSON.
  )

  /**
   * Checks equality between this equip action and another object.
   * Two `AWeapon` actions are considered equal if they share the same name.
   *
   * @param other The object to compare to.
   * @return `true` if the other object is an `AWeapon` with the same name, `false` otherwise.
   */
  override def equals(other: Any): Boolean = {
    other match {
      case other: AWeapon => name == other.getName
      case _ => false
    }
  }
}
