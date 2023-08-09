package nash.example.todolist.model.dao;

import nash.example.todolist.model.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Integer> {
}
