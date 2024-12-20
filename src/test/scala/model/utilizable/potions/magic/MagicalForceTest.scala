package model.utilizable.potions.magic

import model.entity.character.common.Archer
import munit.FunSuite
import util.Json.{*, given}

/**
 * Class `MagicalForceTest` is a unit test suite for the `MagicalForce` potion class,
 * specifically testing the attributes, equality, and JSON representation of the potion.
 */
class MagicalForceTest extends FunSuite{
  /**
   * Create instances of the Magical Force potion for testing.
   */
  val magicalForce1: MagicalForce = new MagicalForce("D3" ,"Magical Force Potion")
  val magicalForce2: MagicalForce = new MagicalForce("D2" ,"Magical Force Potion")
  val magicalForce3: MagicalForce = new MagicalForce("D3" ,"Magical Force Potionv2")

  /**
   * Test to verify the attributes of the Magical Force potion.
   */
  test("Magical Force Attributes"){
    assertEquals(magicalForce1.getName, "Magical Force Potion")
    assertEquals(magicalForce1.getId, "D3")
  }

  /**
   * Test to check equality when two potions have the same name.
   */
  test("Equals should return true if the potion have the same name") {
    assertEquals(magicalForce1, magicalForce2)
  }

  /**
   * Test to check equality of the same potion instance.
   */
  test("Equals should return true when comparing the same potion") {
    assertEquals(magicalForce1, magicalForce1)
  }

  /**
   * Test to check inequality when potions have different names.
   */
  test("Equals should return false if the potion don't have the same name") {
    assertEquals(magicalForce1 == magicalForce3, false)
  }

  /**
   * Test to ensure non-potion objects are not equal to potions.
   */
  test("Equals should return false when comparing with a non-potion") {
    val nonHeal: Archer = new Archer("p1", "Didi", 70, 50, 65)
    assertEquals(magicalForce1 == nonHeal, false)
  }

  /**
   * Test to verify the JSON representation of the Magical Force potion.
   */
  test("toJson should return correct JSON for the potion") {
    val expectedJson: JsObj = JsObj(
      "id" -> "D2",
      "name" -> "Magical Force Potion"
    )
    assertEquals(magicalForce2.toJson, expectedJson)
  }
}
