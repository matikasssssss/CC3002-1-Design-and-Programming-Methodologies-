package model.entity.character.common

import model.exception.{ActionNotFound, CannotHaveMp}
import model.utilizable.potions.Potions
import model.utilizable.potions.common.{Heal, Strength}
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.common.{Bow, Dagger}
import munit.FunSuite
import util.Json.{*, given}

/**
 * Class `ThiefTest` is a suite of unit tests for the `Thief` character,
 * verifying the functionality of its methods and properties related to thief actions.
 *
 * The tests include checking attributes, weapon assignments, equality comparisons,
 * and the JSON representation of the thief character.
 */
class ThiefTest extends FunSuite{
  /**
   * Weapon
   */
  val dagger1: Weapons = new Dagger("C2", "Dagger II", 35, 10, thief1)
  val dagger2: Weapons = new Dagger("C2", "Dagger II", 35, 10, thief3)
  /**
   * Character
   */
  val thief1: Thief = new Thief("p4", "Setzer", 70, 65, 75) {
    weaponsSlot = Some(dagger1)
  }
  val thief2: Thief = new Thief("p4", "Setzer", 70, 65, 75)
  val thief3: Thief = new Thief("p4", "Jadue", 70, 65, 75) {
    weaponsSlot = Some(dagger1)
  }
  val thief4: Thief = new Thief("p4", "Setzer", 60, 65, 75)
  val thief5: Thief = new Thief("p4", "Setzer", 70, 60, 75)
  val thief6: Thief = new Thief("p4", "Setzer", 70, 65, 80)

  /**
   * Potion
   */
  val heal: Heal = new Heal("D6", "Heal Potion")

  test("Thief has maximum attributes") {
    assertEquals(thief1.getMaxHp, 70)
    assertEquals(thief1.getMaxDef, 65)
  }

  test("Thief does not have maximum mp and atkm") {
    intercept[CannotHaveMp] {
      thief1.getMaxMp
    }
    assertEquals(thief1.getAtkm, 0)
  }

  test("Thief can find actions in his action list") {
    val heal: Potions = new Heal("heal1", "Heal potion")
    val strength: Potions = new Strength("strength1", "Strength potion")
    val thief: Thief = new Thief("p4", "Setzer", 70, 65, 75)
    val dagger: Weapons = new Dagger("C2", "Dagger II", 35, 10, thief)
    thief.potions ++= List(heal, strength)
    thief.weapons ++= List(dagger)
    thief.findActionById("2")

    intercept[ActionNotFound] {
      thief.findActionById("Healing")
    }
  }

  /**
   * Test case to verify the attributes of the Thief character.
   */
  test("Thief attributes"){
    assertEquals(thief1.getId, "p4")
    assertEquals(thief1.getName, "Setzer")
    assertEquals(thief1.getHp, 70)
    assertEquals(thief1.getDef, 65)
    assertEquals(thief1.getWeight, 75.0)
  }

  /**
   * Test case to verify that the Thief character has a weapon assigned.
   */
  test("Thief has weapon") {
    val thiefWeapon: Option[Weapons] = thief1.getWeapon
    assertEquals(thiefWeapon, Some(dagger1))
  }

  //test("Thief has inventory") {
  //  val thiefInventory: ListBuffer[Utilizable] = thief1.getInventory
  //  assertEquals(thiefInventory, ListBuffer(dagger, heal))
  //}

  /**
   * Test case to verify equality of two Thief instances with the same attributes.
   */
  test("Equals should return true if the units have the same attributes") {
    assertEquals(thief1, thief2)
  }

  /**
   * Test case to verify that a Thief instance is equal to itself.
   */
  test("Equals should return true when comparing the same units") {
    assertEquals(thief1, thief1)
  }

  /**
   * Test case to verify that the HP of the Thief cannot go below zero.
   */
  test("Thief HP should not be negative") {
    thief1.decreaseHp(100) // Exceeds current HP
    assertEquals(thief1.getHp, 0) // HP should be 0, not negative
  }

  /**
   * Test case to verify inequality of Thief instances with different attributes.
   */
  test("Equals should return false if the unit don't have the same attributes") {
    assertEquals(thief1 == thief3, false)
    assertEquals(thief1 == thief4, false)
    assertEquals(thief1 == thief5, false)
    assertEquals(thief1 == thief6, false)
  }

  /**
   * Test case to verify inequality when comparing a Thief instance with a non-unit.
   */
  test("Equals should return false when comparing with a non-unit") {
    val nonUnit: Heal = new Heal("D6", "Heal Potion")
    assertEquals(thief1 == nonUnit, false)
  }

  /**
   * Test case to verify the JSON representation of a Thief instance.
   */
  test("toJson should return correct JSON for the unit") {
    val expectedJson: JsObj = JsObj(
      "id" -> "p4",
      "attributes" -> JsArr(
        JsObj("name" -> "name", "value" -> "Setzer"),
        JsObj("name" -> "hp", "value" -> 0),
        JsObj("name" -> "def", "value" -> 65),
        JsObj("name" -> "weight", "value" -> 75),
      ),
      "img" -> "Setzer.gif"
    )
    assertEquals(thief1.toJson, expectedJson)
  }
}