package nash.example.todolist.service;


import nash.example.todolist.model.dao.TodoDao;
import nash.example.todolist.model.entity.Todo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoServiceTest {
    @Autowired
    private TodoDao todoDao;
    @Autowired
    private TodoService todoService;

    private static final Logger logger = LoggerFactory.getLogger(TodoServiceTest.class);

    @Test
    public void testGetTodos() {
        log("GET起");
//        Todo todo1=new Todo();
//        todo1.setTask("123");
//        todoDao.save(todo1);
        Optional<Todo> todo = todoDao.findById(1);
        System.out.println(todo.get().getStatus());
        System.out.println(todo.get().getId());
        System.out.println(todo.get().getTask());
        System.out.println(todo.get().getCreateTime());
        System.out.println(todo.get().getUpdateTime());
        assertTrue(todo.isPresent());
        log("GET尾");
    }

    private void log(String s) {
        Iterator<Todo> tt = todoService.getTodos().iterator();
        while (true) {
            if (tt.hasNext()) {
                Todo tr = tt.next();
                logger.warn("server" + s + "   " + tr.getTask());
                logger.warn("server" + s + "   " + tr.getId().toString());
                logger.warn("server" + s + "   " + tr.getUpdateTime().toString());
                logger.warn("server" + s + "   " + tr.getCreateTime().toString());
                logger.warn("server" + s + "   " + tr.getStatus().toString());
                logger.warn("---------------------------------");
                logger.warn("                                              ");
            } else {
                logger.warn("server" + s + "   " + "空的@@@@@@@@@@@@@@");
                break;
            }
        }
    }

}