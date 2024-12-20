package model.actions.attack

import api.{Source, Target}
import model.actions.Actions
import model.entity.Global
import model.exception.{InvalidTarget, ShouldHaveWeapon}
import util.Json.{*, given}

/**
 * Class `Attack` represents an attack action within the game, where one entity
 * performs an attack on another. This class extends the `Actions` trait and includes
 * unique details such as the attack's name and identifier.
 *
 * @constructor Initializes an `Attack` instance with a specified name and ID.
 * @param name The descriptive name of the attack action (protected visibility).
 * @param id   The unique identifier of this specific attack action.
 */
class Attack(protected val name: String, val id: String) extends Actions {

  /**
   * Retrieves the name of the attack action.
   *
   * @return The name of this action.
   */
  def getName: String = name

  /**
   * Retrieves the unique identifier for the attack action.
   *
   * @return The ID of this action.
   */
  def getId: String = id

  /**
   * Converts the attack action into a JSON representation for data exchange or storage.
   *
   * @return A `JsObj` that includes the ID and the action type in the form "Attack→name".
   */
  override def toJson: JsObj = JsObj(
    "id" -> id,
    "action" -> s"Attack→$name"
  )

  /**
   * Determines equality of two attack actions based on their names.
   * Two attack actions are considered equal if they share the same name.
   *
   * @param other The object to be compared.
   * @return `true` if the other object is an `Attack` with the same name, `false` otherwise.
   */
  override def equals(other: Any): Boolean = {
    other match {
      case other: Attack => name == other.getName
      case _ => false
    }
  }

  /**
   * Handles the interaction between a source and a target when performing an attack.
   * Ensures the source and target meet the necessary conditions and processes the attack logic.
   *
   * @param source The attacking entity (must implement `Source`).
   * @param target The entity being attacked (must implement `Target`).
   * @throws InvalidTarget If the source or target is in an invalid state for an attack.
   * @throws ShouldHaveWeapon If the source lacks an attack capability or weapon.
   */
  override def receiveTarget(source: Source, target: Target): Unit = {
    if (source.getHp <= 0) {
      throw new InvalidTarget(s"${source.getName} has negative HP, and cannot perform an attack")
    }
    if (target.getHp <= 0) {
      throw new InvalidTarget(s"${target.getName} is already defeated and cannot receive an attack")
    }

    source match {
      case entity: Global =>
        if (entity.getAtk != 0) {
          val attackPower: Int = source.getAtk
          target.decreaseHp(attackPower)
          println(s"${target.getName} has received $attackPower damage")
        } else {
          throw new ShouldHaveWeapon(s"${entity.getName} must have a weapon to perform an attack")
        }
      case _ =>
        throw new InvalidTarget("Source should be an entity")
    }
  }
}
