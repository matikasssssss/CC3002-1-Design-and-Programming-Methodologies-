package model.utilizable.weapons.magic

import model.entity.Global
import model.entity.character.Entity
import model.utilizable.weapons.AbstractWeapons
import util.Json.{*, given}

/**
 * Class `AbstractMagic` represents an abstract base class for magic weapons in the game.
 * It extends the `AbstractWeapons` class and adds additional functionality specific to magic weapons,
 * including magical attack power.
 *
 * @param id     The unique identifier for the magic weapon.
 * @param name   The name of the magic weapon.
 * @param atk    The physical attack power of the weapon.
 * @param weight The weight of the weapon.
 * @param owner  The character that owns the weapon, represented by an instance of `Entity`.
 * @param atkm   The magical attack power of the weapon.
 */
abstract class AbstractMagic(id: String, name: String, atk: Int, weight: Int, owner: Global, override val atkm: Int)
  extends AbstractWeapons(id, name, atk, weight, owner){

  /**
   * Retrieves the magical attack power of the weapon.
   *
   * @return The magical attack power.
   */
  override def getAtkm: Int = atkm

  /**
   * Converts the magic weapon to a JSON object representation.
   *
   * @return A JSON object containing the weapon's details.
   */
  override def toJson: JsObj = JsObj(
    "id" -> id,
    "name" -> name,
    "atk" -> atk,
    "weight" -> weight,
    "owner" -> owner.toJson,
    "atkm" -> atkm
  )
}
