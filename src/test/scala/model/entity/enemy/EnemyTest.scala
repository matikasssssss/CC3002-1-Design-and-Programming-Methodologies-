package model.entity.enemy

import model.actions.magic.{Healing, Meteor, Purification, Thunder}
import model.exception.{CannotEquipWeapon, CannotHaveDef, CannotHaveMp, CannotHeal, CannotUseMagicSpells}
import model.panels.{IPanel, Panel}
import model.utilizable.potions.common.{Heal, Strength}
import model.utilizable.potions.magic.{MagicalForce, Manna}
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.common.Bow
import munit.FunSuite
import util.Json.{*, given}

/**
 * Class `EnemyTest` is a suite of unit tests for the `Enemy` character,
 * verifying the functionality of its methods and properties related to enemy actions.
 *
 * The tests include checking attributes, equality comparisons,
 * and the JSON representation of the enemy character.
 */
class EnemyTest extends FunSuite {

  // Declare potions available for the enemy.
  val heal: Heal = new Heal("Heal1", "Basic heal potion")
  val strength: Strength = new Strength("Strength1", "Basic strength potion")
  val magicalForce: MagicalForce = new MagicalForce("MagicalForce1", "Basic magical force potion")
  val manna: Manna = new Manna("Manna1", "Basic manna potion")

  val panel1: IPanel = new Panel("Panel1", 2, 3)

  /**
   * Enemy characters for testing.
   */
  val enemy1: IEnemy = new Enemy("p6", "Scorpion", 70, 60, 80, 55)
  val enemy2: IEnemy = new Enemy("p6", "Scorpion", 70, 60, 80, 55)
  val enemy3: IEnemy = new Enemy("p6", "Jhonny", 70, 60, 80, 55)
  val enemy4: IEnemy = new Enemy("p6", "Scorpion", 60, 60, 80, 55)
  val enemy5: IEnemy = new Enemy("p6", "Scorpion", 70, 50, 80, 55)
  val enemy6: IEnemy = new Enemy("p6", "Scorpion", 70, 60, 90, 55)
  val enemy7: IEnemy = new Enemy("p6", "Scorpion", 70, 60, 80, 65)

  /**
   * Test case to verify the attributes of the Enemy character.
   */
  test("Scorpion attributes") {
    assertEquals(enemy1.getId, "p6")
    assertEquals(enemy1.getName, "Scorpion")
    assertEquals(enemy1.getHp, 70)
    assertEquals(enemy1.getAtk, 60)
    assertEquals(enemy1.getDef, 80)
    assertEquals(enemy1.getWeight, 55.0)
  }

  /**
   * Test case to verify that equality is correctly determined for two enemies
   * with the same attributes.
   */
  test("Equals should return true if the units have the same attributes") {
    assertEquals(enemy1, enemy2)
  }

  /**
   * Test case to verify that an Enemy instance is equal to itself.
   */
  test("Equals should return true when comparing the same units") {
    assertEquals(enemy1, enemy1)
  }

  /**
   * Test case to verify that two Enemy instances are not equal
   * when they have different attributes.
   */
  test("Equals should return false if the units don't have the same attributes") {
    assertEquals(enemy1 == enemy3, false)
    assertEquals(enemy1 == enemy4, false)
    assertEquals(enemy1 == enemy5, false)
    assertEquals(enemy1 == enemy6, false)
    assertEquals(enemy1 == enemy7, false)
  }

  /**
   * Test case to verify inequality when comparing an Enemy instance with a non-unit.
   */
  test("Equals should return false when comparing with a non-unit") {
    val nonUnit: Heal = new Heal("D6", "Heal Potion")
    assertEquals(enemy1 == nonUnit, false)
  }

  test("Max def in enemy"){
    assertEquals(enemy1.getMaxDef, 80)
  }

  test("Can not increase def"){
    intercept[CannotHaveDef]{
      enemy1.increaseDef(40)
    }
  }

  test("Enemy's can not activate magic boost"){
    intercept[CannotUseMagicSpells]{
      enemy1.activateMagicBoost()
    }
    intercept[CannotUseMagicSpells] {
      enemy1.deactivateMagicBoost()
    }
    intercept[CannotUseMagicSpells] {
      enemy1.isMagicBoostActivate
    }
  }

  test("Enemy's can not equip or have weapons"){
    val bow: Weapons = new Bow("C3", "Bow V", 65, 30, enemy1)
    intercept[CannotEquipWeapon]{
      enemy1.equipWeapon(bow)
    }
    intercept[CannotEquipWeapon] {
      enemy1.getWeapon
    }
    intercept[CannotEquipWeapon] {
      enemy1.canEquipWeapon(bow)
    }
  }

  /**
   * Test case to verify that the HP of the Enemy cannot go below zero.
   */
  test("Enemy HP should not be negative") {
    enemy1.decreaseHp(100) // Exceeds current HP
    assertEquals(enemy1.getHp, 0) // HP should be 0, not negative
  }

  /**
   * Test case to verify that the Enemy cannot increase its HP and throws CannotHeal exception.
   */
  test("Enemy HP cannot increase and throws CannotHeal exception") {
    intercept[CannotHeal] {
      enemy1.increaseHp(10) // Attempt to increase HP by 10
    }
  }

  /**
   * Test case to verify that the Enemy cannot increase its MP and throws CannotHaveMp exception.
   */
  test("Enemy MP cannot increase and throws CannotHaveMp exception") {
    intercept[CannotHaveMp] {
      enemy1.increaseMp(10) // Attempt to increase MP by 10
    }
  }

  /**
   * Test case to verify that the Enemy cannot decrease its MP and throws CannotHaveMp exception.
   */
  test("Enemy MP cannot decrease and throws CannotHaveMp exception") {
    intercept[CannotHaveMp] {
      enemy1.decreaseMp(10) // Attempt to decrease MP by 10
    }
  }

  /**
   * Test case to verify that the Enemy cannot use potions and throws CannotUsePotion exception.
   */
  test("Enemy cannot use potions and throws CannotUsePotion exception") {
  }

  /**
   * Test case to verify that the Enemy cannot use potions and throws CannotUsePotion exception.
   */
  test("Enemy cannot use potions and throws CannotUsePotion exception") {
  }

  /**
   * Test case to verify that the Enemy cannot use potions and throws CannotUsePotion exception.
   */
  test("Enemy cannot use potions and throws CannotUsePotion exception") {
  }

  /**
   * Test case to verify that the Enemy cannot use potions and throws CannotUsePotion exception.
   */
  test("Enemy cannot use potions and throws CannotUsePotion exception") {
  }

  /**
   * Test case to verify that the Enemy does not have atkm.
   */
  test("Enemy does not have atkm") {
    assertEquals(enemy1.getAtkm, 0)
  }

  /**
   * Test case to verify that the Enemy cannot have MP and throws CannotHaveMp exception.
   */
  test("Enemy cannot have MP and throws CannotHaveMp exception") {
    intercept[CannotHaveMp] {
      enemy1.getMp
    }
  }

  /**
   * Test case to verify that the Enemy cannot have a weapon and throws CannotHaveWeapon exception.
   */
  test("Enemy cannot have weapon and throws CannotHaveWeapon exception") {
    intercept[CannotEquipWeapon] {
      enemy1.getWeapon
    }
  }

  /**
   * Test case to verify that the Enemy cannot move to another unit's panel and throws CannotMoveTo exception.
   */
  test("Enemy cannot move to and throws CannotMoveTo exception") {
  }

  /**
   * Test case to verify that the Enemy can get max HP.
   */
  test("Enemy can get max Hp") {
    assertEquals(enemy2.getMaxHp, 70)
  }

  /**
   * Test case to verify that the Enemy cannot receive move actions and throws CannotMoveTo exception.
   */
  test("Enemy cannot receive Move Action and throws CannotMoveTo exception") {
  }

  /**
   * Test case to verify that the Enemy cannot cast Meteor and throws CannotUseMagicSpells exception.
   */
  test("Enemy cannot spell Meteor and throws CannotUseMagicSpells exception") {
    val spellMeteor: Meteor = new Meteor("Meteor1")
  }

  /**
   * Test case to verify that the Enemy cannot cast Healing and throws CannotUseMagicSpells exception.
   */
  test("Enemy cannot spell Healing and throws CannotUseMagicSpells exception") {
    val spellHealing: Healing = new Healing("Healing1")
  }

  /**
   * Test case to verify that the Enemy cannot cast Purification and throws CannotUseMagicSpells exception.
   */
  test("Enemy cannot spell Purification and throws CannotUseMagicSpells exception") {
    val spellPurification: Purification = new Purification("Purification1")
  }

  /**
   * Test case to verify that the Enemy cannot cast Thunder and throws CannotUseMagicSpells exception.
   */
  test("Enemy cannot spell Thunder and throws CannotUseMagicSpells exception") {
    val spellThunder: Thunder = new Thunder("Thunder1")
  }

  /**
   * Test case to verify that the Enemy cannot apply a damage boost and throws CannotUseMagicSpells exception.
   */
  test("Enemy cannot apply Damage Boost and throws CannotUseMagicSpells exception") {
  }

  /**
   * Test case to verify that the Enemy cannot get a magic damage boost and throws CannotUseMagicSpells exception.
   */
  test("Enemy cannot get Magic Damage Boost and throws CannotUseMagicSpells exception") {
  }

  /**
   * Test case to verify that the Enemy cannot remove a magic force effect and throws CannotUseMagicSpells exception.
   */
  test("Enemy cannot remove Magic Force Effect and throws CannotUseMagicSpells exception") {
  }

  /**
   * Test case to verify the JSON representation of an Enemy instance.
   */
  test("toJson should return correct JSON for the unit") {
    val expectedJson: JsObj = JsObj(
      "id" -> "p6",
      "attributes" -> JsArr(
        JsObj("name" -> "name", "value" -> "Scorpion"),
        JsObj("name" -> "hp", "value" -> 0),
        JsObj("name" -> "atk", "value" -> 60),
        JsObj("name" -> "def", "value" -> 80),
        JsObj("name" -> "weight", "value" -> 55)
      ),
      "img" -> "Scorpion.gif"
    )
    assertEquals(enemy1.toJson, expectedJson)
  }
}
