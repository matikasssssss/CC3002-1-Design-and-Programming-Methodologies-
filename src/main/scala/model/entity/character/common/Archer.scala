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
import model.utilizable.weapons.common.{Bow, Dagger, Sword}
import model.utilizable.weapons.magic.{Cane, Wand}

import scala.collection.mutable.ListBuffer

/**
 * The `Archer` class represents an archer character entity in the game.
 * It extends the `Common` class, inheriting the common character attributes and behaviors
 * while specifically representing the archer role.
 *
 * The `Archer` can perform actions such as attacking, moving, equipping weapons, and consuming potions.
 * It has the ability to equip specific weapons and consume potions like other character types.
 *
 * @constructor Creates an instance of the `Archer` class with the specified attributes.
 * @param id      The unique identifier of the archer.
 * @param name    The name of the archer.
 * @param hp      The current health points of the archer.
 * @param defense The defense value of the archer.
 * @param weight  The weight of the archer.
 */
class Archer(id: String, name: String, hp: Int, defense: Int, weight: Int)
  extends Common(id, name, hp, defense, weight) {

  // Actions that the archer can perform: Attack, Move, Equip Weapon, Consume Potion
  val actions: List[Actions] = List(
    new Attack("Archer Attack", "ArcherAttack1"),
    new Move("Archer Movement", "2"),
    new AWeapon("Archer Equip Weapon", "ArcherAWeapon1", ListBuffer.from(weapons)),
    new CPotion("Archer consume potion", "ArcherConsumePotion1", ListBuffer.from(potions))
  )

  /**
   * Checks if the given weapon can be equipped by the archer.
   * The archer can equip the following weapons: Sword, Wand, Bow.
   *
   * @param weapon The weapon to check.
   * @return `true` if the weapon can be equipped, `false` otherwise.
   */
  override def canEquipWeapon(weapon: Weapons): Boolean = {
    weapon match {
      case _: Sword => true
      case _: Wand  => true
      case _: Bow   => true
      case _        => false
    }
  }

  /**
   * Equip a weapon if it is valid for the archer to use.
   * The weapon must be one of the following: Sword, Wand, or Bow.
   * If the weapon is valid, it will be equipped in the archer's weapon slot.
   *
   * @param weapon The weapon to equip.
   * @throws CannotEquipWeapon If the weapon cannot be equipped.
   */
  override def equipWeapon(weapon: Weapons): Unit = {
    if (canEquipWeapon(weapon)) {
      weaponsSlot = Some(weapon)
    } else {
      throw new CannotEquipWeapon(s"Archer cannot equip ${weapon.getClass.getSimpleName}")
    }
  }
}
