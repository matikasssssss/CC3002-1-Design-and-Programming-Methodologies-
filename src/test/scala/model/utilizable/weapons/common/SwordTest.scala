package model.utilizable.weapons.common

import model.entity.character.common.{Archer, Knight}
import model.utilizable.weapons.Weapons
import munit.FunSuite
import util.Json.{*, given}

/**
 * Class `SwordTest` is a unit test suite for the `Sword` weapon class,
 * specifically testing the attributes, equality, and JSON representation of the sword.
 */
class SwordTest extends FunSuite{

  /**
   * Create instances of characters for testing
   */
  val knight1: Knight = new Knight("p3", "Black Knight", 60, 60, 85)
  val knight2: Knight = new Knight("p3", "Black Knight Crazy", 50, 50, 85)

  /**
   * Create instances of Sword weapons for testing
   */
  val sword1: Weapons = new Sword("C1", "Sword V", 85, 40, knight1)
  val sword2: Weapons = new Sword("C1", "Sword V", 85, 40, knight1)
  val sword3: Weapons = new Sword("C2", "Sword V", 85, 40, knight2)
  val sword4: Weapons = new Sword("C2", "Sword IV", 85, 40, knight1)
  val sword5: Weapons = new Sword("C2", "Sword V", 75, 40, knight1)
  val sword6: Weapons = new Sword("C2", "Sword V", 85, 45, knight1)

  /**
   * Test to verify the attributes of the Sword weapon.
   */
  test("Sword Attributes") {
    assertEquals(sword1.getName, "Sword V")
    assertEquals(sword1.getAtk, 85)
    assertEquals(sword1.getWeight, 40.0)
    assertEquals(sword1.getOwner, knight1)
    assertEquals(sword1.getId, "C1")
  }

  /**
   * Test to check equality when two swords have the same attributes.
   */
  test("Equals should return true if the weapon have the same attributes") {
    assertEquals(sword1, sword2)
  }

  /**
   * Test to check equality of the same sword instance.
   */
  test("Equals should return true when comparing the same weapon") {
    assertEquals(sword1, sword1)
  }

  /**
   * Test to check inequality when swords have different attributes.
   */
  test("Equals should return false if the weapon don't have the same attributes") {
    assertEquals(sword1 == sword3, false)
    assertEquals(sword1 == sword4, false)
    assertEquals(sword1 == sword5, false)
    assertEquals(sword1 == sword6, false)
  }

  /**
   * Test to ensure non-weapon objects are not equal to swords.
   */
  test("Equals should return false when comparing with a non-weapon") {
    val nonHeal: Archer = new Archer("p1", "Dudu", 70, 50, 65)
    assertEquals(sword1 == nonHeal, false)
  }

  /**
   * Test to verify the JSON representation of the Sword weapon.
   */
  test("toJson should return correct JSON for the weapon") {
    val expectedJson: JsObj = JsObj(
      "id" -> "C1",
      "name" -> "Sword V",
      "atk" -> 85,
      "weight" -> 40,
      "owner" -> knight1.toJson
    )
    assertEquals(sword1.toJson, expectedJson)
  }
}
