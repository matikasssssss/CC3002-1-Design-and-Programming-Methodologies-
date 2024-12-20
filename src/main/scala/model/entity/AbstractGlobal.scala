package model.entity

import model.actions.Actions
import model.exception.*
import model.panels.{IPanel, Panel}
import model.utilizable.potions.Potions
import model.utilizable.weapons.Weapons

import scala.collection.mutable.ListBuffer

/**
 * The `AbstractGlobal` class is an abstract implementation of the `Global` trait, 
 * providing base functionality for characters who do not have the ability to use weapons, 
 * magic, or potions. It defines the core actions, panel management, and constraints 
 * for these characters.
 */
abstract class AbstractGlobal extends Global {

  /**
   * The initial panel where the character is located.
   * Default is a new Panel with name "Panel0" at coordinates (0, 0).
   */
  var initPanel: IPanel = new Panel("Panel0", 0, 0)

  /**
   * Retrieves the list of available actions for the character.
   * This will be overridden by specific subclasses to define actual actions.
   *
   * @return A list of actions available to the character.
   */
  override def availableActions: List[Actions] = actions

  /**
   * Gets the current panel where the character is located.
   *
   * @return The current panel where the character resides.
   */
  override def getPanel: IPanel = initPanel

  /**
   * Sets the character's location to a new panel.
   * Throws an exception if the provided panel is null.
   *
   * @param panel The panel to set as the new location of the character.
   */
  override def setPanel(panel: IPanel): Unit = {
    if (panel == null) {
      throw new UnitNotInPanel(s"Panel cannot be null")
    } else {
      initPanel = panel
    }
  }

  /**
   * Finds an action by its unique identifier.
   * If the action is not found, an exception is thrown.
   *
   * @param id The ID of the action to find.
   * @return An optional action that matches the given ID.
   * @throws ActionNotFound If the action with the given ID does not exist.
   */
  override def findActionById(id: String): Option[Actions] = {
    actions.find(_.id == id).orElse {
      throw new ActionNotFound(s"$id not found in action list")
    }
  }

  /**
   * A mutable list of available weapons for the character.
   * Initially empty but can be populated as needed.
   */
  val weapons: ListBuffer[Weapons] = ListBuffer.empty[Weapons]

  /**
   * A mutable list of available potions for the character.
   * Initially empty but can be populated as needed.
   */
  val potions: ListBuffer[Potions] = ListBuffer.empty[Potions]

  /**
   * Throws an exception indicating the character cannot equip weapons.
   * This is a placeholder method to be overridden by subclasses that can equip weapons.
   *
   * @throws CannotEquipWeapon If the character attempts to equip a weapon.
   */
  private def cannotEquipWeapon(): Nothing =
    throw new CannotEquipWeapon(s"${this.getName} cannot equip weapon")

  /**
   * Throws an exception indicating the character cannot heal.
   * This is a placeholder method to be overridden by subclasses that can heal.
   *
   * @throws CannotHeal If the character attempts to heal.
   */
  private def cannotHeal(): Nothing =
    throw new CannotHeal(s"${this.getName} cannot heal")

  /**
   * Throws an exception indicating the character cannot have magic points.
   * This is a placeholder method to be overridden by subclasses that have magic points.
   *
   * @throws CannotHaveMp If the character attempts to use magic points.
   */
  private def cannotHaveMp(): Nothing =
    throw new CannotHaveMp(s"${this.getName} cannot have MP")

  /**
   * Throws an exception indicating the character cannot increase defense.
   * This is a placeholder method to be overridden by subclasses that can increase defense.
   *
   * @throws CannotHaveDef If the character attempts to increase defense.
   */
  private def cannotIncreaseDef(): Nothing =
    throw new CannotHaveDef(s"${this.getName} cannot increase defense")

  /**
   * Throws an exception indicating the character cannot use magic spells.
   * This is a placeholder method to be overridden by subclasses that can use magic.
   *
   * @throws CannotUseMagicSpells If the character attempts to use magic spells.
   */
  private def cannotUseMagicSpells(): Nothing =
    throw new CannotUseMagicSpells(s"${this.getName} cannot use magic spells")

  /**
   * Checks if the character can equip a given weapon.
   * Always throws an exception in this base class.
   *
   * @param weapon The weapon to check for equipability.
   * @return Always throws an exception in this base class.
   */
  override def canEquipWeapon(weapon: Weapons): Boolean = {
    cannotEquipWeapon()
  }

  /**
   * Attempts to equip a weapon. 
   * Always throws an exception in this base class.
   *
   * @param weapon The weapon to equip.
   * @throws CannotEquipWeapon If the character cannot equip the weapon.
   */
  override def equipWeapon(weapon: Weapons): Unit = {
    cannotEquipWeapon()
  }

  /**
   * Decreases the character's magic points by a specified amount.
   * Always throws an exception in this base class.
   *
   * @param amount The amount to decrease the magic points.
   * @throws CannotHaveMp If the character does not have magic points.
   */
  override def decreaseMp(amount: Int): Unit = {
    cannotHaveMp()
  }

  /**
   * Increases the character's health points by a specified amount.
   * Always throws an exception in this base class.
   *
   * @param amount The amount to increase the health points.
   * @throws CannotHeal If the character cannot heal.
   */
  override def increaseHp(amount: Int): Unit = {
    cannotHeal()
  }

  /**
   * Increases the character's magic points by a specified amount.
   * Always throws an exception in this base class.
   *
   * @param amount The amount to increase the magic points.
   * @throws CannotHaveMp If the character cannot have magic points.
   */
  override def increaseMp(amount: Int): Unit = {
    cannotHaveMp()
  }

  /**
   * Checks if the character's magic boost is activated.
   * Always throws an exception in this base class.
   *
   * @return Always throws an exception in this base class.
   * @throws CannotUseMagicSpells If the character cannot use magic spells.
   */
  override def isMagicBoostActivate: Boolean = {
    cannotUseMagicSpells()
  }

  /**
   * Activates the character's magic boost.
   * Always throws an exception in this base class.
   *
   * @throws CannotUseMagicSpells If the character cannot use magic spells.
   */
  override def activateMagicBoost(): Unit = {
    cannotUseMagicSpells()
  }

  /**
   * Deactivates the character's magic boost.
   * Always throws an exception in this base class.
   *
   * @throws CannotUseMagicSpells If the character cannot use magic spells.
   */
  override def deactivateMagicBoost(): Unit = {
    cannotUseMagicSpells()
  }

  /**
   * Retrieves the maximum magic points (MP) of the character.
   * Always throws an exception in this base class.
   *
   * @return Always throws an exception in this base class.
   * @throws CannotHaveMp If the character does not have magic points.
   */
  override def getMaxMp: Int = {
    cannotHaveMp()
  }

  /**
   * Retrieves the weapon currently equipped by the character.
   * Always throws an exception in this base class.
   *
   * @return Always throws an exception in this base class.
   * @throws CannotEquipWeapon If the character cannot equip weapons.
   */
  override def getWeapon: Option[Weapons] = {
    cannotEquipWeapon()
  }

  /**
   * Retrieves the current magic points (MP) of the character.
   * Always throws an exception in this base class.
   *
   * @return Always throws an exception in this base class.
   * @throws CannotHaveMp If the character does not have magic points.
   */
  override def getMp: Int = {
    cannotHaveMp()
  }

  /**
   * Increases the character's defense by a specified amount.
   * Always throws an exception in this base class.
   *
   * @param amount The amount to increase the defense.
   * @throws CannotHaveDef If the character cannot increase defense.
   */
  override def increaseDef(amount: Int): Unit = {
    cannotIncreaseDef()
  }
}
