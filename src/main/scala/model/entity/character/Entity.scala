package model.entity.character

import model.entity.AbstractGlobal

/**
 * The `Entity` trait represents a character in the game and extends the `Global` interface.
 * An entity has attributes such as name, health points, defense, weight, weapons, and magical abilities.
 * This trait defines the basic structure for handling mana, health points, and equipping weapons.
 * It should be extended by more specific character types.
 *
 * The `Entity` trait provides essential attributes and methods for managing the health, defense,
 * and magical capabilities of a character. This includes maximum mana, current mana, and attack power.
 *
 * @note This trait is meant to be extended by more specific character types, such as mages, knights, etc.
 */
trait Entity extends AbstractGlobal {

  // The maximum mana points (MP) of the entity.
  val maxMp: Int

  /**
   * Returns the maximum mana points (MP) of the entity.
   * This is the upper limit of magical energy available for casting spells or using magic-related abilities.
   *
   * @return The maximum mana points of the entity.
   */
  def getMaxMp: Int

  /**
   * Returns the current magic points (MP) of the entity.
   * This is the amount of magical energy that the entity currently has available.
   *
   * @return The current magic points of the entity.
   */
  def getMp: Int

  /**
   * Returns the attack power of the entity's magical abilities.
   * This value represents the attack strength of the entity's magic-based attacks.
   *
   * @return The magical attack power (MP).
   */
  def getAtkm: Int
}

