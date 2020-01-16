package com.diablo.planner

import org.springframework.data.repository.CrudRepository

interface BuildRepository : CrudRepository<Build, Long> {
	fun findBySlug(slug: String): Build?
	fun findAllByOrderByAddedAtDesc(): Iterable<Build>
}

interface ItemRepository : CrudRepository<Item, Long> {
	fun findByName(name: String): Item?
}

interface SkillRepository : CrudRepository<Skill, Long> {
	fun findByName(name: String): Skill?
}