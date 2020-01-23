package com.diabloplanner

import org.springframework.data.repository.CrudRepository

interface BuildRepository : CrudRepository<Build, String> {
	fun findBySlug(slug: String): Build?
	fun findAllByOrderByAddedAtDesc(): Iterable<Build>
}

interface ItemRepository : CrudRepository<Item, Long> {
	fun findByName(name: String): Item?
	fun findByBlizzID(blizzID: String): Item?
}

interface SkillRepository : CrudRepository<Skill, Long> {
	fun findByName(name: String): Skill?
	fun findByIcon(icon: String): Skill?
	fun findByUrl(url: String): Skill?
}