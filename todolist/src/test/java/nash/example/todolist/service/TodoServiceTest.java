package nash.example.todolist.service;

import nash.example.todolist.model.dao.TodoDao;
import nash.example.todolist.model.entity.Todo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TodoServiceTest {
   @Autowired
   private  TodoDao todoDao;
   @Test
   public void testGetTodos () {

//        Todo todo1=new Todo();
//        todo1.setTask("123");
//        todoDao.save(todo1);
       Optional<Todo> todo =todoDao.findById(1);
       System.out.println(todo.get().getStatus());
       System.out.println(todo.get().getId());
       System.out.println(todo.get().getTask());
       System.out.println(todo.get().getCreateTime());
       System.out.println(todo.get().getUpdateTime());
        assertTrue(todo.isPresent());
    }



}