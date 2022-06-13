package com.user.User.controller;

import com.user.User.dto.ResponseDTO;
import com.user.User.dto.UserDTO;
import com.user.User.dto.UserLoginDTO;
import com.user.User.model.UserContact;
import com.user.User.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/userregistration")
@Slf4j
public class UserRegistrationController
{
    @Autowired
    private IUserService Iuserregistrationservice;

    //Ability to call api to register user record
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addUser(@Valid @RequestBody UserDTO userDTO){
        String newUser= Iuserregistrationservice.addUser(userDTO);
        ResponseDTO responseDTO=new ResponseDTO("User Registered Successfully",newUser);
        return new ResponseEntity(responseDTO, HttpStatus.CREATED);
    }


    //Ability to call api to get all user record
    @GetMapping( "/getAll")
    public ResponseEntity<String> getAllUser() {
        List<UserContact> listOfUsers = Iuserregistrationservice.getAllUsers();
        ResponseDTO dto = new ResponseDTO("User retrieved successfully (:",listOfUsers);
        return new ResponseEntity(dto,HttpStatus.OK);
    }

    //Ability to call api to get user record
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        return new ResponseEntity<ResponseDTO>(Iuserregistrationservice.loginUser(userLoginDTO),HttpStatus.OK);
    }

    /**
     *
     * @param email
     * @param password
     * @return
     */

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestParam String email, @RequestParam String password) {
        String resp = Iuserregistrationservice.forgotPassword(email,password);
        return new ResponseEntity(resp, HttpStatus.OK);
    }

    //Ability to call api to get  user record by emailId
    @GetMapping("/getByEmailId/{emailId}")
    public ResponseEntity<ResponseDTO> getUserByEmailId(@PathVariable("emailId") String email) {
        return new ResponseEntity<ResponseDTO>( new
                ResponseDTO("Get User Data by Email",
                Iuserregistrationservice.getUserByEmailId(email)), HttpStatus.OK);
    }

    //Ability to call api to Update  user record by id
    @PutMapping("/update/{contactId}")
    public ResponseEntity<String> updateRecordById(@PathVariable int  contactId,@Valid @RequestBody UserDTO userDTO){
        UserContact entity = Iuserregistrationservice.updateRecordById(contactId,userDTO);
        ResponseDTO dto = new ResponseDTO("User Record updated successfully",entity);
        return new ResponseEntity(dto,HttpStatus.ACCEPTED);
    }
    @GetMapping("/verify/{token}")
    public ResponseEntity<ResponseDTO> verifyUser(@PathVariable String token) {
        return Iuserregistrationservice.verify(token);
    }

    //--------------------API Calls for RestTemplate----------------------------//

    @GetMapping("/findById/{contactId}")
    public UserContact getByIdAPI(@PathVariable int contactId){
        UserContact user =  Iuserregistrationservice.getByIdAPI(contactId);
        return user;
    }

}
