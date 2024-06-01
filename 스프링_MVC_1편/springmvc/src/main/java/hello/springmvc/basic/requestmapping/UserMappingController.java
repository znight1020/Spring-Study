package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequestMapping("/users")
@RestController
public class UserMappingController {
    ArrayList<User> userList = new ArrayList<>();

    @GetMapping
    public List<User> list(){
        userList.add(new User(1L,"이현수", "test1@test.com", 26));
        userList.add(new User(2L,"차봉석", "test2@test.com", 27));
        return userList;
    }

    @GetMapping("/{userId}")
    public User findUser(@PathVariable(name = "userId") Long userId){
        for (User user : userList) if(Objects.equals(user.getId(), userId)) return user;
        return null;
    }

    @PostMapping("/")
    public String register(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "age") int age){
        User user = new User(id,name,email,age);
        userList.add(user);

        return "ok";
    }

    @PatchMapping("{userId}")
    public String update(
            @PathVariable(name = "userId") Long userId,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email
    ){
        User user = null;
        for (User u : userList) if(Objects.equals(u.getId(), userId)) user = u;

        if(user != null){
            user.setName(name);
            user.setEmail(email);
            return "ok";
        } else {
            return "fail";
        }
    }

    @DeleteMapping("{userId}")
    public String delete(@PathVariable(name = "userId") Long userId){
        for (User user : userList) {
            if(Objects.equals(userId, user.getId())) {
                userList.remove(user);
                return "ok";
            }
        }
        return "fail";
    }
}
