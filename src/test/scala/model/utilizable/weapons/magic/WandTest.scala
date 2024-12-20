package model.utilizable.weapons.magic

import model.entity.character.magic.BlackMage
import munit.FunSuite
import util.Json.{*, given}

/**
 * Class `WandTest` is a unit test suite for the `Wand` weapon class,
 * specifically testing the attributes, equality, and JSON representation of the wand.
 */
class WandTest extends FunSuite{

  /**
   * Create instances of characters for testing
   */
  val blackMage1: BlackMage = new BlackMage("p5", "Black Mage", 60, 60, 85, 60)
  val blackMage2: BlackMage = new BlackMage("p5", "Black Mage Pepe", 65, 70, 65, 40)

  /**
   * Create instances of Wand weapons for testing
   */
  val wand1: Wand = new Wand("C0", "Wand II", 20, 5, blackMage1, 90)
  val wand2: Wand = new Wand("C0", "Wand II", 20, 5, blackMage1, 90)
  val wand3: Wand = new Wand("C1", "Wand II", 20, 5, blackMage2, 90)
  val wand4: Wand = new Wand("C0", "Wand I", 20, 5, blackMage1, 90)
  val wand5: Wand = new Wand("C0", "Wand II", 10, 5, blackMage1, 90)
  val wand6: Wand = new Wand("C0", "Wand II", 20, 10, blackMage1, 90)

  /**
   * Test to verify the attributes of the Wand weapon.
   */
  test("Cane Attributes") {
    assertEquals(wand1.getName, "Wand II")
    assertEquals(wand1.getAtk, 20)
    assertEquals(wand1.getWeight, 5.0)
    assertEquals(wand1.getOwner, blackMage1)
    assertEquals(wand1.getAtkm, 90)
    assertEquals(wand1.getId, "C0")
  }

  /**
   * Test to check equality when two wands have the same attributes.
   */
  test("Equals should return true if the weapon have the same attributes") {
    assertEquals(wand1, wand2)
  }

  /**
   * Test to check equality of the same wand instance.
   */
  test("Equals should return true when comparing the same weapon") {
    assertEquals(wand1, wand1)
  }

  /**
   * Test to check inequality when wands have different attributes.
   */
  test("Equals should return false if the weapon don't have the same attributes") {
    assertEquals(wand1 == wand3, false)
    assertEquals(wand1 == wand4, false)
    assertEquals(wand1 == wand5, false)
    assertEquals(wand1 == wand6, false)
  }

  /**
   * Test to ensure non-weapon objects are not equal to wands.
   */
  test("Equals should return false when comparing with a non-weapon") {
    val nonHeal: BlackMage = new BlackMage("p5", "Black Mage", 60, 60, 85, 60)
    assertEquals(wand1 == nonHeal, false)
  }

  /**
   * Test to verify the JSON representation of the Wand weapon.
   */
  test("toJson should return correct JSON for the weapon") {
    val expectedJson: JsObj = JsObj(
      "id" -> "C0",
      "name" -> "Wand II",
      "atk" -> 20,
      "weight" -> 5,
      "owner" -> blackMage1.toJson,
      "atkm" -> 90
    )
    assertEquals(wand1.toJson, expectedJson)
  }
}
