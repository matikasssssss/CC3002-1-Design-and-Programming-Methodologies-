package model.panels

import model.actions.magic.{Healing, Meteor, Purification, Thunder}
import model.entity.Global
import model.entity.character.common.Archer
import model.entity.enemy.{Enemy, IEnemy}
import model.exception.{CannotEquipWeapon, CannotHaveDef, CannotHaveMp, CannotHaveName, CannotHeal, CannotUseMagicSpells, InvalidTarget, UnitNotInPanel}
import model.utilizable.potions.common.{Heal, Strength}
import model.utilizable.potions.magic.{MagicalForce, Manna}
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.common.Bow
import munit.FunSuite
import util.Json.{*, given}

import scala.collection.mutable.ListBuffer

/**
 * Class `PanelTest` is a suite of unit tests for the `Panel` class,
 * which represents a section of the game map that can contain entities.
 *
 * The tests cover various attributes of the panels, their equality,
 * and their JSON representation.
 */
class PanelTest extends FunSuite {

  // Declare potions available for the enemy.
  val heal: Heal = new Heal("Heal1", "Basic heal potion")
  val strength: Strength = new Strength("Strength1", "Basic strength potion")
  val magicalForce: MagicalForce = new MagicalForce("MagicalForce1", "Basic magical force potion")
  val manna: Manna = new Manna("Manna1", "Basic manna potion")

  /**
   * Panel instances for testing.
   */
  val panel1: IPanel = new Panel("p1", 1, 2)
  val panel2: IPanel = new Panel("p2", 1, 1)
  val panel3: IPanel = new Panel("p3", 2, 2)
  val panel4: IPanel = new Panel("p4", 2, 3)
  val panel5: IPanel = new Panel("p1", 1, 2)

  val archer: Global = new Archer("Archer1", "Loop", 100, 60, 70)

  /**
   * Test case to verify the attributes of the Panel.
   */
  test("Panel attributes and neighbor") {
    assertEquals(panel1.getX, 1)
    assertEquals(panel2.getY, 1)
    assertEquals(panel2.getId, "p2")
    assert(panel1.isNeighbor(panel2))
    assert(panel2.isNeighbor(panel1))
  }

  /**
   * Test case to verify adding and removing units from a panel.
   */
  test("Add units and remove units from panel") {
    panel1.addUnits(archer)
    assert(panel1.getStorage == ListBuffer(archer))

    panel1.removeUnits(archer)
    assert(panel1.getStorage == ListBuffer())
  }

  /**
   * Test case to verify that the Panel cannot have a name and throws CannotHaveName exception.
   */
  test("Panel cannot have name and throws CannotHaveName exception") {
    intercept[CannotHaveName] {
      panel1.getName
    }
  }

  /**
   * Test case to verify that the Panel cannot increase HP and throws CannotHeal exception.
   */
  test("Panel cannot increase hp and throws CannotHeal exception") {
    intercept[CannotHeal] {
      panel1.increaseHp(10)
    }
  }

  /**
   * Test case to verify that the Panel cannot decrease HP and throws CannotHeal exception.
   */
  test("Panel cannot decrease hp and throws CannotHeal exception") {
    intercept[CannotHeal] {
      panel1.decreaseHp(10)
    }
  }

  /**
   * Test case to verify that the Panel cannot get HP and throws CannotHeal exception.
   */
  test("Panel cannot get hp and throws CannotHeal exception") {
    intercept[CannotHeal] {
      panel1.getHp
    }
  }

  /**
   * Test case to verify that the Panel cannot get max HP and throws CannotHeal exception.
   */
  test("Panel cannot get max hp and throws CannotHeal exception") {
    intercept[CannotHeal] {
      panel1.getMaxHp
    }
  }

  /**
   * Test case to verify that the Panel cannot use potions and throws CannotUsePotion exception.
   */
  test("Panel cannot equip weapon and throws CannotEquipWeapon exception") {
    val archer1: Archer = new Archer("p1", "Didi", 70, 50, 65)
    val bow1: Weapons = new Bow("C3", "Bow V", 65, 30, archer1)
    intercept[CannotEquipWeapon]{
      panel1.canEquipWeapon(bow1)
    }
    intercept[CannotEquipWeapon] {
      panel1.equipWeapon(bow1)
    }
  }

  test("Panel cannot decrease/increase or get max mp and throws CannotHaveNp exception"){
    intercept[CannotHaveMp]{
      panel1.decreaseMp(500054)
    }
    intercept[CannotHaveMp] {
      panel1.increaseMp(500054)
    }
    intercept[CannotHaveMp] {
      panel1.getMaxMp
    }
  }

  test("Panel cannot increase or get def and throws CannotHaveDef exception"){
    intercept[CannotHaveDef]{
      panel1.increaseDef(43534534)
    }
    intercept[CannotHaveDef] {
      panel1.getDef
    }
  }

  test("Panel cannot have magic spells and throws CannotUseMagicSpells exception"){
    intercept[CannotUseMagicSpells]{
      panel1.isMagicBoostActivate
    }
    intercept[CannotUseMagicSpells] {
      panel1.activateMagicBoost()
    }
    intercept[CannotUseMagicSpells] {
      panel1.deactivateMagicBoost()
    }

  }
  /**
   * Test case to verify that a unit cannot be in a panel and throws UnitNotInPanel exception.
   */
  test("Unit cannot be in a panel and throws UnitNotInPanel exception") {
    val archer11: Archer = new Archer("p1", "Dude", 70, 50, 65)
    panel1.addUnits(archer11)
    panel1.removeUnits(archer11)
    intercept[UnitNotInPanel] {
      panel1.removeUnits(archer11)
    }
  }

  /**
   * Test case to verify that same unit cannot be in the same panel and throws InvalidTarget exception.
   */
  test("The same unit can not be in the same panel twice"){
    val archer1: Archer = new Archer("p1", "Didi", 70, 50, 65)
    val panel122: IPanel = new Panel("p122", 1, 2)
    panel122.addUnits(archer1)
    intercept[InvalidTarget]{
      panel122.addUnits(archer1)
    }
  }
  
  test("Panel can clear his storage"){
    val archer1: Archer = new Archer("p1", "Didi", 70, 50, 65)
    val archer2: Archer = new Archer("p2", "luck", 70, 50, 65)
    val archer3: Archer = new Archer("p3", "pedri", 70, 50, 65)
    panel1.addUnits(archer1)
    panel1.addUnits(archer2)
    panel1.addUnits(archer3)
    println(panel1.getStorage)
    assertEquals(panel1.getStorage, ListBuffer(archer1, archer2, archer3))
    panel1.clearUnits()
    println(panel1.clearUnits())
    assertEquals(panel1.getStorage, ListBuffer())
  }
  
  /**
   * Test case to verify equality of panels with the same ID.
   */
  test("Equals should return true if the panels have the same Id") {
    val panel12: IPanel = new Panel("p1", 1, 2)
    val panel34: IPanel = new Panel("p1", 1, 2)
    assertEquals(panel34, panel12)
  }

  /**
   * Test case to verify that a Panel instance is equal to itself.
   */
  test("Equals should return true when comparing the same panel") {
    assertEquals(panel1, panel1)
  }

  test("Entity should be in a panel, can no be null"){
    val archer1: Archer = new Archer("p1", "Relm", 70, 50, 65)
    intercept[UnitNotInPanel]{
      archer1.setPanel(null)
    }
  }

  /**
   * Test case to verify that two Panel instances are not equal
   * when they have different attributes.
   */
  test("Equals should return false if the panels don't have the same attributes") {
    assertEquals(panel1 == panel2, false)
  }

  /**
   * Test case to verify inequality when comparing a Panel instance with a non-panel.
   */
  test("Equals should return false when comparing with a non-panel") {
    assertEquals(panel1 == archer, false)
  }

  /**
   * Test case to verify the JSON representation of a Panel instance.
   */
  test("toJson should return correct JSON for the panel") {
    val panel12: IPanel = new Panel("p1", 1, 2)
    panel12.addUnits(archer)
    val expectedJson: JsObj = JsObj(
      "id" -> panel12.getId,
      "x" -> panel12.getX,
      "y" -> panel12.getY,
      "storage" -> JsArr(archer.toJson)
    )
    assertEquals(panel12.toJson, expectedJson)
  }
}
