package nash.example.todolist.controller;

import nash.example.todolist.model.entity.Todo;
import nash.example.todolist.service.TodoService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;

@SpringBootTest
public class jj {
    @Autowired
    TodoService todoService;

    private static final Logger logger = LoggerFactory.getLogger(jj.class);
    @Test
    public void findAll(){
        log("findAll()");

}
@Test
@Transactional
public void createTodo(){

        Todo todo=new Todo();
        todo.setTask("拖地");
        Integer num=todoService.createTodo(todo);
        log("createTodo");

    }
    @Test
    public void findAllAgain(){
       log("findAllAgain");


    }
    private void log(String s){
        Iterator<Todo> tt=todoService.getTodos().iterator();
        while (true) {
            if (tt.hasNext()) {
                Todo tr = tt.next();
                logger.warn(s+"   "+tr.getTask());
                logger.warn(s+"   "+tr.getId().toString());
                logger.warn(s+"   "+tr.getUpdateTime().toString());
                logger.warn(s+"   "+tr.getCreateTime().toString());
                logger.warn(s+"   "+tr.getStatus().toString());
                logger.warn("---------------------------------");
                logger.warn("                                              ");
            } else {
                logger.warn (s+"   "+"空的@@@@@@@@@@@@@@");
                break;
            }
        }

    }



}
