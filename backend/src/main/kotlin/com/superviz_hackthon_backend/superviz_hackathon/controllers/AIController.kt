package com.superviz_hackthon_backend.superviz_hackathon.controllers

import com.superviz_hackthon_backend.superviz_hackathon.dto.AITaskDTO
import com.superviz_hackthon_backend.superviz_hackathon.dto.AITasksDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.web.client.RestTemplate
import org.json.JSONObject
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/ai")
class AIController {

    @Value("\${groq}")
    private val groqApiKey: String? = null

    @PostMapping("/task")
    fun resumeTask(@RequestBody aiTaskDTO: AITaskDTO): ResponseEntity<String> {
        val url = "https://api.groq.com/openai/v1/chat/completions"

        // Criação do RestTemplate
        val restTemplate = RestTemplate()

        // Configurando o cabeçalho da requisição
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.setBearerAuth(groqApiKey!!) // Adiciona o token de autorização

        // Corpo da requisição
        val requestBody: MutableMap<String, Any> = HashMap()
        requestBody["model"] = "llama3-8b-8192" // Valor fixo do modelo

        // Estrutura da mensagem
        val message: MutableMap<String, String> = HashMap()
        message["role"] = "user" // Valor fixo do role
        message["content"] = "I need to resume a task of a Kanban Project. I need a concise and complete resume of the task ${aiTaskDTO.task}"

        // Adiciona a mensagem ao corpo da requisição
        requestBody["messages"] = listOf<Map<String, String>>(message)

        // Criação do HttpEntity com o corpo e cabeçalhos
        val entity = HttpEntity<Map<String, Any>>(requestBody, headers)

        // Fazendo a requisição POST
        val response = restTemplate.exchange(
            url, HttpMethod.POST, entity, String::class.java
        )

        // Parse da resposta JSON para extrair o "content"
        val jsonResponse = JSONObject(response.body)
        val content = jsonResponse
            .getJSONArray("choices")
            .getJSONObject(0)
            .getJSONObject("message")
            .getString("content")

        // Retorna apenas o campo "content"
        return ResponseEntity(content, response.statusCode)
    }

    @PostMapping("/multitask")
    fun resumeTasks(@RequestBody aiTasksDTO: AITasksDTO): ResponseEntity<String> {
        val url = "https://api.groq.com/openai/v1/chat/completions"

        // Criação do RestTemplate
        val restTemplate = RestTemplate()

        // Configurando o cabeçalho da requisição
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.setBearerAuth(groqApiKey!!) // Adiciona o token de autorização

        // Formatando as tasks para o conteúdo da mensagem
        val formattedTasks = aiTasksDTO.tasks.mapIndexed { index, task ->
            "Task ${index + 1}:\n\n$task"
        }.joinToString("\n\n") // Junta todas as tasks com duas quebras de linha entre elas

        // Corpo da requisição
        val requestBody: MutableMap<String, Any> = HashMap()
        requestBody["model"] = "llama3-8b-8192" // Valor fixo do modelo

        // Estrutura da mensagem
        val message: MutableMap<String, String> = HashMap()
        message["role"] = "user" // Valor fixo do role
        message["content"] = "I need to resume a list of tasks of a Kanban Project. I need a concise and complete resume of the tasks:\n\n$formattedTasks"

        // Adiciona a mensagem ao corpo da requisição
        requestBody["messages"] = listOf<Map<String, String>>(message)

        // Criação do HttpEntity com o corpo e cabeçalhos
        val entity = HttpEntity<Map<String, Any>>(requestBody, headers)

        // Fazendo a requisição POST
        val response = restTemplate.exchange(
            url, HttpMethod.POST, entity, String::class.java
        )

        // Parse da resposta JSON para extrair o "content"
        val jsonResponse = JSONObject(response.body)
        val content = jsonResponse
            .getJSONArray("choices")
            .getJSONObject(0)
            .getJSONObject("message")
            .getString("content")

        // Retorna apenas o campo "content"
        return ResponseEntity(content, response.statusCode)
    }

}