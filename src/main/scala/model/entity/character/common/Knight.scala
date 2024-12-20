package model.entity.character.common

import api.Source
import model.actions.Actions
import model.actions.attack.Attack
import model.actions.consume.CPotion
import model.actions.equip.AWeapon
import model.actions.movement.Move
import model.exception.CannotEquipWeapon
import model.utilizable.potions.common.{Heal, Strength}
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.common.{Sword, Dagger, Bow}
import scala.collection.mutable.ListBuffer

/**
 * The `Knight` class represents a knight character entity in the game.
 * It extends the `Common` class, inheriting the attributes and behaviors 
 * of common characters while specifically representing the knight role.
 * Knights have specific weapon restrictions and their own actions.
 *
 * @constructor Creates an instance of the `Knight` class with specified attributes.
 * @param id      The unique identifier of the knight.
 * @param name    The name of the knight.
 * @param hp      The current health points of the knight.
 * @param defense The defense value of the knight.
 * @param weight  The weight of the knight.
 */
class Knight(id: String, name: String, hp: Int, defense: Int, weight: Int)
  extends Common(id, name, hp, defense, weight) {

  /**
   * A list of available actions for the knight.
   * These actions include attack, movement, weapon equip, and potion consumption.
   */
  val actions: List[Actions] = List(
    new Attack("Knight Attack", "KnightAttack1"),
    new Move("Knight Movement", "2"),
    new AWeapon("Knight Equip Weapon", "KnightAWeapon1", ListBuffer.from(weapons)),
    new CPotion("Knight consume potion", "KnightConsumePotion1", ListBuffer.from(potions))
  )

  /**
   * Checks if the knight can equip the given weapon based on their restrictions.
   * Knights can equip swords, daggers, and bows.
   *
   * @param weapon The weapon to be checked.
   * @return `true` if the knight can equip the weapon, `false` otherwise.
   */
  override def canEquipWeapon(weapon: Weapons): Boolean = {
    weapon match {
      case _: Sword => true
      case _: Dagger => true
      case _: Bow => true
      case _        => false
    }
  }

  /**
   * Equips the specified weapon to the knight if the weapon is allowed.
   * The knight can equip swords, daggers, and bows.
   *
   * @param weapon The weapon to be equipped.
   * @throws CannotEquipWeapon If the weapon is not allowed to be equipped by the knight.
   */
  override def equipWeapon(weapon: Weapons): Unit = {
    if (canEquipWeapon(weapon)) {
      weaponsSlot = Some(weapon)
    } else {
      throw new CannotEquipWeapon("This weapon cannot be equipped by the knight.")
    }
  }
}

