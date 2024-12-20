package model.utilizable.weapons

import model.entity.Global
import util.Json.{*, given}
import model.exception.*

/**
 * Abstract class `AbstractWeapons` provides a base implementation for weapon entities.
 * It implements the `Weapons` trait and includes common properties and methods
 * for all weapon types.
 *
 * @param id     The unique identifier for the weapon.
 * @param name   The name of the weapon.
 * @param atk    The attack power of the weapon.
 * @param weight The weight of the weapon.
 * @param owner  The character or entity that owns the weapon.
 */
abstract class AbstractWeapons(val id: String, protected val name: String, protected val atk: Int, protected val weight: Int, protected var owner: Global)
  extends Weapons {

  // Default implementation for attack modifier.
  override def atkm: Int = 0

  /**
   * Returns the name of the weapon.
   *
   * @return The name of the weapon.
   */
  def getName: String = name

  /**
   * Returns the unique identifier of the weapon.
   *
   * @return The weapon ID.
   */
  def getId: String = id

  /**
   * Returns the attack power of the weapon.
   *
   * @return The attack value.
   */
  def getAtk: Int = atk

  /**
   * Returns the attack modifier of the weapon.
   * In this case, it is always 0.
   *
   * @return The attack modifier.
   */
  def getAtkm: Int = 0

  /**
   * Returns the weight of the weapon.
   *
   * @return The weight value.
   */
  def getWeight: Double = weight

  /**
   * Returns the entity that owns this weapon.
   *
   * @return The owner entity.
   */
  def getOwner: Global = owner

  /**
   * Converts the weapon instance to a JSON object for serialization.
   *
   * @return A JSON object representing the weapon.
   */
  override def toJson: JsObj = JsObj(
    "id" -> id,
    "name" -> name,
    "atk" -> atk,
    "weight" -> weight,
    "owner" -> owner.toJson
  )

  /**
   * Compares this weapon to another object for equality.
   * Two weapons are considered equal if they have the same name, attack power,
   * weight, and owner.
   *
   * @param other The other object to compare.
   * @return True if the weapons are equal, false otherwise.
   */
  override def equals(other: Any): Boolean = {
    other match {
      case other: Weapons =>
        name == other.getName && atk == other.getAtk &&
          weight == other.getWeight && owner.equals(other.getOwner)
      case _ => false
    }
  }
}
