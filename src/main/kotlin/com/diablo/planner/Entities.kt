package com.diablo.planner
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

enum class ItemSlot{
    HELM, SHOULDERS, GLOVES, BOOTS, PANTS, BELT, TORSO, BRACERS, NECK, RING, OHWEAPON, THWEAPON, OFFHAND, TOKEN
}

enum class SkillType{
    ACTIVE, PASSIVE
}

@Entity
@Table(name = "item")
class Item(
    var name: String,
    var url: String,
    var slot: ItemSlot,
    @Column(unique=true)
    var blizzID: String = "",
    var type: String = "",
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    var id: Long? = null
)


@Entity
@Table(name = "skill")
class Skill(
    val name: String,
    val className: String,
    @Column(unique=true)
    val url: String = "",
    val blizzID: String = "",
    val type: SkillType = SkillType.PASSIVE,
    val runes: Array<SkillRune>? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    var id: Long? = null
)

data class SkillRune(val name:String, val url:String) : Serializable

@Entity
@Table(name = "build")
class Build(
    val name: String,
    val className: String,
    val author: String,
    @ManyToOne var helm: Item? = null,
    @ManyToOne var shoulders: Item? = null,
    @ManyToOne var gloves: Item? = null,
    @ManyToOne var boots: Item? = null,
    @ManyToOne var pants: Item? = null,
    @ManyToOne var belt: Item? = null,
    @ManyToOne var torso: Item? = null,
    @ManyToOne var bracers: Item? = null,
    @ManyToOne var neck: Item? = null,
    @ManyToOne var ringA: Item? = null,
    @ManyToOne var ringB: Item? = null,
    @ManyToOne var mainHand: Item? = null,
    @ManyToOne var offHand: Item? = null,
    @ManyToOne var cubeA: Item? = null,
    @ManyToOne var cubeB: Item? = null,
    @ManyToOne var cubeC: Item? = null,
    @ManyToOne var skillA: Skill? = null,
    @ManyToOne var skillB: Skill? = null,
    @ManyToOne var skillC: Skill? = null,
    @ManyToOne var skillD: Skill? = null,
    @ManyToOne var skillE: Skill? = null,
    @ManyToOne var skillF: Skill? = null,
    var runeA: String? = null,
    var runeB: String? = null,
    var runeC: String? = null,
    var runeD: String? = null,
    var runeE: String? = null,
    var runeF: String? = null,
    @ManyToOne var passiveA: Skill? = null,
    @ManyToOne var passiveB: Skill? = null,
    @ManyToOne var passiveC: Skill? = null,
    @ManyToOne var passiveD: Skill? = null,
    var description: String = "",
    var addedAt: LocalDateTime = LocalDateTime.now(),
    @Column(unique=true)
    val slug: String = name.toSlug(),
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    val id: Long? = null
)
    

