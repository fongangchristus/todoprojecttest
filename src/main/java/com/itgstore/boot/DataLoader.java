package com.itgstore.boot;

import com.itgstore.model.Todo;
import com.itgstore.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private TodoRepository todoRepository;

    @Autowired
    public void setTodoRepository(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public void run(String... strings) throws Exception {

        Todo todo1 = new Todo();
        todo1.setName("Milky Bar");
        todo1.setDescription("Milky Bar with chocolate and hazelnut");
        todo1.setType("CANDIES");
        todo1.setCategory("BARS");

        todoRepository.save(todo1);

        Todo todo2 = new Todo();
        todo2.setName("Almond Bar");
        todo2.setDescription("Almond Bar with chocolate and honey");
        todo2.setType("CANDIES");
        todo2.setCategory("BARS");

        todoRepository.save(todo2);
    }
}
