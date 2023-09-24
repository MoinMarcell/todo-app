package com.github.moinmarcell.backend.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {
    private static final String BASE_URL = "/api/v1/todos";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DirtiesContext
    void getAllTodos_whenNoTodosInDb_thenExpectStatus200AndEmptyArray() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    void getTodoById_whenTodoExist_thenExpectStatus200AndReturnTodo() throws Exception {
        TodoRequest todoRequest = new TodoRequest("test", "test", "test");
        MvcResult result = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        Todo todo = objectMapper.readValue(result.getResponse().getContentAsString(), Todo.class);
        System.out.println(todo);

        mockMvc.perform(get(BASE_URL + "/" + todo.id()))
                .andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    void addNewTodo_whenCalled_expectStatus201AndReturnAddedTodo() throws Exception {
        TodoRequest todoRequest = new TodoRequest("test", "test", "test");
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    @DirtiesContext
    void updateTodo_whenTodoExist_thenExpectStatus200AndReturnUpdatedTodo() throws Exception {
        TodoRequest todoRequest = new TodoRequest("test", "test", "test");
        MvcResult result = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        Todo todo = objectMapper.readValue(result.getResponse().getContentAsString(), Todo.class);

        TodoRequest todoRequest2 = new TodoRequest("test2", "test2", "test2");
        mockMvc.perform(put(BASE_URL + "/" + todo.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoRequest2)))
                .andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    void deleteTodo_whenTodoExist_thenExpectStatus204() throws Exception {
        TodoRequest todoRequest = new TodoRequest("test", "test", "test");
        MvcResult result = mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        Todo todo = objectMapper.readValue(result.getResponse().getContentAsString(), Todo.class);

        mockMvc.perform(delete(BASE_URL + "/" + todo.id()))
                .andExpect(status().isNoContent());
    }
}