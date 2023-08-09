package nash.example.todolist.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    public String tag;

    @ManyToMany(cascade=CascadeType.ALL, mappedBy="tags")
    Set<Todo> todos;
}
