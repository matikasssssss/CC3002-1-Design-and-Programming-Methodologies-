package api

import model.actions.Actions
import model.panels.IPanel
import model.utilizable.weapons.Weapons
import scala.collection.mutable.ListBuffer

/**
 * The `Source` trait represents an entity in the game that can initiate actions.
 * It serves as the origin or "source" of actions, encapsulating attributes and abilities
 * related to performing various actions and applying effects in the game.
 *
 * The `Source` is typically implemented by characters or entities that can perform actions
 * such as attacking, using items, casting spells, or moving.
 */
trait Source extends GameObject {

  /**
   * The name of the source.
   * This is a unique identifier, often used to represent the character or entity in-game.
   *
   * @return The name of the source entity.
   */
  val name: String

  /**
   * The list of actions that the source can perform.
   * This contains all available actions that the source entity can execute.
   * These actions could include attacks, using items, or casting spells.
   *
   * @return A list of actions available for the source entity.
   */
  val actions: List[Actions]

  /**
   * Searches for a specific action by its unique identifier.
   *
   * @param id The unique ID associated with the action.
   * @return An optional `Actions` instance if a match is found; otherwise, None.
   */
  def findActionById(id: String): Option[Actions]

  /**
   * Executes a specified action on a given target.
   *
   * @param action The action to be performed.
   * @param target The target entity that the action is directed towards.
   */
  def doAction(action: Actions, target: Target): Unit

  /**
   * Returns the attack value of the source entity.
   *
   * @return The attack value (e.g., physical damage or other modifiers).
   */
  def getAtk: Int

  /**
   * Returns the attack modifier (e.g., magical attack) of the source entity.
   *
   * @return The attack modifier value.
   */
  def getAtkm: Int

  /**
   * Returns the current amount of mana points (MP) of the source entity.
   *
   * @return The mana points of the entity.
   */
  def getMp: Int

  /**
   * Decreases the source's mana points by a specified amount.
   *
   * @param amount The amount to decrease from the source's MP.
   */
  def decreaseMp(amount: Int): Unit

  /**
   * Returns the name of the source entity.
   *
   * @return The name of the source entity.
   */
  def getName: String

  /**
   * Deactivates the magic boost of the source entity.
   * This may be used for actions that temporarily enhance the source's magical capabilities.
   */
  def deactivateMagicBoost(): Unit

  /**
   * Returns the current hit points (HP) of the source entity.
   *
   * @return The hit points of the entity.
   */
  def getHp: Int
}
