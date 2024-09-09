package org.likelion.imteacherproject.domain;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class StudentRepository {

    private static final Map<Long, Student> database = new HashMap<>();

    public Student save(Student student) {
        database.put(student.getId(), student);
        return database.get(student.getId());
    }

    public Optional<Student> findById(Long id){
        return Optional.ofNullable(database.get(id));
    }

    public List<Student> findAll(){
        return database.values().stream().toList();
    }
    public void deleteById(Long id){
        database.remove(id);
    }

    public void clear(){
        database.clear();
    }

}
