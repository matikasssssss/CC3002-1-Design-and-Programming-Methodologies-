package model.entity.character.magic

import api.{Source, Target}
import model.actions.Actions
import model.entity.Global
import model.entity.character.Entity
import model.exception.UnitNotInPanel
import model.panels.{IPanel, Panel}
import model.utilizable.Utilizable
import model.utilizable.potions.magic.Manna
import model.utilizable.weapons.Weapons
import util.Json.{*, given}

import scala.collection.mutable.ListBuffer

/**
 * The `Magic` class represents a magical character entity in the game.
 * It extends the `AbstractCharacters` class, inheriting common character attributes
 * and adding magic-specific attributes and behaviors, such as managing magic points (MP),
 * activating magic boosts, and using magic weapons.
 *
 * This class provides basic functionality for magic users in the game,
 * allowing them to interact with actions and attributes specific to magic.
 *
 * @constructor Creates an instance of the `Magic` class with the specified attributes.
 * @param id      The unique identifier of the magic character.
 * @param name    The name of the magic character.
 * @param hp      The current health points (HP) of the magic character.
 * @param defense The defense value of the magic character.
 * @param weight  The weight of the magic character.
 * @param mp      The magic points (MP) of the magic character.
 */
abstract class Magic(val id: String, val name: String, var hp: Int, protected var defense: Int, protected val weight: Int, protected var mp: Int)
  extends IMagic {

  // Attribute to control whether the magic boost is active
  private var magicBoostActive: Boolean = false

  /**
   * The maximum health points (HP) of the character, initialized with the initial value of `hp`.
   */
  val maxHp: Int = hp

  // The maximum defense points of the character
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

  /**
   * Retrieves the maximum defense points of the character.
   *
   * @return The maximum defense points.
   */
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
   * @return The weight of the character as a decimal (Double).
   */
  def getWeight: Double = weight

  /**
   * Returns the unique identifier of the character.
   *
   * @return The unique identifier.
   */
  def getId: String = id

  /**
   * Returns the weapon equipped by the character.
   *
   * @return An option of the weapon assigned to the character.
   */
  override def getWeapon: Option[Weapons] = weaponsSlot

  /**
   * The maximum magic points (MP) of the character, initialized with the initial value of `mp`.
   * This is used to determine the character's magic capacity.
   */
  val maxMp: Int = mp

  /**
   * Gets the maximum magic points of the character.
   *
   * @return The maximum magic points.
   */
  override def getMaxMp: Int = maxMp

  /**
   * Gets the current magic points of the character.
   *
   * @return The current magic points (MP).
   */
  override def getMp: Int = mp

  /**
   * Decreases the magic points (MP) of the character by a specified amount.
   * Ensures MP does not fall below zero.
   *
   * @param amount The amount to decrease the magic points.
   */
  override def decreaseMp(amount: Int): Unit = {
    mp = (mp - amount).max(0)
  }

  /**
   * Increases the magic points (MP) of the character by a specified amount.
   * Ensures MP does not exceed the maximum magic points.
   *
   * @param amount The amount to increase the magic points.
   */
  override def increaseMp(amount: Int): Unit = {
    mp = (mp + amount).min(maxMp)
  }

  /**
   * Returns the attack value of the character.
   * For this class, the attack value depends on whether the character has a weapon equipped.
   *
   * @return The attack value.
   */
  override def getAtk: Int = weaponsSlot match {
    case Some(weapon) => weapon.getAtk
    case None => 0
  }

  /**
   * Returns the magical attack value (magic attack) of the character.
   * If a magic boost is activated, the attack value is increased by 50%.
   *
   * @return The magical attack value.
   */
  override def getAtkm: Int = weaponsSlot match {
    case Some(weapon) =>
      if (isMagicBoostActivate) {
        (weapon.getAtkm + weapon.getAtkm * 0.50).toInt
      } else {
        weapon.getAtkm
      }
    case None => 0
  }

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

  /**
   * Increases the defense value of the character.
   * Ensures defense does not exceed the maximum defense value (maxDef).
   *
   * @param amount The amount to increase the defense value.
   */
  override def increaseDef(amount: Int): Unit = {
    defense = (defense + amount).min(maxDef)
  }

  /**
   * Performs an action on a target by invoking the `receiveTarget` method of the action.
   *
   * @param action The action to be performed.
   * @param target The target of the action.
   */
  override def doAction(action: Actions, target: Target): Unit = {
    action.receiveTarget(this, target)
  }

  /**
   * Deactivates the magic boost for this character.
   */
  override def deactivateMagicBoost(): Unit = {
    magicBoostActive = false
  }

  /**
   * Activates the magic boost for this character, increasing magical attack value.
   */
  override def activateMagicBoost(): Unit = {
    magicBoostActive = true
  }

  /**
   * Checks if the magic boost is currently active.
   *
   * @return `true` if the magic boost is active, `false` otherwise.
   */
  override def isMagicBoostActivate: Boolean = magicBoostActive

  /**
   * Converts the magic character's attributes to a JSON object representation.
   * This includes the character's id, attributes (name, hp, defense, weight, mp), and an image reference.
   *
   * @return A `JsObj` containing the character's id, attributes, and image reference.
   */
  override def toJson: JsObj = JsObj(
    "id" -> id,
    "attributes" -> JsArr(
      JsObj("name" -> "name", "value" -> name),
      JsObj("name" -> "hp", "value" -> hp),
      JsObj("name" -> "def", "value" -> defense),
      JsObj("name" -> "weight", "value" -> weight),
      JsObj("name" -> "mp", "value" -> mp),
    ),
    "img" -> s"$name.gif"  // Character's image reference based on its name
  )

  /**
   * Checks for equality between this magic character and another object.
   * The comparison is based on the character's `name`, `hp`, `defense`, `weight`, and `mp`.
   *
   * @param other The object to compare against.
   * @return `true` if the other object is a magic character with the same attributes; otherwise, `false`.
   */
  override def equals(other: Any): Boolean = {
    other match {
      case other: Entity =>
        name == other.getName && hp == other.getHp && defense == other.getDef &&
          weight == other.getWeight && mp == other.getMp
      case _ => false
    }
  }
}


