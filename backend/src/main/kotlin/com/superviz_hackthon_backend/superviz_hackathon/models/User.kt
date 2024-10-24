package com.superviz_hackthon_backend.superviz_hackathon.models

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("USERS_COLLABNOTES")
data class User(@Id @GeneratedValue(strategy= GenerationType.AUTO) val id: Long = 0, val name: String)
