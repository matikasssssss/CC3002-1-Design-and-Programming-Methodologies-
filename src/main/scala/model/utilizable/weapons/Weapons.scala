package model.utilizable.weapons

import api.Source
import model.entity.Global
import model.entity.character.Entity
import model.utilizable.{AbstractUtilizable, Utilizable}

/**
 * Trait `Weapons` defines the interface for weapon entities in the game.
 * It extends `Utilizable` to ensure that all weapons have an ID and a name.
 */
trait Weapons extends AbstractUtilizable {

  /**
   * The attack power of the weapon.
   */
  protected def atk: Int

  /**
   * The attack modifier for the weapon.
   */
  def atkm: Int

  /**
   * The weight of the weapon.
   */
  protected def weight: Int

  /**
   * The owner of the weapon, typically a character entity.
   */
  protected def owner: Global

  /**
   * Returns the attack power of the weapon.
   *
   * @return The attack value.
   */
  def getAtk: Int

  /**
   * Returns the attack modifier of the weapon.
   *
   * @return The attack modifier.
   */
  def getAtkm: Int

  /**
   * Returns the weight of the weapon.
   *
   * @return The weight value.
   */
  def getWeight: Double

  /**
   * Returns the entity that owns this weapon.
   *
   * @return The owner entity.
   */
  def getOwner: Global
}
