package projeto.bancario.agregadorinvestimentos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projeto.bancario.agregadorinvestimentos.controller.dto.AccountResponseDto;
import projeto.bancario.agregadorinvestimentos.controller.dto.CreateAccountDto;
import projeto.bancario.agregadorinvestimentos.controller.dto.CreateUserDTO;
import projeto.bancario.agregadorinvestimentos.controller.dto.UpdateUserDto;
import projeto.bancario.agregadorinvestimentos.entity.User;
import projeto.bancario.agregadorinvestimentos.service.UserService;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UUID> createUser(@RequestBody CreateUserDTO createUserDTO) {

        UUID user = userService.createUser(createUserDTO);

        return ResponseEntity
                .created(URI.create("/v1/users/" + user.toString()))
                .body(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {

        User user = userService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable UUID userId,
                                                @RequestBody UpdateUserDto updateUserDto){
        var updated = userService.updateUserById(userId, updateUserDto);
        if (!updated) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID userId) {
        userService.deleteById(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<UUID> createAccount(@PathVariable UUID userId,
                                              @RequestBody CreateAccountDto createAccountDto) {
        UUID accountId = userService.createAccount(userId, createAccountDto);

        return ResponseEntity
                .created(URI.create("/v1/users/" + userId + "/accounts/" + accountId))
                .body(accountId);
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDto>> listAccounts(@PathVariable UUID userId) {
        var accounts = userService.listAccounts(userId);

        return ResponseEntity.ok(accounts);
    }



}

