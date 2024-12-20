package model.utilizable.weapons.common

import model.entity.character.common.{Archer, Thief}
import model.utilizable.weapons.Weapons
import munit.FunSuite
import util.Json.{*, given}

/**
 * Class `DaggerTest` is a unit test suite for the `Dagger` weapon class,
 * specifically testing the attributes, equality, and JSON representation of the dagger.
 */
class DaggerTest extends FunSuite {

  /**
   * Create instances of characters for testing
   */
  val thief1: Thief = new Thief("p2", "Jadue", 70, 65, 75)
  val thief2: Thief = new Thief("p1", "Hamilton", 65, 45, 65)

  /**
   * Create instances of Dagger weapons for testing
   */
  val dagger1: Weapons = new Dagger("C2", "Dagger II", 35, 10, thief1)
  val dagger2: Weapons = new Dagger("C2", "Dagger II", 35, 10, thief1)
  val dagger3: Weapons = new Dagger("C1", "Dagger II", 35, 10, thief2)
  val dagger4: Weapons = new Dagger("C2", "Dagger II", 25, 10, thief1)
  val dagger5: Weapons = new Dagger("C2", "Dagger II", 35, 15, thief1)
  val dagger6: Weapons = new Dagger("C2", "Dagger I", 35, 10, thief1)

  /**
   * Test to verify the attributes of the Dagger weapon.
   */
  test("Dagger Attributes") {
    assertEquals(dagger1.getName, "Dagger II")
    assertEquals(dagger1.getAtk, 35)
    assertEquals(dagger1.getWeight, 10.0)
    assertEquals(dagger1.getOwner, thief1)
    assertEquals(dagger1.getId, "C2")
  }

  /**
   * Test to check equality when two daggers have the same attributes.
   */
  test("Equals should return true if the weapon have the same attributes") {
    assertEquals(dagger1, dagger2)
  }

  /**
   * Test to check equality of the same dagger instance.
   */
  test("Equals should return true when comparing the same weapon") {
    assertEquals(dagger1, dagger1)
  }

  /**
   * Test to check inequality when daggers have different attributes.
   */
  test("Equals should return false if the weapon don't have the same attributes") {
    assertEquals(dagger1 == dagger3, false)
    assertEquals(dagger1 == dagger4, false)
    assertEquals(dagger1 == dagger5, false)
    assertEquals(dagger1 == dagger6, false)
  }

  /**
   * Test to ensure non-weapon objects are not equal to daggers.
   */
  test("Equals should return false when comparing with a non-weapon") {
    val nonHeal: Archer = new Archer("p1", "Dudu", 70, 50, 65)
    assertEquals(dagger1 == nonHeal, false)
  }

  /**
   * Test to verify the JSON representation of the Dagger weapon.
   */
  test("toJson should return correct JSON for the weapon") {
    val expectedJson: JsObj = JsObj(
      "id" -> "C2",
      "name" -> "Dagger II",
      "atk" -> 35,
      "weight" -> 10,
      "owner" -> thief1.toJson
    )
    assertEquals(dagger1.toJson, expectedJson)
  }
}