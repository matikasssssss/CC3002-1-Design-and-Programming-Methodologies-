package model.entity.character.magic

import model.actions.Actions
import model.actions.equip.AWeapon
import model.entity.Global
import model.exception.ActionNotFound
import model.utilizable.potions.Potions
import model.utilizable.potions.common.{Heal, Strength}
import model.utilizable.potions.magic.Manna
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.magic.Cane
import munit.FunSuite
import util.Json.{*, given}

import scala.collection.mutable.ListBuffer

/**
 * Class `WhiteMageTest` is a suite of unit tests for the `WhiteMage` character,
 * verifying the functionality of its methods and properties related to magic actions.
 *
 * The tests include checking attributes, weapon assignments, equality comparisons,
 * and the JSON representation of the white mage character.
 */
class WhiteMageTest extends FunSuite {

  /**
   * Weapon
   */
  val cane1: Cane = Cane("C4", "Cane IV", 60, 15, whiteMage1, 90)
  val cane2: Cane = Cane("C4", "Cane IV", 60, 15, whiteMage3, 90)

  /**
   * Character
   */
  val whiteMage1: WhiteMage = new WhiteMage("p5", "Terra", 70, 60, 80, 55){
    weaponsSlot = Some(cane1)
  }
  val whiteMage2: WhiteMage = new WhiteMage("p5", "Terra", 70, 60, 80, 55)
  val whiteMage3: WhiteMage = new WhiteMage("p5", "White Mage", 70, 60, 80, 55) {
    weaponsSlot = Some(cane1)
  }
  val whiteMage4: WhiteMage = new WhiteMage("p5", "Terra", 60, 60, 80, 55)
  val whiteMage5: WhiteMage = new WhiteMage("p5", "Terra", 70, 65, 80, 55)
  val whiteMage6: WhiteMage = new WhiteMage("p5", "Terra", 70, 60, 85, 55)
  val whiteMage7: WhiteMage = new WhiteMage("p5", "Terra", 70, 60, 80, 50)

  /**
   * Potion
   */
  val manna: Manna = new Manna("D2", "Manna Potion")

  test("White mage can find actions in his action list") {
    val heal: Potions = new Heal("heal1", "Heal potion")
    val strength: Potions = new Strength("strength1", "Strength potion")
    val whiteMage: Global = new WhiteMage("p5", "Terra", 70, 60, 80, 55)
    val cane: Cane = Cane("C4", "Cane I", 40, 10, whiteMage, 80)
    whiteMage.potions ++= List(heal, strength)
    whiteMage.weapons ++= List(cane)
    whiteMage.findActionById("Healing1")

    intercept[ActionNotFound] {
      whiteMage.findActionById("3")
    }
  }

  /**
   * Test case to verify the attributes of the White Mage character.
   */
  test("White Mage attributes") {
    assertEquals(whiteMage1.getId, "p5")
    assertEquals(whiteMage1.getName, "Terra")
    assertEquals(whiteMage1.getHp, 70)
    assertEquals(whiteMage1.getDef, 60)
    assertEquals(whiteMage1.getWeight, 80.0)
    assertEquals(whiteMage1.getMp, 55)
  }

  test("Black mage can get the atk from their weapons") {
    val whiteMage: WhiteMage = new WhiteMage("p5", "Terra", 70, 60, 80, 55)
    val cane: Cane = Cane("C4", "Cane I", 40, 10, whiteMage, 80)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    whiteMage.doAction(equipCane, whiteMage)
    whiteMage.getAtk
    assertEquals(whiteMage.getAtk, 40)
  }

  /**
   * Test case to verify that the White Mage character has a weapon assigned.
   */
  test("White Mage has weapon") {
    val blackMageWeapon: Option[Weapons] = whiteMage1.getWeapon
    assertEquals(blackMageWeapon, Some(cane1))
  }

  /**
   * Test case to verify equality of two White Mage instances with the same attributes.
   */
  test("Equals should return true if the units have the same attributes") {
    assertEquals(whiteMage1, whiteMage2)
  }

  /**
   * Test case to verify that a White Mage instance is equal to itself.
   */
  test("Equals should return true when comparing the same units") {
    assertEquals(whiteMage1, whiteMage1)
  }

  /**
   * Test case to verify that the HP of the White mage cannot go below zero.
   */
  test("White mage HP should not be negative") {
    whiteMage1.decreaseHp(100) // Exceeds current HP
    assertEquals(whiteMage1.getHp, 0) // HP should be 0, not negative
  }


  /**
   * Test case to verify inequality of White Mage instances with different attributes.
   */
  test("Equals should return false if the unit don't have the same attributes") {
    assertEquals(whiteMage1 == whiteMage3, false)
    assertEquals(whiteMage1 == whiteMage4, false)
    assertEquals(whiteMage1 == whiteMage5, false)
    assertEquals(whiteMage1 == whiteMage6, false)
    assertEquals(whiteMage1 == whiteMage7, false)
  }

  /**
   * Test case to verify inequality when comparing a White Mage instance with a non-unit.
   */
  test("Equals should return false when comparing with a non-unit") {
    val nonUnit: Heal = new Heal("D6", "Heal Potion")
    assertEquals(whiteMage1 == nonUnit, false)
  }

  /**
   * Test case to verify the JSON representation of a White Mage instance.
   */
  test("toJson should return correct JSON for the unit") {
    val expectedJson: JsObj = JsObj(
      "id" -> "p5",
      "attributes" -> JsArr(
        JsObj("name" -> "name", "value" -> "Terra"),
        JsObj("name" -> "hp", "value" -> 0),
        JsObj("name" -> "def", "value" -> 60),
        JsObj("name" -> "weight", "value" -> 80),
        JsObj("name" -> "mp", "value" -> 55),
      ),
      "img" -> "Terra.gif"
    )
    assertEquals(whiteMage1.toJson, expectedJson)
  }
}