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
 * Class `BlackMageTest` is a suite of unit tests for the `BlackMage` character,
 * verifying the functionality of its methods and properties related to magic actions.
 *
 * The tests include checking attributes, weapon assignments, equality comparisons,
 * and the JSON representation of the black mage character.
 */
class BlackMageTest extends FunSuite{
  /**
   * Weapon
   */
  val cane1: Cane = Cane("C4", "Cane I", 40, 10, blackMage1, 80)
  val cane2: Cane = Cane("C4", "Cane I", 40, 10, blackMage3, 80)

  /**
   * Character
   */
  val blackMage1: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 85, 60){
    weaponsSlot = Some(cane1)
  }
  val blackMage2: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 85, 60)
  val blackMage3: BlackMage = new BlackMage("p4", "Black Mage", 60, 60, 85, 60) {
    weaponsSlot = Some(cane2)
  }
  val blackMage4: BlackMage = new BlackMage("p4", "Shadow", 50, 60, 85, 60)
  val blackMage5: BlackMage = new BlackMage("p4", "Shadow", 60, 65, 85, 60)
  val blackMage6: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 90, 60)
  val blackMage7: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 85, 65)
  val blackMage8: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 85, 60)

  //Equip Weapon
  //blackMage.equipWeapon(cane)

  /**
   * Potion
   */
  val manna: Manna = new Manna("D2", "Manna Potion")

  test("Black mage can find actions in his action list") {
    val heal: Potions = new Heal("heal1", "Heal potion")
    val strength: Potions = new Strength("strength1", "Strength potion")
    val blackMage: Global = new BlackMage("p4", "Shadow", 60, 60, 85, 60)
    val cane: Cane = Cane("C4", "Cane I", 40, 10, blackMage, 80)
    blackMage.potions ++= List(heal, strength)
    blackMage.weapons ++= List(cane)
    blackMage.findActionById("3")

    intercept[ActionNotFound] {
      blackMage.findActionById("Healing1")
    }
  }

  test("Black mage can get the atk from their weapons"){
    val blackMage: Global = new BlackMage("p4", "Shadow", 60, 60, 85, 60)
    val cane: Cane = Cane("C4", "Cane I", 40, 10, blackMage, 80)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    blackMage.doAction(equipCane, blackMage)
    blackMage.getAtk
    assertEquals(blackMage.getAtk, 40)
  }

  test("If don't have weapon, atk equal 0"){
    val blackMage: Global = new BlackMage("p4", "Shadow", 60, 60, 85, 60)
    assertEquals(blackMage.getAtk, 0)
  }

  test("Magic characters have attribute max def"){
    val blackMage: Global = new BlackMage("p4", "Shadow", 60, 60, 85, 60)
    assertEquals(blackMage.getMaxDef, 60)
  }


  /**
   * Test case to verify the attributes of the Black Mage character.
   */
  test("Black Mage attributes") {
    assertEquals(blackMage1.getId, "p4")
    assertEquals(blackMage1.getName, "Shadow")
    assertEquals(blackMage1.getHp, 60)
    assertEquals(blackMage1.getDef, 60)
    assertEquals(blackMage1.getWeight, 85.0)
    assertEquals(blackMage1.getMp, 60)
  }

  /**
   * Test case to verify that the Black Mage character has a weapon assigned.
   */
  test("Black Mage has weapon") {
    val blackMageWeapon: Option[Weapons] = blackMage1.getWeapon
    assertEquals(blackMageWeapon, Some(cane1))
  }

  //test("Black Mage has inventory") {
  //  val blackMageInventory: ListBuffer[Utilizable] = blackMage.getInventory
  //  assertEquals(blackMageInventory, ListBuffer(cane, manna))
  //}

  /**
   * Test case to verify that the HP of the Black mage cannot go below zero.
   */
  test("Black mage HP should not be negative") {
    blackMage1.decreaseHp(100) // Exceeds current HP
    assertEquals(blackMage1.getHp, 0) // HP should be 0, not negative
  }

  /**
   * Test case to verify equality of two Black Mage instances with the same attributes.
   */
  test("Equals should return true if the units have the same attributes") {
    assertEquals(blackMage2, blackMage8)
  }

  /**
   * Test case to verify that a Black Mage instance is equal to itself.
   */
  test("Equals should return true when comparing the same units") {
    assertEquals(blackMage1, blackMage1)
  }

  /**
   * Test case to verify inequality of Black Mage instances with different attributes.
   */
  test("Equals should return false if the unit don't have the same attributes") {
    assertEquals(blackMage1 == blackMage3, false)
    assertEquals(blackMage1 == blackMage4, false)
    assertEquals(blackMage1 == blackMage5, false)
    assertEquals(blackMage1 == blackMage6, false)
    assertEquals(blackMage1 == blackMage7, false)
  }

  /**
   * Test case to verify inequality when comparing a Black Mage instance with a non-unit.
   */
  test("Equals should return false when comparing with a non-unit") {
    val nonUnit: Heal = new Heal("D6", "Heal Potion")
    assertEquals(blackMage1 == nonUnit, false)
  }

  /**
   * Test case to verify the JSON representation of a Black Mage instance.
   */
  test("toJson should return correct JSON for the unit") {
    val expectedJson: JsObj = JsObj(
      "id" -> "p4",
      "attributes" -> JsArr(
        JsObj("name" -> "name", "value" -> "Shadow"),
        JsObj("name" -> "hp", "value" -> 0),
        JsObj("name" -> "def", "value" -> 60),
        JsObj("name" -> "weight", "value" -> 85),
        JsObj("name" -> "mp", "value" -> 60),
      ),
      "img" -> "Shadow.gif"
    )
    assertEquals(blackMage1.toJson, expectedJson)
  }
}
