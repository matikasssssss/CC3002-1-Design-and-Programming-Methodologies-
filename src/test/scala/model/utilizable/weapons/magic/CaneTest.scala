package model.utilizable.weapons.magic

import model.entity.character.magic.WhiteMage
import munit.FunSuite
import util.Json.{*, given}

/**
 * Class `CaneTest` is a unit test suite for the `Cane` weapon class,
 * specifically testing the attributes, equality, and JSON representation of the cane.
 */
class CaneTest extends FunSuite {

  /**
   * Create instances of characters for testing
   */
  val whiteMage1: WhiteMage = new WhiteMage("p4", "White Mage", 70, 60, 80, 55)
  val whiteMage2: WhiteMage = new WhiteMage("p5", "White Mage Pluto", 75, 50, 60, 35)

  /**
   * Create instances of Cane weapons for testing
   */
  val cane1: Cane = new Cane("C4", "Cane I", 40, 10, whiteMage1, 80)
  val cane2: Cane = new Cane("C4", "Cane I", 40, 10, whiteMage1, 80)
  val cane3: Cane = new Cane("C5", "Cane I", 40, 10, whiteMage2, 50)
  val cane4: Cane = new Cane("C5", "Cane II", 40, 10, whiteMage1, 80)
  val cane5: Cane = new Cane("C5", "Cane I", 30, 10, whiteMage1, 80)
  val cane6: Cane = new Cane("C5", "Cane I", 40, 15, whiteMage1, 80)

  /**
   * Test to verify the attributes of the Cane weapon.
   */
  test("Cane Attributes") {
    assertEquals(cane1.getName, "Cane I")
    assertEquals(cane1.getAtk, 40)
    assertEquals(cane1.getWeight, 10.0)
    assertEquals(cane1.getOwner, whiteMage1)
    assertEquals(cane1.getAtkm, 80)
    assertEquals(cane1.getId, "C4")
  }

  /**
   * Test to check equality when two canes have the same attributes.
   */
  test("Equals should return true if the weapon have the same attributes") {
    assertEquals(cane1, cane2)
  }

  /**
   * Test to check equality of the same cane instance.
   */
  test("Equals should return true when comparing the same weapon") {
    assertEquals(cane1, cane1)
  }

  /**
   * Test to check inequality when canes have different attributes.
   */
  test("Equals should return false if the weapon don't have the same attributes") {
    assertEquals(cane1 == cane3, false)
    assertEquals(cane1 == cane4, false)
    assertEquals(cane1 == cane5, false)
    assertEquals(cane1 == cane6, false)
  }

  /**
   * Test to ensure non-weapon objects are not equal to canes.
   */
  test("Equals should return false when comparing with a non-weapon") {
    val nonHeal: WhiteMage = new WhiteMage("p4", "White Mage", 70, 60, 80, 55)
    assertEquals(cane1 == nonHeal, false)
  }

  /**
   * Test to verify the JSON representation of the Cane weapon.
   */
  test("toJson should return correct JSON for the weapon") {
    val expectedJson: JsObj = JsObj(
      "id" -> "C4",
      "name" -> "Cane I",
      "atk" -> 40,
      "weight" -> 10,
      "owner" -> whiteMage1.toJson,
      "atkm" -> 80
    )
    assertEquals(cane1.toJson, expectedJson)
  }
}
