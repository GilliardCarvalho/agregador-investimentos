package projeto.bancario.agregadorinvestimentos.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import projeto.bancario.agregadorinvestimentos.controller.dto.CreateUserDTO;
import projeto.bancario.agregadorinvestimentos.controller.dto.UpdateUserDto;
import projeto.bancario.agregadorinvestimentos.entity.User;
import projeto.bancario.agregadorinvestimentos.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
    class UserServiceTest {

        @Mock
        private UserRepository userRepository;

        @InjectMocks
        private UserService userService;

        @Captor
        private ArgumentCaptor<User> userArgumentCaptor;

        @Captor
        private ArgumentCaptor<UUID> uuidArgumentCaptor;


        @Nested
        class createUser {

            @Test
            @DisplayName("Should Create a user with success")
            void shouldCreateAUserWithSuccess(){
                //Arrange
                var userId = UUID.randomUUID();
                var user = new User (
                        userId,
                        "username",
                        "email@email.com",
                        "password",
                        null,
                        null
                        );
                doReturn(user).when(userRepository).save(userArgumentCaptor.capture());
                var input = new CreateUserDTO(
                        "username",
                        "email@email.com",
                        "password"
                );
                // Act
               var output = userService.createUser(input);

               //Assert
                assertNotNull(output);
                var userCaptured = userArgumentCaptor.getValue();

                assertEquals(input.username(),userCaptured.getUsername());
                assertEquals(input.email(),userCaptured.getEmail());
                assertEquals(input.password(),userCaptured.getPassword());
                }
    }

        @Test
        @DisplayName("Should throw exception when error occurs")
         void shouldThrowExceptionWhenErrorOccurs() {
            //Arrange
            doThrow(new RuntimeException()).when(userRepository).save(any());

            var input = new CreateUserDTO(
                    "username",
                    "email@email.com",
                    "password"
            );
            // Act & Assert
            assertThrows(RuntimeException.class, () -> userService.createUser(input));
        }

        @Nested
        class getUserById {
            @Test
            @DisplayName("Should get user by id with success ")
            void shouldGetUserByIdWithSuccess() {
                //Arrange
                var userId = UUID.randomUUID();
                var user = new User (
                        userId,
                        "username",
                        "email@email.com",
                        "password",
                        null,
                        null
                );
                doReturn(Optional.of(user)).when(userRepository).findById(userId);

                //Act

                var output = userService.getUserById(user.getUserId());

                //Assert
                assertNotNull(output);
                assertEquals(user.getUserId(), output.getUserId());

                verify(userRepository).findById(uuidArgumentCaptor.capture());
                assertEquals(user.getUserId(), uuidArgumentCaptor.getValue());
            }

            @Test
            @DisplayName("Should return null when user does not exist")
            void shouldReturnNullWhenUserDoesNotExist() {
                //Arrange
                var userId = UUID.randomUUID();
                doReturn(Optional.empty()).when(userRepository).findById(userId);

                //Act
                var output = userService.getUserById(userId);

                //Assert
                assertNull(output);

                verify(userRepository).findById(uuidArgumentCaptor.capture());
                assertEquals(userId, uuidArgumentCaptor.getValue());
            }
        }

        @Nested
        class listUsers {
            @Test
            @DisplayName("Should list users with success")
            void shouldListUsersWithSuccess() {
                //Arrange
                var user = new User(
                        UUID.randomUUID(),
                        "username",
                        "email@email.com",
                        "password",
                        null,
                        null
                );
                var users = List.of(user);
                doReturn(users).when(userRepository).findAll();

                //Act
                var output = userService.listUsers();

                //Assert
                assertNotNull(output);
                assertEquals(1, output.size());
                assertEquals(user.getUserId(), output.getFirst().getUserId());

                verify(userRepository).findAll();
            }
        }

        @Nested
        class updateUserById {
            @Test
            @DisplayName("Should update user by id with success")
            void shouldUpdateUserByIdWithSuccess() {
                //Arrange
                var userId = UUID.randomUUID();
                var user = new User(
                        userId,
                        "username",
                        "email@email.com",
                        "password",
                        null,
                        null
                );
                var input = new UpdateUserDto("newUsername", "newPassword");
                doReturn(Optional.of(user)).when(userRepository).findById(userId);
                doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

                //Act
                var output = userService.updateUserById(userId, input);

                //Assert
                assertTrue(output);

                var userCaptured = userArgumentCaptor.getValue();
                assertEquals(input.username(), userCaptured.getUsername());
                assertEquals(input.password(), userCaptured.getPassword());
                assertEquals(user.getEmail(), userCaptured.getEmail());

                verify(userRepository).findById(uuidArgumentCaptor.capture());
                assertEquals(userId, uuidArgumentCaptor.getValue());
            }

            @Test
            @DisplayName("Should not update user when user does not exist")
            void shouldNotUpdateUserWhenUserDoesNotExist() {
                //Arrange
                var userId = UUID.randomUUID();
                var input = new UpdateUserDto("newUsername", "newPassword");
                doReturn(Optional.empty()).when(userRepository).findById(userId);

                //Act
                var output = userService.updateUserById(userId, input);

                //Assert
                assertFalse(output);

                verify(userRepository).findById(uuidArgumentCaptor.capture());
                assertEquals(userId, uuidArgumentCaptor.getValue());
                verify(userRepository, never()).save(any());
            }
        }

        @Nested
        class deleteById {
            @Test
            @DisplayName("Should delete user by id with success")
            void shouldDeleteUserByIdWithSuccess() {
                //Arrange
                var userId = UUID.randomUUID();
                doReturn(true).when(userRepository).existsById(userId);

                //Act
                userService.deleteById(userId);

                //Assert
                verify(userRepository).existsById(userId);
                verify(userRepository).deleteById(uuidArgumentCaptor.capture());
                assertEquals(userId, uuidArgumentCaptor.getValue());
            }

            @Test
            @DisplayName("Should not delete user when user does not exist")
            void shouldNotDeleteUserWhenUserDoesNotExist() {
                //Arrange
                var userId = UUID.randomUUID();
                doReturn(false).when(userRepository).existsById(userId);

                //Act
                userService.deleteById(userId);

                //Assert
                verify(userRepository).existsById(uuidArgumentCaptor.capture());
                assertEquals(userId, uuidArgumentCaptor.getValue());
                verify(userRepository, never()).deleteById(any());
            }
        }
    }
