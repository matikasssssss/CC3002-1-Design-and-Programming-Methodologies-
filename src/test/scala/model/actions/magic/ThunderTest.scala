package model.actions.magic

import model.actions.Actions
import model.actions.equip.AWeapon
import model.entity.Global
import model.entity.character.common.Archer
import model.entity.character.magic.BlackMage
import model.exception.{CannotUseMagicSpells, InsufficientMana, InvalidTarget}
import model.utilizable.weapons.magic.Cane
import munit.FunSuite
import util.Json.{*, given}

import scala.collection.mutable.ListBuffer

/**
 * Class `ThunderTest` is a suite of unit tests for the `Thunder` action,
 * verifying the functionality of its methods and properties related to thunder magic.
 *
 * The tests include checking attributes, equality comparisons,
 * and the JSON representation of the thunder action.
 */
class ThunderTest extends FunSuite {

  /**
   *  Thunder action instances
   */
  val thunder1: Thunder = new Thunder(id = "F4")
  val thunder2: Thunder = new Thunder(id = "F4")
  val thunder3: Thunder = new Thunder(id = "F4")

  /**
   * Test case to verify the attributes of the Thunder action.
   */
  test("Thunder Attributes") {
    assertEquals(thunder1.getName, "Thunder")
    assertEquals(thunder1.getId, "F4")
  }

  /**
   * Test case to verify equality of two Thunder instances with the same id.
   */
  test("Equals should return true if the action have the same id") {
    assertEquals(thunder2, thunder3)
  }

  /**
   * Test case to verify that a Thunder instance is equal to itself.
   */
  test("Equals should return true when comparing the same action") {
    assertEquals(thunder1, thunder1)
  }

  /**
   * Test case to verify inequality when comparing a Thunder instance with a non-action.
   */
  test("Equals should return false when comparing with a non-action") {
    val nonHeal: Archer = new Archer("p1", "Didi", 70, 50, 65)
    assertEquals(thunder1 == nonHeal, false)
  }

  test("Thunder be perform by a black mage") {
    val blackMage: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 85, 60)
    val cane: Cane = Cane("C4", "Cane I", 40, 10, blackMage, 40)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    blackMage.doAction(equipCane, blackMage)
    println(s"Black Mage weapon: ${blackMage.getWeapon}")
    val thunder: Actions = new Thunder("thunder1")
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    println(s"Archer hp: ${archer.getHp}")
    println(s"Black mage mp: ${blackMage.getMp}")
    blackMage.doAction(thunder, archer)
    println(s"Archer hp: ${archer.getHp}")
    println(s"Black mage mp: ${blackMage.getMp}")

    intercept[InvalidTarget] {
      archer.doAction(thunder, blackMage)
    }

    blackMage.decreaseMp(45)
    intercept[InsufficientMana] {
      blackMage.doAction(thunder, archer)
    }
  }

  test("An entity that have negative/null hp can not perform thunder spell") {
    val blackMage: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 85, 60)
    val cane: Cane = Cane("C4", "Cane I", 40, 10, blackMage, 40)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    blackMage.doAction(equipCane, blackMage)
    val thunder: Actions = new Thunder("thunder1")
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    blackMage.decreaseHp(70)
    val exception = intercept[InvalidTarget] {
      blackMage.doAction(thunder, archer)
    }
    println(exception)
    assert(exception.getMessage == "Shadow has negative HP, and can not perform thunder spell")
  }

  test("An entity that have negative/null hp can not receive thunder") {
    val blackMage: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 85, 60)
    val cane: Cane = Cane("C4", "Cane I", 40, 10, blackMage, 40)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    blackMage.doAction(equipCane, blackMage)
    val thunder: Actions = new Thunder("thunder1")
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    archer.decreaseHp(1000)
    val exception = intercept[InvalidTarget] {
      blackMage.doAction(thunder, archer)
    }
    println(exception)
    assert(exception.getMessage == "Legolas already defeated and can not receive thunder")
  }

  /**
   * Test case to verify the JSON representation of a Thunder action instance.
   */
  test("toJson should return correct JSON for the action") {
    val expectedJson: JsObj = JsObj(
      "id" -> "F4",
      "action" -> s"Magic→Thunder"
    )
    assertEquals(thunder1.toJson, expectedJson)
  }
}
