package model.utilizable.potions.common

import model.entity.character.common.Archer
import munit.FunSuite
import util.Json.{*, given}

/**
 * Test suite for the `Strength` potion class.
 *
 * This suite verifies the functionality of the `Strength` potion, including
 * its attributes, equality comparison, and JSON serialization.
 */
class StrengthTest extends FunSuite{

  /**
   * Create instances of the Strength potion for testing.
   */
  val strength1: Strength = new Strength("D5", "Strength Potion")
  val strength2: Strength = new Strength("D2", "Strength Potion")
  val strength3: Strength = new Strength("D5", "Strength Potionv2")

  /**
   * Test to verify the attributes of the Strength potion.
   */
  test("Strength Attributes"){
    assertEquals(strength1.getName, "Strength Potion")
    assertEquals(strength1.getId, "D5")
  }

  /**
   * Test to check equality when two potions have the same name.
   */
  test("Equals should return true if the potion have the same name") {
    assertEquals(strength1, strength2)
  }

  /**
   * Test to check equality of the same potion instance.
   */
  test("Equals should return true when comparing the same potion") {
    assertEquals(strength1, strength1)
  }

  /**
   * Test to check inequality when potions have different names.
   */
  test("Equals should return false if the potion don't have the same name") {
    assertEquals(strength1 == strength3, false)
  }

  /**
   * Test to ensure non-potion objects are not equal to potions.
   */
  test("Equals should return false when comparing with a non-potion") {
    val nonHeal: Archer = new Archer("p1", "Didi", 70, 50, 65)
    assertEquals(strength1 == nonHeal, false)
  }

  /**
   * Test to verify the JSON representation of the Strength potion.
   */
  test("toJson should return correct JSON for the potion") {
    val expectedJson: JsObj = JsObj(
      "id" -> "D5",
      "name" -> "Strength Potion"
    )
    assertEquals(strength1.toJson, expectedJson)
  }
}
