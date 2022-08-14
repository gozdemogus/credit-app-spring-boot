package gradproject.demo.controller;

import gradproject.demo.dto.UserDTO;
import gradproject.demo.entity.User;
import gradproject.demo.exception.RecordNotFoundException;
import gradproject.demo.exception.UserNotFoundException;
import gradproject.demo.mapper.UserMapper;
import gradproject.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public List<UserDTO> getAllUsers(){
        List<User> all = userService.getAllUsers();
        List<UserDTO> userDTOList = UserMapper.INSTANCE.toUserDTOList(all);
        return userDTOList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        if (user == null) {
            throw new RecordNotFoundException("User doesn't exists with id: " + id);
        }
        UserDTO userDTO = UserMapper.INSTANCE.toUserDTO(user);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @PostMapping("/create")
    public ResponseEntity saveNewUserFromDto(@RequestBody UserDTO userDto) {

        User user = userService.findUserByIdentityNumber(userDto.getIdentityNumber());
        User userNew = new User();
        if(user != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with identity number " + user.getIdentityNumber() + " exists!");
        }
        else{
            User convertedUser = UserMapper.INSTANCE.toUser(userDto);
            userNew = userService.saveUser(convertedUser);
        }
        return ResponseEntity.status(HttpStatus.OK).body(userNew);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody UserDTO userDto) {
        User user = userService.findUserById(id);
        User updatedUser = null;
        if(user != null){
            if (userDto.getId() != user.getId()){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("ID can't be changed");
            }
             updatedUser = UserMapper.INSTANCE.toUser(userDto);
             userService.saveUser(updatedUser);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        User user = userService.findUserById(id);
        if(user != null) {
            userService.deleteUserByIdentity(user.getIdentityNumber());
            return ResponseEntity.status(HttpStatus.OK).body("User deleted.");
        }
        else{
            throw new UserNotFoundException(String.valueOf(id));
        }
    }
}