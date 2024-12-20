package model.utilizable.potions.magic

import model.entity.character.common.Archer
import munit.FunSuite
import util.Json.{*, given}

/**
 * Class `MannaTest` is a unit test suite for the `Manna` potion class,
 * specifically testing the attributes, equality, and JSON representation of the potion.
 */
class MannaTest extends FunSuite{
  /**
   * Create instances of the Manna potion for testing.
   */
  val manna1: Manna = new Manna("D2", "Manna Potion")
  val manna2: Manna = new Manna("D4", "Manna Potion")
  val manna3: Manna = new Manna("D2", "Manna Potionv2")

  /**
   * Test to verify the attributes of the Manna potion.
   */
  test("Manna Attributes"){
    assertEquals(manna1.getName, "Manna Potion")
    assertEquals(manna1.getId, "D2")
  }

  /**
   * Test to check equality when two potions have the same name.
   */
  test("Equals should return true if the potion have the same name") {
    assertEquals(manna1, manna2)
  }

  /**
   * Test to check equality of the same potion instance.
   */
  test("Equals should return true when comparing the same potion") {
    assertEquals(manna1, manna1)
  }

  /**
   * Test to check inequality when potions have different names.
   */
  test("Equals should return false if the potion don't have the same name") {
    assertEquals(manna1 == manna3, false)
  }

  /**
   * Test to ensure non-potion objects are not equal to potions.
   */
  test("Equals should return false when comparing with a non-potion") {
    val nonHeal: Archer = new Archer("p1", "Didi", 70, 50, 65)
    assertEquals(manna1 == nonHeal, false)
  }

  /**
   * Test to verify the JSON representation of the Manna potion.
   */
  test("toJson should return correct JSON for the potion") {
    val expectedJson: JsObj = JsObj(
      "id" -> "D4",
      "name" -> "Manna Potion"
    )
    assertEquals(manna2.toJson, expectedJson)
  }
}