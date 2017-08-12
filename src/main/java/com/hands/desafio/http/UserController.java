package com.hands.desafio.http;

import com.hands.desafio.http.dto.UserResponse;
import com.hands.desafio.usecase.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @ApiOperation(value = "user", nickname = "User Search")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserResponse.class),
            @ApiResponse(code = 400, message = "NotFound"),
            @ApiResponse(code = 500, message = "Failure")})
    @GetMapping("/{page}")
    public ResponseEntity<List<UserResponse>> users(@PathVariable("page") final Integer page)
            throws URISyntaxException {
        log.debug("REST request to get list user");

        List<UserResponse> content = userService.findAll(page);

        return new ResponseEntity<>(content, HttpStatus.OK);
    }


    @ApiOperation(value = "User", nickname = "User Search By Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = UserResponse.class),
            @ApiResponse(code = 400, message = "NotFound"),
            @ApiResponse(code = 500, message = "Failure")})
    @GetMapping("/{id}/user")
    public ResponseEntity<UserResponse> findById(@PathVariable String id)
            throws URISyntaxException {
        log.info("REST request to get extrato");
        UserResponse content = userService.findById(id);
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<UserResponse> uploadUsers(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            userService.importUsers(new String(bytes, "UTF-8").split("\n"));
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
