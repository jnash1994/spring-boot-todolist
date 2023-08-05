package nash.example.todolist.model.dao;

import nash.example.todolist.model.entity.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoDao extends CrudRepository<Todo, Integer> {
}
