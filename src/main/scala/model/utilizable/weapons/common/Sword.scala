package model.utilizable.weapons.common

import api.Source
import model.entity.Global
import model.entity.character.Entity

/**
 * Class `Sword` represents a sword weapon in the game, extending the `AbstractCommon` class.
 * It provides specific characteristics for a sword weapon, including its attack power, weight,
 * and the character that owns it.
 *
 * @param id     The unique identifier for the sword.
 * @param name   The name of the sword.
 * @param atk    The attack power of the sword.
 * @param weight The weight of the sword.
 * @param owner  The character that owns the sword, represented by an instance of `AbstractCharacters`.
 */
class Sword(id: String, name: String, atk: Int, weight: Int, owner: Global)
  extends AbstractCommon(id, name, atk, weight, owner) {
}
