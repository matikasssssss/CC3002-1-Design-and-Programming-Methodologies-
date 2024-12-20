package api

import model.utilizable.weapons.Weapons

/**
 * The `Target` trait represents any entity in the game that can be targeted by actions.
 * It provides methods for managing health, mana, defense, and interactions with various items,
 * including weapons and potions.
 * This trait is typically implemented by entities that can be the recipient of actions,
 * such as characters, enemies, or NPCs.
 */
trait Target extends GameObject {

  /**
   * Gets the current health points (HP) of the target.
   *
   * @return The current HP of the target.
   */
  def getHp: Int

  /**
   * Decreases the target's health points by the specified amount.
   *
   * @param amount The amount to decrease the target's HP by.
   */
  def decreaseHp(amount: Int): Unit

  /**
   * Decreases the target's magic points (MP) by the specified amount.
   *
   * @param amount The amount to decrease the target's MP by.
   */
  def decreaseMp(amount: Int): Unit

  /**
   * Increases the target's health points by the specified amount.
   *
   * @param amount The amount to increase the target's HP by.
   */
  def increaseHp(amount: Int): Unit

  /**
   * Increases the target's magic points (MP) by the specified amount.
   *
   * @param amount The amount to increase the target's MP by.
   */
  def increaseMp(amount: Int): Unit

  /**
   * Increases the target's defense by the specified amount.
   *
   * @param amount The amount to increase the target's defense by.
   */
  def increaseDef(amount: Int): Unit

  /**
   * Gets the current defense of the target.
   *
   * @return The defense value of the target.
   */
  def getDef: Int

  /**
   * Gets the maximum health points (HP) of the target.
   *
   * @return The maximum HP of the target.
   */
  def getMaxHp: Int

  /**
   * Gets the name of the target.
   *
   * @return The name of the target.
   */
  def getName: String

  /**
   * Gets the maximum magic points (MP) of the target.
   *
   * @return The maximum MP of the target.
   */
  def getMaxMp: Int

  /**
   * Activates the target's magic boost, enhancing its magic abilities.
   */
  def activateMagicBoost(): Unit

  /**
   * Deactivates the target's magic boost, returning magic abilities to normal.
   */
  def deactivateMagicBoost(): Unit

  /**
   * Checks if the target's magic boost is currently activated.
   *
   * @return `true` if the magic boost is activated, otherwise `false`.
   */
  def isMagicBoostActivate: Boolean

  /**
   * Checks if the target can equip the specified weapon.
   *
   * @param weapon The weapon to check if it can be equipped.
   * @return `true` if the weapon can be equipped, otherwise `false`.
   */
  def canEquipWeapon(weapon: Weapons): Boolean

  /**
   * Equips the specified weapon to the target.
   *
   * @param weapon The weapon to equip.
   */
  def equipWeapon(weapon: Weapons): Unit
}
