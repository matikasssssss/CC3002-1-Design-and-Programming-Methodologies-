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
 * The `Thief` class represents a thief character entity in the game.
 * It extends the `Common` class, inheriting the attributes and behaviors
 * of common characters while specifically representing the thief role.
 * Thieves have specific weapon restrictions and their own actions.
 *
 * @constructor Creates an instance of the `Thief` class with specified attributes.
 * @param id      The unique identifier of the thief.
 * @param name    The name of the thief.
 * @param hp      The current health points of the thief.
 * @param defense The defense value of the thief.
 * @param weight  The weight of the thief.
 */
class Thief(id: String, name: String, hp: Int, defense: Int, weight: Int)
  extends Common(id, name, hp, defense, weight) {

  /**
   * A list of weapons available for the thief.
   * These weapons include a basic sword and a basic dagger.
   */
  val sword: Sword = new Sword("Sword1", "Basic Sword", 15, 10, this)
  val dagger: Dagger = new Dagger("Dagger1", "Basic Dagger", 10, 10, this)

  /**
   * A list of potions available for the thief.
   * These potions include a basic healing potion and a basic strength potion.
   */
  private val heal: Heal = new Heal("Heal1", "Basic Heal Potion")
  private val strength: Strength = new Strength("Strength1", "Basic Strength Potion")

  /**
   * A list of actions available for the thief.
   * These actions include attack, movement, weapon equip, and potion consumption.
   */
  val actions: List[Actions] = List(
    new Attack("Thief Attack", "ThiefAttack1"),
    new Move("Thief Movement", "2"),
    new AWeapon("Thief Equip Weapon", "ThiefAWeapon1", ListBuffer.from(weapons)),
    new CPotion("Thief Consume Potion", "ThiefConsumePotion1", ListBuffer.from(potions))
  )

  /**
   * Checks if the thief can equip the given weapon based on their restrictions.
   * Thieves can equip swords and daggers.
   *
   * @param weapon The weapon to be checked.
   * @return `true` if the thief can equip the weapon, `false` otherwise.
   */
  override def canEquipWeapon(weapon: Weapons): Boolean = {
    weapon match {
      case _: Sword => true
      case _: Dagger => true
      case _ => false
    }
  }

  /**
   * Equips the specified weapon to the thief if the weapon is allowed.
   * The thief can equip swords and daggers.
   *
   * @param weapon The weapon to be equipped.
   * @throws CannotEquipWeapon If the weapon is not allowed to be equipped by the thief.
   */
  override def equipWeapon(weapon: Weapons): Unit = {
    if (canEquipWeapon(weapon)) {
      weaponsSlot = Some(weapon)
    } else {
      throw new CannotEquipWeapon("This weapon cannot be equipped by the thief.")
    }
  }
}

