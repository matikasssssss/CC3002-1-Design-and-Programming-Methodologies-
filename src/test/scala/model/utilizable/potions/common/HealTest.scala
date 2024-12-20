package model.utilizable.potions.common

import model.entity.character.common.Archer
import munit.FunSuite
import util.Json.{*, given}

/**
 * Class `HealTest` is a unit test suite for the `Heal` potion class,
 * specifically testing its attributes and functionality.
 */
class HealTest extends FunSuite{

  /**
   * Create instances of Heal potions for testing.
   */
  val heal1: Heal = new Heal("D6", "Heal Potion")
  val heal2: Heal = new Heal("D1", "Heal Potion")
  val heal3: Heal = new Heal("D1", "Heal Potionv1")

  /**
   * Test to verify the attributes of the Heal potion.
   */
  test("Heal Attributes"){
    assertEquals(heal1.getName, "Heal Potion")
    assertEquals(heal1.getId, "D6")
  }

  /**
   * Test to check equality based on the potion's name.
   */
  test("Equals should return true if the potion have the same name"){
    assertEquals(heal1, heal2)
  }

  /**
   * Test to check equality when comparing the same potion instance.
   */
  test("Equals should return true when comparing the same potion") {
    assertEquals(heal1, heal1)
  }

  /**
   * Test to check inequality based on different potion names.
   */
  test("Equals should return false if the potion don't have the same name") {
    assertEquals(heal1 == heal3, false)
  }

  /**
   * Test to ensure equality check returns false when comparing with a non-potion object.
   */
  test("Equals should return false when comparing with a non-potion") {
    val nonHeal: Archer = new Archer("p1", "Didi", 70, 50, 65)
    assertEquals(heal1 == nonHeal, false)
  }

  /**
   * Test to verify the JSON representation of the Heal potion.
   */
  test("toJson should return correct JSON for the potion"){
    val expectedJson: JsObj = JsObj(
      "id"->"D1",
      "name"->"Heal Potion"
    )
    assertEquals(heal2.toJson, expectedJson)
  }
}
