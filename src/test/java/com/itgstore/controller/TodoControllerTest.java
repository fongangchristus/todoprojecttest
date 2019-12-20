package com.itgstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itgstore.model.Todo;
import com.itgstore.repository.TodoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@WebMvcTest(controllers = TodoController.class)
public class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TodoRepository todoRepository;

    private List<Todo> todoList;
    private Todo todo1 ;
    
    private static String CONTENTTYPE = "text/html;charset=UTF-8";

    @Before
    public void setUp(){
        //MockitoAnnotations.initMocks(this);
        todoList = new ArrayList<>();
        todo1 = new Todo();
        todo1.setName("Milky Bar");
        todo1.setDescription("Milky Bar with chocolate and hazelnut");
        todo1.setType("CANDIES");
        todo1.setCategory("BARS");
        Todo todo2 = new Todo();
        todo2.setName("Almond Bar");
        todo2.setDescription("Almond Bar with chocolate and honey");
        todo2.setType("CANDIES");
        todo2.setCategory("BARS");
        todoList.add(todo1);
        todoList.add(todo2);
        System.out.println(todoList);
    }

    @Test
    public void createTodo() throws Exception {
        Mockito.when(todoRepository.findAll()).thenReturn(todoList);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/todos/add")
                .contentType(CONTENTTYPE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todo"));
    }
    @Test
    public  void saveTodo() throws Exception{
        Mockito.when(todoRepository.save(todo1)).thenReturn(todo1);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                .contentType(CONTENTTYPE)
                .content(objectMapper.writeValueAsString(todo1)))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    @Test
    public void getAllTodos() throws Exception {
        Mockito.when(todoRepository.findAll()).thenReturn(todoList);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                //.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(CONTENTTYPE))
                .andExpect(MockMvcResultMatchers.model().attributeExists("todos"))
                .andExpect(MockMvcResultMatchers.model().attribute("todos", todoList))
                .andExpect(MockMvcResultMatchers.view().name("todos"));
    }

/*    @Test
    public void editTodo() throws Exception{
        Mockito.when(todoRepository.findOne("2")).thenReturn(todo1);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/todos/edit/{id}", "2"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("todos"))
                .andExpect(MockMvcResultMatchers.view().name("edit"));
    }*/
}
