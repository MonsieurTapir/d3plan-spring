
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
    val id: String? = null
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
}
    