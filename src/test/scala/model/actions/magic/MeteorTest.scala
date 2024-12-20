package model.actions.magic

import model.actions.Actions
import model.actions.equip.AWeapon
import model.actions.movement.Move
import model.entity.Global
import model.entity.character.common.{Archer, Knight, Thief}
import model.entity.character.magic.BlackMage
import model.exception.{InsufficientMana, InvalidTarget}
import model.panels.{IPanel, Panel}
import model.utilizable.weapons.magic.Cane
import munit.FunSuite
import util.Json.{*, given}

import scala.collection.mutable.ListBuffer

/**
 * Class `MeteorTest` is a suite of unit tests for the `Meteor` action,
 * verifying the functionality of its methods and properties related to meteor magic.
 *
 * The tests include checking attributes, equality comparisons,
 * and the JSON representation of the meteor action.
 */
class MeteorTest extends FunSuite {

  /**
   *  Meteor action instances
   */
  val meteor1: Meteor = new Meteor(id = "F2")
  val meteor2: Meteor = new Meteor(id = "F2")
  val meteor3: Meteor = new Meteor(id = "F2")

  /**
   * Test case to verify the attributes of the Meteor action.
   */
  test("Meteor Attributes") {
    assertEquals(meteor1.getName, "Meteor")
    assertEquals(meteor1.getId, "F2")
  }

  /**
   * Test case to verify equality of two Meteor instances with the same id.
   */
  test("Equals should return true if the action have the same id") {
    assertEquals(meteor2, meteor3)
  }

  /**
   * Test case to verify that a Meteor instance is equal to itself.
   */
  test("Equals should return true when comparing the same action") {
    assertEquals(meteor1, meteor1)
  }

  /**
   * Test case to verify inequality when comparing a Meteor instance with a non-action.
   */
  test("Equals should return false when comparing with a non-action") {
    val nonHeal: Archer = new Archer("p1", "Didi", 70, 50, 65)
    assertEquals(meteor1 == nonHeal, false)
  }

  test("Meteor can only be perform by a black mage"){
    val blackMage: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 85, 200)
    println(s"Black mage mp: ${blackMage.getMp}")
    val cane: Cane = Cane("C4", "Cane I", 40, 10, blackMage, 40)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    blackMage.doAction(equipCane, blackMage)
    println(s"Black mage weapon: ${blackMage.getWeapon}")
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    val knight: Global = new Knight("knight1", "Gogo", 60, 60, 85)
    val thief: Global = new Thief("thief1", "Setzer", 70, 65, 75)
    val panel12: IPanel = new Panel("p1", 1, 2)
    panel12.addUnits(archer)
    panel12.addUnits(knight)
    panel12.addUnits(thief)
    println(s"Panel P1 units: ${panel12.getStorage}")
    val meteor: Actions = new Meteor()
    blackMage.doAction(meteor, panel12)
    println(s"Black mage mp: ${blackMage.getMp}")
    println(s"Archer hp: ${archer.getHp}")
    println(s"Knight hp: ${knight.getHp}")
    println(s"Thief hp: ${thief.getHp}")
    intercept[InvalidTarget]{
      archer.doAction(meteor, panel12)
    }
  }

  test("An entity that have negative/null hp can not perform meteor spell") {
    val blackMage: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 85, 200)
    val cane: Cane = Cane("C4", "Cane I", 40, 10, blackMage, 40)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    val knight: Global = new Knight("knight1", "Gogo", 60, 60, 85)
    val thief: Global = new Thief("thief1", "Setzer", 70, 65, 75)
    val panel12: IPanel = new Panel("p1", 1, 2)
    panel12.addUnits(archer)
    panel12.addUnits(knight)
    panel12.addUnits(thief)
    val meteor: Actions = new Meteor()
    blackMage.decreaseHp(60)
    val exception = intercept[InvalidTarget] {
      blackMage.doAction(meteor, panel12)
    }
    println(exception)
    assert(exception.getMessage == "Shadow has negative HP, and can not perform meteor spell")
  }

  test("An entity that have negative/null hp can not receive meteor") {
    val blackMage: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 85, 200)
    val cane: Cane = Cane("C4", "Cane I", 40, 10, blackMage, 40)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    val knight: Global = new Knight("knight1", "Gogo", 60, 60, 85)
    val thief: Global = new Thief("thief1", "Setzer", 70, 65, 75)
    val panel12: IPanel = new Panel("p1", 1, 2)
    panel12.addUnits(archer)
    panel12.addUnits(knight)
    panel12.addUnits(thief)
    val meteor: Actions = new Meteor()
    archer.decreaseHp(70)
    val exception = intercept[InvalidTarget] {
      blackMage.doAction(meteor, panel12)
    }
    println(exception)
    assert(exception.getMessage == "Legolas have zero or negative hp, and can not receive a meteor spell")
  }

  test("Black Mage should have sufficient manna points to perform Meteor spell") {
    val blackMage: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 85, 40)
    println(s"Black mage mp: ${blackMage.getMp}")
    val cane: Cane = Cane("C4", "Cane I", 40, 10, blackMage, 40)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    blackMage.doAction(equipCane, blackMage)
    println(s"Black mage weapon: ${blackMage.getWeapon}")
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    val knight: Global = new Knight("knight1", "Gogo", 60, 60, 85)
    val thief: Global = new Thief("thief1", "Setzer", 70, 65, 75)
    val panel12: IPanel = new Panel("p1", 1, 2)
    panel12.addUnits(archer)
    panel12.addUnits(knight)
    panel12.addUnits(thief)
    println(s"Panel P1 units: ${panel12.getStorage}")
    val meteor: Actions = new Meteor()
    intercept[InsufficientMana]{
      blackMage.doAction(meteor, panel12)
    }
  }

  test("Target should be a panel") {
    val blackMage: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 85, 40)
    println(s"Black mage mp: ${blackMage.getMp}")
    val cane: Cane = Cane("C4", "Cane I", 40, 10, blackMage, 40)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    blackMage.doAction(equipCane, blackMage)
    println(s"Black mage weapon: ${blackMage.getWeapon}")
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    val knight: Global = new Knight("knight1", "Gogo", 60, 60, 85)
    val thief: Global = new Thief("thief1", "Setzer", 70, 65, 75)
    val panel12: IPanel = new Panel("p1", 1, 2)
    panel12.addUnits(archer)
    panel12.addUnits(knight)
    panel12.addUnits(thief)
    println(s"Panel P1 units: ${panel12.getStorage}")
    val meteor: Actions = new Meteor()
    intercept[InvalidTarget] {
      blackMage.doAction(meteor, archer)
    }
  }

  /**
   * Test case to verify the JSON representation of a Meteor action instance.
   */
  test("toJson should return correct JSON for the action") {
    val expectedJson: JsObj = JsObj(
      "id" -> "F2",
      "action" -> s"Magic→Meteor"
    )
    assertEquals(meteor1.toJson, expectedJson)
  }
}
