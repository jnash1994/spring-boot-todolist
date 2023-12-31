package nash.example.todolist.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nash.example.todolist.model.entity.Todo;
import nash.example.todolist.service.TodoService;
import org.hibernate.annotations.SQLDelete;
import org.json.JSONObject;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
//整合測試 H2資料庫
public class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    TodoService todoService;

    @Autowired
    ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(TodoControllerTest.class);
    @Transactional
    @Test
    public void testGetTodos() throws Exception {
        log("Get起");
        String strDate = "2020-09-20 19:00:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(strDate);

        // [Arrange] 預期回傳的值
        List<Todo> expectedList = new ArrayList();
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("洗衣服");
        todo.setCreateTime(date);
        todo.setUpdateTime(date);
        expectedList.add(todo);

        // [Act] 模擬網路呼叫[GET] /api/todos
        String returnString = mockMvc.perform(MockMvcRequestBuilders.get("/api/todos")
                        .accept(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        Iterable<Todo> actualList = objectMapper.readValue(returnString, new TypeReference<Iterable<Todo>>() {
        });
        // [Assert] 判定回傳的body是否跟預期的一樣
        assertEquals(expectedList,  actualList);
        log("Get尾");
    }
    @Transactional
    @Test
    public void testCreateTodos() throws Exception {
        // [Arrange] 預期回傳的值
        JSONObject todoObject = new JSONObject();
        todoObject.put("task", "寫文章");

        // [Act] 模擬網路呼叫[POST] /api/todos
        String actualId = mockMvc.perform(MockMvcRequestBuilders.post("/api/todos")
                        .accept(MediaType.APPLICATION_JSON) //response 設定型別
                        .contentType(MediaType.APPLICATION_JSON) // request 設定型別
                        .content(String.valueOf(todoObject))) // body 內容
                .andExpect(status().isCreated()) // 預期回應的status code 為 201(Created)
                .andReturn().getResponse().getContentAsString();

        // [Assert] 判定回傳的body是否跟預期的一樣
        assertEquals(2,  Integer.parseInt(actualId));
    }
    @Transactional
    @Test
    //@Disabled
    public void testUpdateTodoSuccess() throws Exception {
        log("Update起");
        JSONObject todoObject = new JSONObject();
        todoObject.put("status", 2);

        // [Act] 模擬網路呼叫[PUT] /api/todos/{id}
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON) // request 設定型別
                        .content(String.valueOf(todoObject))) // body 內容
                .andExpect(status().isOk()); // [Assert] 預期回應的status code 為 200(OK)
        log("Update尾");
    }
    @Transactional
    @Test
    public void testUpdateTodoButIdNotExist() throws Exception {
        JSONObject todoObject = new JSONObject();
        todoObject.put("status", 2);

        // [Act] 模擬網路呼叫[PUT] /api/todos/{id}
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todos/100")
                        .contentType(MediaType.APPLICATION_JSON) // request 設定型別
                        .content(String.valueOf(todoObject))) // body 內容
                .andExpect(status().isBadRequest()); // [Assert] 預期回應的status code 為 400(Bad Request)

    }
    @Transactional
    @Test
    public void testDeleteTodoSuccess() throws Exception {
        // [Act] 模擬網路呼叫[DELETE] /api/todos/{id}
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todos/1")
                        .contentType(MediaType.APPLICATION_JSON)) // request 設定型別
                .andExpect(status().isNoContent()); // [Assert] 預期回應的status code 為 204(No Content)
    }
    @Transactional
    @Test
    public void testDeleteTodoButIdNotExist() throws Exception {
        // [Act] 模擬網路呼叫[DELETE] /api/todos/{id}
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todos/100")
                        .contentType(MediaType.APPLICATION_JSON)) // request 設定型別
                .andExpect(status().isBadRequest()); // [Assert] 預期回應的status code 為 400(Bad Request)
    }
    private void log(String s) {
        Iterator<Todo> tt = todoService.getTodos().iterator();
        while (true) {
            if (tt.hasNext()) {
                Todo tr = tt.next();
                logger.warn("整合測試"+s+"   "+tr.getTask());
                logger.warn("整合測試"+s+"   "+tr.getId().toString());
                logger.warn("整合測試"+s+"   "+tr.getUpdateTime().toString());
                logger.warn("整合測試"+s+"   "+tr.getCreateTime().toString());
                logger.warn("整合測試"+s+"   "+tr.getStatus().toString());
                logger.warn("---------------------------------");
                logger.warn("                                              ");
            } else {
                logger.warn ("整合測試"+s+"   "+"空的@@@@@@@@@@@@@@");
                break;
            }
        }

    }

}