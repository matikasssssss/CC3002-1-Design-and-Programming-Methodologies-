package model.entity.character.magic

import api.Source
import model.actions.Actions
import model.actions.attack.Attack
import model.actions.consume.CPotion
import model.actions.equip.AWeapon
import model.actions.magic.{Healing, Meteor, Purification, Thunder}
import model.actions.movement.Move
import model.exception.CannotEquipWeapon
import model.utilizable.potions.common.{Heal, Strength}
import model.utilizable.potions.magic.{MagicalForce, Manna}
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.common.{Bow, Dagger, Sword}
import model.utilizable.weapons.magic.{Cane, Wand}

import scala.collection.mutable.ListBuffer

/**
 * The `WhiteMage` class represents a white mage character entity in the game.
 * It extends the `Magic` class, inheriting common attributes and behaviors
 * while specifically representing the white mage role, including unique weapon restrictions and actions.
 *
 * The white mage is a character class that can equip specific weapons (Bow, Wand, Cane), 
 * has access to a set of unique spells (Healing and Purification), and is capable of performing 
 * a variety of actions such as attacking, moving, and consuming potions.
 *
 * @constructor Creates an instance of the `WhiteMage` class with specified attributes.
 * @param id      The unique identifier of the white mage.
 * @param name    The name of the white mage.
 * @param hp      The current health points of the white mage.
 * @param defense The defense value of the white mage.
 * @param weight  The weight of the white mage.
 * @param mp      The magic points of the white mage.
 */
class WhiteMage(id: String, name: String, hp: Int, defense: Int, weight: Int, mp: Int)
  extends Magic(id, name, hp, defense, weight, mp) {

  /**
   * Declare spells available for the White mage.
   * These spells include Healing and Purification.
   */
  private val spellHealing: Healing = new Healing("Healing1")
  private val spellPurification: Purification = new Purification("Purification1")

  /**
   * A list of actions available for the White mage.
   * These actions include attack, movement, weapon equip, potion consumption, and magic spells.
   */
  val actions: List[Actions] = List(
    new Attack("White Mage Attack", "WhiteMageAttack1"),
    new Move("White Mage Movement", "WhiteMageMove1"),
    new AWeapon("White Mage Equip Weapon", "WhiteMageAWeapon1", ListBuffer.from(weapons)),
    new CPotion("White mage consume potion", "WhiteMageConsumePotion1", ListBuffer.from(potions)),
    spellHealing, spellPurification
  )

  /**
   * Determines whether the white mage can equip a specific weapon.
   * The white mage can equip only specific types of weapons.
   *
   * @param weapon The weapon to check.
   * @return True if the weapon is allowed, false otherwise.
   */
  override def canEquipWeapon(weapon: Weapons): Boolean = {
    weapon match {
      case _: Bow => true  // The white mage can equip a Bow.
      case _: Wand => true  // The white mage can equip a Wand.
      case _: Cane => true  // The white mage can equip a Cane.
      case _ => false       // The white mage cannot equip any other weapon type.
    }
  }

  /**
   * Equips a weapon to the white mage if it is allowed by the weapon restrictions.
   * If the weapon is valid, it will be assigned to the white mage's weapon slot.
   *
   * @param weapon The weapon to equip.
   */
  override def equipWeapon(weapon: Weapons): Unit = {
    if (canEquipWeapon(weapon)) {
      weaponsSlot = Some(weapon)  // Equip the valid weapon to the white mage's weapon slot.
    }
  }
}
