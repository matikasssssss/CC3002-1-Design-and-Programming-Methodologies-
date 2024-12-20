package model.utilizable

import model.utilizable.weapons.Weapons
import model.exception._

/**
 * Abstract class `AbstractUtilizable` provides default implementations for methods 
 * in the `Utilizable` trait. It defines methods related to equipping weapons, 
 * health, magic points, defense, and magic boosts, but throws exceptions if 
 * called, indicating that these operations are not allowed for this class.
 *
 * This class is typically extended by entities that do not support certain 
 * features (such as healing, magic boosts, or defense), providing a base for 
 * other classes that may override the methods when necessary.
 */
abstract class AbstractUtilizable extends Utilizable {

  /**
   * Throws an exception indicating that this entity cannot equip a weapon.
   *
   * @return Nothing.
   */
  private def cannotEquipWeapon(): Nothing =
    throw new CannotEquipWeapon(s"${this.getName} can not equip weapon")

  /**
   * Throws an exception indicating that this entity cannot heal.
   *
   * @return Nothing.
   */
  private def cannotHeal(): Nothing =
    throw new CannotHeal(s"${this.getName} can not heal")

  /**
   * Throws an exception indicating that this entity cannot have magic points (MP).
   *
   * @return Nothing.
   */
  private def cannotHaveMp(): Nothing =
    throw new CannotHaveMp(s"${this.getName} can not have mp")

  /**
   * Throws an exception indicating that this entity cannot have defense points (def).
   *
   * @return Nothing.
   */
  private def cannotHaveDef(): Nothing =
    throw new CannotHaveDef(s"${this.getName} can not have def")

  /**
   * Throws an exception indicating that this entity cannot use magic spells.
   *
   * @return Nothing.
   */
  private def cannotUseMagicSpells(): Nothing =
    throw new CannotUseMagicSpells(s"${this.getName} can not use magic spells")


  /**
   * Attempts to equip a weapon but throws an exception indicating that this entity
   * cannot equip a weapon.
   *
   * @param weapon The weapon to equip.
   * @return Nothing.
   */
  override def canEquipWeapon(weapon: Weapons): Boolean = {
    cannotEquipWeapon()
  }

  /**
   * Attempts to equip a weapon but throws an exception indicating that this entity
   * cannot equip a weapon.
   *
   * @param weapon The weapon to equip.
   * @return Nothing.
   */
  override def equipWeapon(weapon: Weapons): Unit = {
    cannotEquipWeapon()
  }

  /**
   * Attempts to decrease health points but throws an exception indicating that
   * this entity cannot heal.
   *
   * @param amount The amount of health to decrease.
   * @return Nothing.
   */
  override def decreaseHp(amount: Int): Unit = {
    cannotHeal()
  }

  /**
   * Attempts to decrease magic points but throws an exception indicating that
   * this entity cannot have magic points.
   *
   * @param amount The amount of magic points to decrease.
   * @return Nothing.
   */
  override def decreaseMp(amount: Int): Unit = {
    cannotHaveMp()
  }

  /**
   * Attempts to increase defense points but throws an exception indicating that
   * this entity cannot have defense points.
   *
   * @param amount The amount of defense points to increase.
   * @return Nothing.
   */
  override def increaseDef(amount: Int): Unit = {
    cannotHaveDef()
  }

  /**
   * Attempts to increase health points but throws an exception indicating that
   * this entity cannot heal.
   *
   * @param amount The amount of health to increase.
   * @return Nothing.
   */
  override def increaseHp(amount: Int): Unit = {
    cannotHeal()
  }

  /**
   * Attempts to increase magic points but throws an exception indicating that
   * this entity cannot have magic points.
   *
   * @param amount The amount of magic points to increase.
   * @return Nothing.
   */
  override def increaseMp(amount: Int): Unit = {
    cannotHaveMp()
  }

  /**
   * Attempts to check if magic boost is activated but throws an exception indicating
   * that this entity cannot use magic spells.
   *
   * @return Nothing.
   */
  override def isMagicBoostActivate: Boolean = {
    cannotUseMagicSpells()
  }

  /**
   * Attempts to activate magic boost but throws an exception indicating that
   * this entity cannot use magic spells.
   *
   * @return Nothing.
   */
  override def activateMagicBoost(): Unit = {
    cannotUseMagicSpells()
  }

  /**
   * Attempts to deactivate magic boost but throws an exception indicating that
   * this entity cannot use magic spells.
   *
   * @return Nothing.
   */
  override def deactivateMagicBoost(): Unit = {
    cannotUseMagicSpells()
  }

  /**
   * Attempts to get the defense value but throws an exception indicating that
   * this entity cannot have defense points.
   *
   * @return Nothing.
   */
  override def getDef: Int = {
    cannotHaveDef()
  }

  /**
   * Attempts to get the health points but throws an exception indicating that
   * this entity cannot heal.
   *
   * @return Nothing.
   */
  override def getHp: Int = {
    cannotHeal()
  }

  /**
   * Attempts to get the maximum magic points but throws an exception indicating that
   * this entity cannot have magic points.
   *
   * @return Nothing.
   */
  override def getMaxMp: Int = {
    cannotHaveMp()
  }

  /**
   * Attempts to get the maximum health points but throws an exception indicating that
   * this entity cannot heal.
   *
   * @return Nothing.
   */
  override def getMaxHp: Int = {
    cannotHeal()
  }
}
