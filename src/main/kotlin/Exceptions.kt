
package com.diabloplanner

class InvalidItemEquip(message: String, slot:String, item:Item) : Exception(message)
class InvalidSpellEquip(message: String, slot:String, skill:Skill, otherslot:String = "") : Exception(message)
class InvalidRuneEquip(message: String, slot:String, rune:String) : Exception(message)
class InvalidSkillId(message: String, id: Long) : Exception(message)
class InvalidItemId(message: String, id: Long) : Exception(message)