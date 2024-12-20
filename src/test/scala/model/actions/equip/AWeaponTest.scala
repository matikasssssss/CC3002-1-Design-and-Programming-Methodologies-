package model.actions.equip

import model.actions.Actions
import model.entity.Global
import model.entity.character.common.{Archer, Knight, Thief}
import model.entity.character.magic.{BlackMage, WhiteMage}
import model.exception.{CannotEquipWeapon, CannotUsePotion, InvalidTarget}
import model.utilizable.weapons.Weapons
import model.utilizable.weapons.common.{Bow, Sword}
import model.utilizable.weapons.magic.{Cane, Wand}
import munit.FunSuite
import util.Json.{*, given}

import scala.collection.mutable.ListBuffer

/**
 * Class `AWeaponTest` is a suite of unit tests for the `AWeapon` class,
 * verifying the functionality of its methods and properties related to weapon equipping.
 *
 * The tests include checking attributes, equality comparisons,
 * and the JSON representation of the equip weapon actions.
 */
class AWeaponTest extends FunSuite {

  /**
   *  Character instances
   */
  val whiteMage: WhiteMage = new WhiteMage("p1", "White Mage", 70, 60, 80, 55)
  val blackMage: BlackMage = new BlackMage("p2", "Black Mage", 60, 60, 85, 60)

  /**
   * Weapon instances
   */
  val cane: Cane = Cane("C4", "Cane I", 40, 10, whiteMage, 80)
  val wand: Wand = new Wand("C0", "Wand II", 20, 5, blackMage, 90)

  /**
   *  Magic weapons lists
   */
  val magicList1: ListBuffer[Weapons] = ListBuffer(cane, wand)
  val magicList2: ListBuffer[Weapons] = ListBuffer(cane)
  /**
   * Equip weapon instances
   */
  val equipWeapon1: AWeapon = new AWeapon("wand", "E1", magicList1)
  val equipWeapon2: AWeapon = new AWeapon("wand", "E1", magicList1)
  val equipWeapon3: AWeapon = new AWeapon("wand V", "E1", magicList2)

  /**
   * Test case to verify the attributes of the AWeapon class.
   */
  test("Equip weapon Attributes") {
    assertEquals(equipWeapon1.getName, "wand")
    assertEquals(equipWeapon1.getId, "E1")
    assertEquals(equipWeapon1.getTargets, magicList1)
  }

  /**
   * Test case to verify equality of two AWeapon instances with the same attributes.
   */
  test("Equals should return true if the action have the same name") {
    assertEquals(equipWeapon1, equipWeapon2)
  }

  /**
   * Test case to verify that an AWeapon instance is equal to itself.
   */
  test("Equals should return true when comparing the same action") {
    assertEquals(equipWeapon1, equipWeapon1)
  }

  /**
   * Test case to verify inequality of two AWeapon instances with different names.
   */
  test("Equals should return false if the action don't have the same name") {
    assertEquals(equipWeapon1 == equipWeapon3, false)
  }

  /**
   * Test case to verify inequality when comparing an AWeapon instance with a non-action.
   */
  test("Equals should return false when comparing with a non-action") {
    val nonHeal: Archer = new Archer("p1", "Didi", 70, 50, 65)
    assertEquals(equipWeapon1 == nonHeal, false)
  }

  test("An entity(Archer) can only equip weapons that it has designated"){
    val archer: Global = new Archer("archer1", "Legolas", 70, 50, 65)
    val bow: Weapons = new Bow("C3", "Bow V", 65, 30, archer)
    val equipBow: Actions = new AWeapon("Equip bow", "equipBow1", ListBuffer(bow))
    archer.doAction(equipBow, archer)
    println(s"Archer weapon is: ${archer.getWeapon}")
    val cane: Cane = Cane("C4", "Cane I", 40, 10, archer, 80)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    intercept[CannotEquipWeapon] {
      archer.doAction(equipCane, archer)
    }
  }
  test("An entity(Knight) can only equip weapons that it has designated") {
    val knight: Global = new Knight("p2", "Gogo", 60, 60, 85)
    val bow: Weapons = new Bow("C3", "Bow V", 65, 30, knight)
    val equipBow: Actions = new AWeapon("Equip bow", "equipBow1", ListBuffer(bow))
    knight.doAction(equipBow, knight)
    println(s"Knight weapon is: ${knight.getWeapon}")
    val cane: Cane = Cane("C4", "Cane I", 40, 10, knight, 80)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    intercept[CannotEquipWeapon] {
      knight.doAction(equipCane, knight)
    }
  }

  test("An entity(Thief) can only equip weapons that it has designated") {
    val thief: Global = new Thief("p4", "Setzer", 70, 65, 75)
    val sword: Weapons = new Sword("C1", "Sword V", 85, 40, thief)
    val equipSword: Actions = new AWeapon("Equip sword", "equipSword1", ListBuffer(sword))
    thief.doAction(equipSword, thief)
    println(s"Thief weapon is: ${thief.getWeapon}")
    val cane: Cane = Cane("C4", "Cane I", 40, 10, thief, 80)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    intercept[CannotEquipWeapon] {
      thief.doAction(equipCane, thief)
    }
  }

  test("An entity(Black Mage) can only equip weapons that it has designated") {
    val blackMage: Global = new BlackMage("p4", "Shadow", 60, 60, 85, 60)
    val cane: Cane = Cane("C4", "Cane I", 40, 10, blackMage, 80)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    blackMage.doAction(equipCane, blackMage)
    println(s"Black mage weapon is: ${blackMage.getWeapon}")
    val sword: Weapons = new Sword("C1", "Sword V", 85, 40, blackMage)
    val equipSword: Actions = new AWeapon("Equip sword", "equipSword1", ListBuffer(sword))
    intercept[CannotEquipWeapon] {
      blackMage.doAction(equipSword, blackMage)
    }
  }

  test("An entity that have negative/null hp can not equip/receive a weapon") {
    val blackMage: BlackMage = new BlackMage("p4", "Shadow", 60, 60, 85, 60)
    val cane: Cane = Cane("C4", "Cane I", 40, 10, blackMage, 40)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    blackMage.decreaseHp(60)
    val exception = intercept[InvalidTarget] {
      blackMage.doAction(equipCane, blackMage)
    }
    println(exception)
    assert(exception.getMessage == "Shadow has negative HP and cannot equip a weapon")
  }

  test("An entity(White Mage) can only equip weapons that it has designated") {
    val whiteMage: WhiteMage = new WhiteMage("p5", "Terra", 70, 60, 80, 55)
    val cane: Cane = Cane("C4", "Cane I", 40, 10, whiteMage, 80)
    val equipCane: Actions = new AWeapon("Equip cane", "equipCane1", ListBuffer(cane))
    whiteMage.doAction(equipCane, whiteMage)
    println(s"White mage weapon is: ${whiteMage.getWeapon}")
    val sword: Weapons = new Sword("C1", "Sword V", 85, 40, whiteMage)
    val equipSword: Actions = new AWeapon("Equip sword", "equipSword1", ListBuffer(sword))
    intercept[CannotEquipWeapon] {
      whiteMage.doAction(equipSword, whiteMage)
    }
  }
  /**
   * Test case to verify the JSON representation of an AWeapon instance.
   */
  test("toJson should return correct JSON for the action") {
    val expectedJson: JsObj = JsObj(
      "id" -> "E1",
      "action" -> s"Weapon→wand",
      "targets" -> JsArr(magicList1.map(_.toJson))
    )
    assertEquals(equipWeapon1.toJson, expectedJson)
  }
}
