package model.utilizable.weapons.common

import api.Source
import model.entity.Global
import model.entity.character.Entity

/**
 * Class `Dagger` represents a dagger weapon in the game, extending the `AbstractCommon` class.
 * It provides specific characteristics for a dagger weapon, including its attack power, weight,
 * and the character that owns it.
 *
 * @param id     The unique identifier for the dagger.
 * @param name   The name of the dagger.
 * @param atk    The attack power of the dagger.
 * @param weight The weight of the dagger.
 * @param owner  The character that owns the dagger, represented by an instance of `AbstractCharacters`.
 */
class Dagger(id: String, name: String, atk: Int, weight: Int, owner: Global)
  extends AbstractCommon(id, name, atk, weight, owner) {
}
