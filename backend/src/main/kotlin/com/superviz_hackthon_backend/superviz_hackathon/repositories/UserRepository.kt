package com.superviz_hackthon_backend.superviz_hackathon.repositories

import com.superviz_hackthon_backend.superviz_hackathon.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, String>