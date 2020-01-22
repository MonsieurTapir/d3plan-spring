
package com.diabloplanner
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*
import org.hibernate.annotations.GenericGenerator

enum class ClassName{
   BARBARIAN,CRUSADER,DH,MONK,NECROMANCER,WD,WIZARD
}
enum class CubeSlot{
    JEWELRY,ARMOR,WEAPON
}
enum class SkillSlot{
    Q,W,E,R,LCLICK,RCLICK,PASSIVE1,PASSIVE2,PASSIVE3,PASSIVE4
}
enum class RuneSlot{
    Q,W,E,R,LCLICK,RCLICK
}

enum class BuildSlot{
    HELM, SHOULDERS, GLOVES, BOOTS, PANTS, BELT, TORSO, BRACERS, NECK, RING1, RING2, MAINHAND, OFFHAND
}

enum class GemSlot{
    GEM1, GEM2, GEM3
}

@Entity
@Table(name = "build")
class Build(
    val name: String = "",
    val className: String = "",
    val author: String = "",

    @ManyToMany(targetEntity=com.diabloplanner.Item)
    var legendaryGems: MutableMap<GemSlot,Item?> = HashMap(),

    @ManyToMany(targetEntity=com.diabloplanner.Item)
    var itemsEquipped: MutableMap<BuildSlot, Item?> = HashMap(),

    @ManyToMany(targetEntity=com.diabloplanner.Item)
    var itemsCubed: MutableMap<CubeSlot, Item?> = HashMap(),

    @ManyToMany(targetEntity=com.diabloplanner.Skill)
    var skillsEquipped: MutableMap<SkillSlot, Skill?> = HashMap(),

    @ElementCollection
    var runes: MutableMap<RuneSlot, String?> = HashMap(),

    var description: String = "",
    var addedAt: LocalDateTime = LocalDateTime.now(),
    val slug: String = name.toSlug(),
    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid",
      strategy = "uuid")
    var id: String? = null
){
    fun init_empty(){
        for(s in BuildSlot.values()){
            itemsEquipped[s] = null
        }
        for(s in GemSlot.values()){
            legendaryGems[s] = null
        }
        for(s in SkillSlot.values()){
            skillsEquipped[s] = null
        }
        for(s in RuneSlot.values()){
            runes[s] = null
        }
        for(s in CubeSlot.values()){
            itemsCubed[s] = null
        }
    }
    fun equipItem(slot : BuildSlot, item :Item){
        if(!fits(slot,item))
            throw InvalidItemEquip("Invalid item equip", slot.name, item)
        else{
            itemsEquipped[slot] = item
        }
    }
    fun equipGem(slot : GemSlot, item :Item){
        if(!fits(slot,item))
            throw InvalidItemEquip("Invalid item equip", slot.name, item)
        else{
            legendaryGems[slot] = item
        }
    } 
    fun cubeItem(slot : CubeSlot, item :Item){
        if(!fits(slot,item))
            throw InvalidItemEquip("Invalid item equip", slot.name, item)
        else{
            itemsCubed[slot] = item
        }
    }
    fun equipSkill(slot: SkillSlot, skill : Skill){
        if(!fits(slot,skill))
            throw InvalidSpellEquip("Invalid skill equip", slot.name, skill)
        else if(skillsEquipped.containsValue(skill)) {
            throw InvalidSpellEquip("Duplicate skill", slot.name, skill, skillsEquipped.filterValues{it == skill}.keys.toList()[0].name)
        }else{
            skillsEquipped[slot] = skill
        }
    }
    fun equipRune(slot: RuneSlot, rune : String){
        val skslot : SkillSlot = SkillSlot.valueOf(slot.name)
        val skillLink : Skill = skillsEquipped[skslot]?:throw InvalidRuneEquip("No skill equipped", slot.name, rune)
        if(!skillLink.runes!!.map{it.name}.contains(rune))
            throw InvalidRuneEquip("Unknown rune", slot.name, rune)
        else{
            runes[slot] = rune
        }
    }
    private fun fits(slot : SkillSlot, skill: Skill): Boolean{
        return when(skill.type){
            SkillType.ACTIVE -> slot in arrayOf(SkillSlot.Q, SkillSlot.W, SkillSlot.E, SkillSlot.R, SkillSlot.LCLICK, SkillSlot.RCLICK)
            SkillType.PASSIVE -> slot in arrayOf(SkillSlot.PASSIVE1,SkillSlot.PASSIVE2,SkillSlot.PASSIVE3,SkillSlot.PASSIVE4)
        }
    }
    private fun fits(slot : CubeSlot, item :Item) : Boolean{
        return when(slot) {
            CubeSlot.ARMOR -> item.slot in arrayOf(ItemSlot.HELM,ItemSlot.SHOULDERS,ItemSlot.BRACERS,ItemSlot.PANTS,ItemSlot.BELT,ItemSlot.TORSO,ItemSlot.GLOVES, ItemSlot.BOOTS)
            CubeSlot.JEWELRY -> item.slot in arrayOf(ItemSlot.RING, ItemSlot.NECK)
            CubeSlot.WEAPON -> item.slot in arrayOf(ItemSlot.OHWEAPON, ItemSlot.OFFHAND, ItemSlot.THWEAPON)
        }
    }
    private fun fits(slot : GemSlot, item :Item) : Boolean{
        return item.slot == ItemSlot.GEM
    }
    private fun fits(slot : BuildSlot, item :Item) : Boolean{
        return when(item.slot) {
            ItemSlot.RING -> slot == BuildSlot.RING1 || slot == BuildSlot.RING2 
            ItemSlot.THWEAPON -> slot == BuildSlot.MAINHAND && itemsEquipped[BuildSlot.OFFHAND] == null
            ItemSlot.OFFHAND -> slot == BuildSlot.OFFHAND && (itemsEquipped[BuildSlot.MAINHAND] == null || itemsEquipped[BuildSlot.MAINHAND]!!.slot ==ItemSlot.OHWEAPON)
            ItemSlot.OHWEAPON ->  slot == BuildSlot.MAINHAND || (slot == BuildSlot.OFFHAND && (itemsEquipped[BuildSlot.MAINHAND] == null || itemsEquipped[BuildSlot.MAINHAND]!!.slot ==ItemSlot.OHWEAPON))
            else -> slot.name == item.slot.name
        }
    }
}
    