package com.diabloplanner
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

enum class ItemSlot{
    HELM, SHOULDERS, GLOVES, BOOTS, PANTS, BELT, TORSO, BRACERS, NECK, RING, OHWEAPON, THWEAPON, OFFHAND, GEM, TOKEN
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



