package model.entity.character.common

import api.Target
import model.actions.Actions
import model.entity.character.Entity
import model.exception.{ActionNotFound, UnitNotInPanel}
import model.panels.{IPanel, Panel}
import model.utilizable.Utilizable
import model.utilizable.potions.Potions
import model.utilizable.weapons.Weapons
import util.Json.{*, given}

import scala.collection.mutable.ListBuffer

/**
 * The `Common` abstract class represents a common character entity in the game.
 * It extends the `AbstractCharacters` class and provides functionality 
 * for JSON serialization, equality comparison, and handling common character attributes.
 * It serves as a base class for various character types with shared functionality like HP, defense, weight,
 * and inventory management.
 *
 * @constructor Creates an instance of the `Common` class with specified attributes.
 * @param id      The unique identifier of the character.
 * @param name    The name of the character.
 * @param hp      The current health points of the character.
 * @param defense The defense value of the character.
 * @param weight  The weight of the character.
 */
abstract class Common(val id: String, val name: String, var hp: Int, protected var defense: Int, protected val weight: Int)
  extends ICommon{

  /**
   * The maximum health points (HP) of the character, initialized with the initial value of `hp`.
   */
  val maxHp: Int = hp

  val maxMp: Int = 0

  val maxDef: Int = defense

  /**
   * Slot for weapons assigned to the character.
   * Initialized with a single empty slot.
   */
  var weaponsSlot: Option[Weapons] = None

  /**
   * Inventory of usable items for the character.
   * It is private and can only be accessed within this class.
   */
  private val inventory: ListBuffer[Utilizable] = ListBuffer.empty[Utilizable]

  /**
   * Returns the name of the character.
   *
   * @return The name of the character.
   */
  def getName: String = name

  /**
   * Returns the current health points of the character.
   *
   * @return The current health points.
   */
  override def getHp: Int = hp

  /**
   * Retrieves the maximum health points of the character.
   *
   * @return The maximum health points.
   */
  override def getMaxHp: Int = maxHp

  override def getMaxDef: Int = maxDef

  /**
   * Returns the defense value of the character.
   *
   * @return The defense value.
   */
  override def getDef: Int = defense

  /**
   * Returns the weight of the character.
   *
   * @return The weight of the character as a double.
   */
  override def getWeight: Double = weight

  /**
   * Returns the unique identifier of the character.
   *
   * @return The unique identifier.
   */
  override def getId: String = id

  /**
   * Returns the array of weapons available to the character.
   *
   * @return An array of optional weapons assigned to the character.
   */
  override def getWeapon: Option[Weapons] = weaponsSlot


  /**
   * Returns the attack value of the character.
   * For this class, the attack value is always 0 unless a weapon is equipped.
   *
   * @return The attack value.
   */
  override def getAtk: Int = weaponsSlot match{
    case Some(weapon)=> weapon.getAtk
    case None=> 0
  }

  /**
   * Returns the magic attack value of the character.
   * For this class, the magic attack value is always 0.
   *
   * @return The magic attack value.
   */
  override def getAtkm: Int = 0

  /**
   * Reduces the character's HP by a specified damage amount.
   * Ensures HP does not fall below zero.
   *
   * @param amount The amount to apply to the character's HP.
   */
  override def decreaseHp(amount: Int): Unit = {
    hp = (hp - amount).max(0)
  }

  /**
   * Increases the character's HP by a specified amount.
   * Ensures HP does not exceed the maximum health points (maxHp).
   *
   * @param amount The amount of health to add to the character's HP.
   */
  override def increaseHp(amount: Int): Unit = {
    hp = (hp + amount).min(maxHp)
  }

  override def increaseDef(amount: Int): Unit = {
    defense = (defense + amount).min(maxDef)
  }

  /**
   * Executes an action on a target.
   * This method delegates the action to the provided target.
   *
   * @param action The action to be performed.
   * @param target The target of the action.
   */
  override def doAction(action: Actions, target: Target): Unit = {
    action.receiveTarget(this, target)
  }

  /**
   * Converts the common character's attributes into a JSON object representation.
   * This is used for serialization purposes.
   *
   * @return A `JsObj` containing the character's ID, attributes (name, HP, defense, weight),
   *         and an image filename based on the character's name.
   */
  override def toJson: JsObj = JsObj(
    "id" -> id,
    "attributes" -> JsArr(
      JsObj("name" -> "name", "value" -> name),
      JsObj("name" -> "hp", "value" -> hp),
      JsObj("name" -> "def", "value" -> defense),
      JsObj("name" -> "weight", "value" -> weight),
    ),
    "img" -> s"$name.gif"
  )

  /**
   * Compares this common character with another object to check if they are equal.
   * Two common characters are considered equal if they share the same name, 
   * health points, defense, and weight.
   *
   * @param other The object to compare to.
   * @return `true` if the other object is an `Entity` with the same attributes, `false` otherwise.
   */
  override def equals(other: Any): Boolean = {
    other match {
      case other: Entity =>
        name == other.getName && hp == other.getHp && defense == other.getDef && weight == other.getWeight
      case _ => false
    }
  }
}
