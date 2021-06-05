package com.example.library.controller;

import com.example.library.entity.User;
import com.example.library.repository.BookRepository;
import com.example.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public List<User> getAllBooks(){
        return userRepository.findAll();
    }


    @GetMapping("/{id}")
    public User getBook(@PathVariable int id){
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("Yhis doesn't not exist"));
    }

    @PostMapping
    public User save(@RequestBody User user){
        Optional<User> bookisThere = userRepository.findUserByFullname(user.getFullname());
        if (bookisThere.isPresent()){
            throw new RuntimeException("This user already exist");
        }
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User update(@RequestBody User book, @PathVariable int id ){
        Optional<User> user1 = userRepository.findById(id);
        if (user1.isPresent()){
            user1.get().setFullname(book.getFullname());
            return userRepository.save(user1.get());
        }else {
            throw new RuntimeException("You can't edit this books, because it doesn't exist in our database");
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id ){
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
             userRepository.delete(user.get());
             return "the users has been deleted";
        }else {
            throw new RuntimeException("You can't delete this books, because it doesn't exist in our database");
        }
    }
}
