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
 * The `BlackMage` class represents a black mage character entity in the game.
 * It extends the `Magic` class, inheriting the common attributes and behaviors
 * while specifically representing the black mage role.
 * Black mages have specific weapon restrictions and their own actions,
 * focusing on magic and support roles in combat.
 *
 * @constructor Creates an instance of the `BlackMage` class with specified attributes.
 * @param id      The unique identifier of the black mage.
 * @param name    The name of the black mage.
 * @param hp      The current health points of the black mage.
 * @param defense The defense value of the black mage.
 * @param weight  The weight of the black mage.
 * @param mp      The magic points of the black mage.
 */
class BlackMage(id: String, name: String, hp: Int, defense: Int, weight: Int, mp: Int)
  extends Magic(id, name, hp, defense, weight, mp) {

  /**
   * Declare spells available for the Black mage.
   * These spells include Meteor and Thunder.
   */
  private val spellMeteor: Meteor = new Meteor("3")
  private val spellThunder: Thunder = new Thunder("Thunder1")

  /**
   * A list of actions available for the Black mage.
   * These actions include attack, movement, weapon equip, potion consumption, and magic spells.
   */
  val actions: List[Actions] = List(
    new Attack("Black Mage Attack", "BlackMageAttack1"),
    new Move("Black Mage Movement", "BlackMageMove1"),
    new AWeapon("BlackMage Equip Weapon", "BlackMageAWeapon1", ListBuffer.from(weapons)),
    new CPotion("Black Mage Consume Potion", "BlackMageConsumePotion1", ListBuffer.from(potions)),
    spellMeteor, spellThunder
  )

  /**
   * Checks if the black mage can equip the given weapon based on their restrictions.
   * Black mages can equip daggers, wands, and canes.
   *
   * @param weapon The weapon to be checked.
   * @return `true` if the black mage can equip the weapon, `false` otherwise.
   */
  override def canEquipWeapon(weapon: Weapons): Boolean = {
    weapon match {
      case _: Dagger => true // The black mage can equip a Dagger.
      case _: Wand => true // The black mage can equip a Wand.
      case _: Cane => true // The black mage can equip a Cane.
      case _ => false // The black mage cannot equip any other weapon type.
    }
  }

  /**
   * Equips the specified weapon to the black mage if the weapon is allowed.
   * The black mage can equip daggers, wands, and canes.
   *
   * @param weapon The weapon to be equipped.
   * @throws CannotEquipWeapon If the weapon is not allowed to be equipped by the black mage.
   */
  override def equipWeapon(weapon: Weapons): Unit = {
    if (canEquipWeapon(weapon)) {
      weaponsSlot = Some(weapon) // Equip the valid weapon to the black mage's weapon slot.
    } 
  }
}

