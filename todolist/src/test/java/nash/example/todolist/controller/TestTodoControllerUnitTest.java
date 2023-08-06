package nash.example.todolist.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nash.example.todolist.model.entity.Todo;
import nash.example.todolist.service.TodoService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestTodoControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

    private static final Logger logger = LoggerFactory.getLogger(TestTodoControllerUnitTest.class);

    @Transactional
    @Test
    public void testGetTodos() throws Exception {

        // 設定資料
        List<Todo> expectedList = new ArrayList();
        Todo todo = new Todo();
        todo.setTask("掃地");
        todo.setId(1);
        expectedList.add(todo);

        // 模擬todoService.getTodos() 回傳 expectedList
        Mockito.when(todoService.getTodos()).thenReturn(expectedList);

        // 模擬呼叫[GET] /api/todos
        String returnString = mockMvc.perform(MockMvcRequestBuilders.get("/api/todos")
                        .accept(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        Iterable<Todo> actualList = objectMapper.readValue(returnString, new TypeReference<Iterable<Todo>>() {
        });

        // 判定回傳的body是否跟預期的一樣
        assertEquals(expectedList, actualList);
        //log("Get");
    }


    @Test
    public void testrCreateTodo() throws Exception {
        //log("Create");
        // 設定資料
        Todo mockTodo = new Todo();
        mockTodo.setId(2);
        mockTodo.setTask("拖地");
        mockTodo.setStatus(1);

        JSONObject todoObject = new JSONObject();
        todoObject.put("id", 2);
        todoObject.put("task", "哈哈");

        // 模擬todoService.createTodo(todo) 回傳 id 1
        Mockito.when(todoService.createTodo(mockTodo)).thenReturn(1);

        // 模擬呼叫[POST] /api/todos
        String actual = mockMvc.perform(MockMvcRequestBuilders.post("/api/todos")
                        .accept(MediaType.APPLICATION_JSON) //response 設定型別
                        .contentType(MediaType.APPLICATION_JSON) // request 設定型別
                        .content(String.valueOf(todoObject))) // body 內容
                .andExpect(status().isCreated()) // 預期回應的status code 為 201(Created)
                .andReturn().getResponse().getContentAsString();
        log("Create");
    }
    @Transactional
    @Test
    public void testDeleteTodoSuccess() throws Exception {
        // 模擬todoService.deleteTodo(1) 成功回傳true
        Mockito.when(todoService.deleteTodo(1)).thenReturn(true);

        // 模擬呼叫[DELETE] /api/todos/{id}
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todos/1")
                        .accept(MediaType.APPLICATION_JSON_UTF8 ) //response 設定型別
                        .contentType(MediaType.APPLICATION_JSON)) // request 設定型別
                .andExpect(status().isNoContent()); // 預期回應的status code 應為 204(No Content)
        log("Delete");
    }
    private void log(String s) {
        Iterator<Todo> tt = todoService.getTodos().iterator();
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
