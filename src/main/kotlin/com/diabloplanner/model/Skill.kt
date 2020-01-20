package com.diabloplanner
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

enum class SkillType{
    ACTIVE, PASSIVE
}

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
