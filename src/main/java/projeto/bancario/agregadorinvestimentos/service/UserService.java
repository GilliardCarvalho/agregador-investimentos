package projeto.bancario.agregadorinvestimentos.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import projeto.bancario.agregadorinvestimentos.controller.dto.AccountResponseDto;
import projeto.bancario.agregadorinvestimentos.controller.dto.CreateAccountDto;
import projeto.bancario.agregadorinvestimentos.controller.dto.UpdateUserDto;
import projeto.bancario.agregadorinvestimentos.entity.Account;
import projeto.bancario.agregadorinvestimentos.entity.BillingAddress;
import projeto.bancario.agregadorinvestimentos.entity.User;
import projeto.bancario.agregadorinvestimentos.repository.AccountRepository;
import projeto.bancario.agregadorinvestimentos.repository.UserRepository;
import projeto.bancario.agregadorinvestimentos.controller.dto.CreateUserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserService(UserRepository userRepository,
                       AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    public UUID createUser(CreateUserDTO createUserDTO) {

       //DTO -> Entity
        var entity = new User(
                null,
                createUserDTO.username(),
                createUserDTO.email(),
                createUserDTO.password(),
                null,
                null);
        var userSaved = userRepository.save(entity);
            return userSaved.getUserId();
    }


    public User getUserById(UUID userId) {

        return userRepository.findById(userId).orElse(null);

    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }


    public boolean updateUserById(UUID userId, UpdateUserDto updateUserDto) {
        var userEntity = userRepository.findById(userId);

        if(userEntity.isPresent()){
            var user = userEntity.get();

            if(updateUserDto.username() != null) {
                user.setUsername(updateUserDto.username());
            }
            if(updateUserDto.password() != null) {
                user.setPassword(updateUserDto.password());
            }

            userRepository.save(user);
            return true;
        }

        return false;
    }

    public void deleteById(UUID userId){
        var userExists = userRepository.existsById(userId);

        if(userExists){
            userRepository.deleteById(userId);
        }
    }

    @Transactional
    public UUID createAccount(UUID userId, CreateAccountDto createAccountDto) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //DTO->Entity
        var account = new Account(
           null,
           user,
                null,
           createAccountDto.description(),
                new ArrayList<>()

        );

        var billingAddress = new BillingAddress(
            null,
                account,
                createAccountDto.street(),
                createAccountDto.number()
        );

        account.setBillingAddress(billingAddress);
        Account savedAccount = accountRepository.save(account);
        return savedAccount.getAccountId();
    }

    public List<AccountResponseDto> listAccounts(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return user.getAccounts()
                .stream()
                .map(ac->
                        new AccountResponseDto(ac.getAccountId().toString(), ac.getDescription()))
                .toList();
    }
}
