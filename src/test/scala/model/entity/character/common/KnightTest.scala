package model.entity.character.common

import model.exception.{ActionNotFound, CannotHaveMp}
import model.utilizable.potions.Potions
import model.utilizable.potions.common.{Heal, Strength}
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.common.{Bow, Sword}
import munit.FunSuite
import util.Json.{*, given}


/**
 * Class `KnightTest` is a suite of unit tests for the `Knight` character,
 * verifying the functionality of its methods and properties related to knight actions.
 *
 * The tests include checking attributes, weapon assignments, equality comparisons,
 * and the JSON representation of the knight character.
 */
class KnightTest extends FunSuite{

  /**
   * Weapon
    */
  val sword1: Weapons = new Sword("C1", "Sword V", 85, 40, knight1)
  val sword2: Weapons = new Sword("C1", "Sword V", 85, 40, knight3)

  /**
   * Character
   */
  val knight1: Knight = new Knight("p2", "Gogo", 60, 60, 85) {
    weaponsSlot = Some(sword1)
  }
  val knight2: Knight = new Knight("p2", "Gogo", 60, 60, 85) 
  val knight3: Knight = new Knight("p2", "Black Knight plus", 60, 60, 85) {
    weaponsSlot = Some(sword2)
  }
  val knight4: Knight = new Knight("p2", "Gogo", 50, 60, 85) 
  val knight5: Knight = new Knight("p2", "Gogo", 60, 65, 85)
  val knight6: Knight = new Knight("p2", "Gogo", 60, 60, 80)

  //Equip Weapon
  //knight1.equipWeapon(sword)

  /**
   * Potion
   */
  val heal: Heal = new Heal("D6", "Heal Potion")

  //Add potion and heal to inventory
  //knight1.addToInventory(sword)
  //knight1.addToInventory(heal)

  /**
   * Test case to verify the attributes of the Knight character.
   */
  test("Knight attributes"){
    assertEquals(knight1.getId, "p2")
    assertEquals(knight1.getName, "Gogo")
    assertEquals(knight1.getHp, 60)
    assertEquals(knight1.getDef, 60)
    assertEquals(knight1.getWeight, 85.0)
  }

  /**
   * Test case to verify that the Knight character has a weapon assigned.
   */
  test("Knight has weapon") {
    val knightWeapon: Option[Weapons] = knight1.getWeapon
    assertEquals(knightWeapon, Some(sword1))
  }
  
  test("Knight has maximum attributes") {
    assertEquals(knight1.getMaxHp, 60)
    assertEquals(knight1.getMaxDef, 60)
  }

  test("Knight does not have maximum mp and atkm") {
    intercept[CannotHaveMp] {
      knight1.getMaxMp
    }
    assertEquals(knight1.getAtkm, 0)
  }

  test("Knight can find actions in his action list") {
    val heal: Potions = new Heal("heal1", "Heal potion")
    val strength: Potions = new Strength("strength1", "Strength potion")
    val knight: Knight = new Knight("p2", "Gogo", 60, 60, 85)
    val bow: Weapons = new Bow("C3", "Bow V", 65, 30, knight)
    knight.potions ++= List(heal, strength)
    knight.weapons ++= List(bow)
    knight.findActionById("2")

    intercept[ActionNotFound] {
      knight.findActionById("Healing")
    }
  }

  /**
   * Test case to verify equality of two Knight instances with the same attributes.
   */
  test("Equals should return true if the units have the same attributes") {
    assertEquals(knight1, knight2)
  }

  /**
   * Test case to verify that the HP of the Knight cannot go below zero.
   */
  test("Knight HP should not be negative") {
    knight1.decreaseHp(100) // Exceeds current HP
    assertEquals(knight1.getHp, 0) // HP should be 0, not negative
  }

  /**
   * Test case to verify that a Knight instance is equal to itself.
   */
  test("Equals should return true when comparing the same units") {
    assertEquals(knight1, knight1)
  }

  /**
   * Test case to verify inequality of Knight instances with different attributes.
   */
  test("Equals should return false if the unit don't have the same attributes") {
    assertEquals(knight1 == knight3, false)
    assertEquals(knight1 == knight4, false)
    assertEquals(knight1 == knight5, false)
    assertEquals(knight1 == knight6, false)
  }

  /**
   * Test case to verify inequality when comparing a Knight instance with a non-unit.
   */
  test("Equals should return false when comparing with a non-unit") {
    val nonUnit: Heal = new Heal("D6", "Heal Potion")
    assertEquals(knight1 == nonUnit, false)
  }

  /**
   * Test case to verify the JSON representation of a Knight instance.
   */
  test("toJson should return correct JSON for the unit") {
    val expectedJson: JsObj = JsObj(
      "id" -> "p2",
      "attributes" -> JsArr(
        JsObj("name" -> "name", "value" -> "Gogo"),
        JsObj("name" -> "hp", "value" -> 0),
        JsObj("name" -> "def", "value" -> 60),
        JsObj("name" -> "weight", "value" -> 85),
      ),
      "img" -> "Gogo.gif"
    )
    assertEquals(knight1.toJson, expectedJson)
  }
}
