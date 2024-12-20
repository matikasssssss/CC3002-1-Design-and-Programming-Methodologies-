package model.entity

import api.{GameObject, Source, Target}
import model.actions.Actions
import model.panels.IPanel
import model.utilizable.potions.Potions
import model.utilizable.weapons.Weapons

import scala.collection.mutable.ListBuffer

/**
 * The `Global` trait defines the behavior of global characters in the game, 
 * implementing the `GameObject`, `Source`, and `Target` traits. It includes 
 * methods for managing actions, panels, health, defense, and weapons.
 */
trait Global extends GameObject with Target with Source {

  /**
   * The initial panel where the character is located.
   */
  var initPanel: IPanel

  /**
   * Retrieves the current panel where the global character is located.
   *
   * @return The current panel of the global character.
   */
  def getPanel: IPanel

  /**
   * Sets the panel where the global character should be located.
   *
   * @param panel The panel to set as the new location of the global character.
   */
  def setPanel(panel: IPanel): Unit

  /**
   * A mutable list of the global character's available weapons.
   */
  val weapons: ListBuffer[Weapons]

  /**
   * A mutable list of the global character's available potions.
   */
  val potions: ListBuffer[Potions]

  /**
   * Retrieves the list of actions available to the global character.
   *
   * @return A list of actions the global character can perform.
   */
  def availableActions: List[Actions]

  /**
   * The name of the global character.
   */
  val name: String

  /**
   * The current health points of the global character.
   */
  var hp: Int

  /**
   * The maximum health points of the global character.
   */
  val maxHp: Int

  /**
   * The maximum defense value of the global character.
   */
  val maxDef: Int

  /**
   * The defense value of the global character, which determines its resistance 
   * to incoming attacks.
   */
  protected var defense: Int

  /**
   * The weight of the global character, which might affect its movement or abilities.
   */
  protected val weight: Int

  /**
   * The unique identifier of the global character.
   */
  def id: String

  /**
   * Retrieves the name of the global character.
   *
   * @return The name of the global character.
   */
  def getName: String

  /**
   * Retrieves the current health points of the global character.
   *
   * @return The current health points of the global character.
   */
  def getHp: Int

  /**
   * Retrieves the maximum health points of the global character.
   *
   * @return The maximum health points of the global character.
   */
  def getMaxHp: Int

  /**
   * Retrieves the maximum defense value of the global character.
   *
   * @return The maximum defense value of the global character.
   */
  def getMaxDef: Int

  /**
   * Retrieves the defense value of the global character.
   *
   * @return The defense value of the global character.
   */
  def getDef: Int

  /**
   * Retrieves the weight of the global character.
   *
   * @return The weight of the global character.
   */
  def getWeight: Double

  /**
   * Retrieves the unique identifier of the global character.
   *
   * @return The unique identifier of the global character.
   */
  def getId: String

  /**
   * Retrieves the array of weapons the global character can equip.
   *
   * @return An optional weapon the character can equip.
   */
  def getWeapon: Option[Weapons]

  /**
   * Increases the defense value of the global character by a specified amount.
   *
   * @param amount The amount to increase the defense value.
   */
  def increaseDef(amount: Int): Unit

  /**
   * Increases the health points of the global character by a specified amount.
   *
   * @param amount The amount to increase the health points.
   */
  def increaseHp(amount: Int): Unit

  /**
   * Decreases the health points of the global character by a specified amount.
   *
   * @param amount The amount to decrease the health points.
   */
  def decreaseHp(amount: Int): Unit

  /**
   * Decreases the magic points of the global character by a specified amount.
   *
   * @param amount The amount to decrease the magic points.
   */
  def decreaseMp(amount: Int): Unit

  /**
   * Increases the magic points of the global character by a specified amount.
   *
   * @param amount The amount to increase the magic points.
   */
  def increaseMp(amount: Int): Unit

  /**
   * Checks whether the global character can equip the provided weapon.
   *
   * @param weapon The weapon to check for equipability.
   * @return `true` if the weapon can be equipped, otherwise `false`.
   */
  def canEquipWeapon(weapon: Weapons): Boolean

  /**
   * Equips a specified weapon to the global character.
   *
   * @param weapon The weapon to equip.
   */
  def equipWeapon(weapon: Weapons): Unit
}
