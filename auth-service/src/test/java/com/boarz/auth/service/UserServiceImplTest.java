package com.boarz.auth.service;

import com.boarz.auth.entity.Role;
import com.boarz.auth.entity.User;
import com.boarz.auth.entity.UserUsernameType;
import com.boarz.auth.exception.*;
import com.boarz.auth.jwt.JwtFactory;
import com.boarz.auth.jwt.JwtWrapper;
import com.boarz.auth.repo.RoleRepo;
import com.boarz.auth.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
class UserServiceImplTest {

  @InjectMocks private UserServiceImpl userService;

  @Mock private UserRepo userRepository;
  @Mock private RoleRepo roleRepo;
  @Mock private TimeService timeService;
  @Mock private Environment env;
  @Mock private PasswordEncoder passwordEncoder;
  @Mock private JwtFactory jwtFactory;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void register_successfully_newUser() {
    Role role = new Role("USER", "DESC");
    User user = new User();
    user.setPhoneNumber("09110000000");
    user.setEmail("");
    user.setType(UserUsernameType.PHONE_NUMBER);

    UserServiceImpl userServiceSpy = Mockito.spy(userService);

    Mockito.when(roleRepo.existsByName("USER")).thenReturn(true);
    Mockito.when(roleRepo.findByName("USER")).thenReturn(role);
    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(false);
    Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
    Mockito.when(timeService.nowUnix()).thenReturn(1500L);
//    Mockito.doReturn(true).when(userServiceSpy).handleSMSOrEmailRegister(any(User.class));

    User savedUser =
        userServiceSpy.register(user.getEmail(), user.getPhoneNumber(), user.getType(), "");
    assertEquals(user.getUsername(), savedUser.getUsername());
  }

  @Test
  void register_successfully_unVerifiedUser() {
    Role role = new Role("USER", "DESC");
    User user = new User();
    user.setPhoneNumber("09110000000");
    user.setEmail("");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setVerified(false);

    UserServiceImpl userServiceSpy = Mockito.spy(userService);

    Mockito.when(roleRepo.existsByName("USER")).thenReturn(true);
    Mockito.when(roleRepo.findByName("USER")).thenReturn(role);
    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(user);
    Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
    Mockito.when(timeService.nowUnix()).thenReturn(1500L);
//    Mockito.doReturn(true).when(userServiceSpy).handleSMSOrEmailRegister(any(User.class));

    User savedUser =
        userServiceSpy.register(user.getEmail(), user.getPhoneNumber(), user.getType(), "");
    assertEquals(user.getUsername(), savedUser.getUsername());
  }

  @Test
  void register_falied_roleNotFound() {
    User user = new User();
    user.setPhoneNumber("09110000000");
    user.setEmail("");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setVerified(false);

    Mockito.when(roleRepo.findByName(any(String.class))).thenReturn(null);

    assertThrows(
        NoUserRoleFoundException.class,
        () -> {
          userService.register(
              user.getEmail(), user.getPhoneNumber(), UserUsernameType.PHONE_NUMBER, anyString());
        });
  }

  @Test
  void register_falied_verifiedUserExists() {
    Role role = new Role("USER", "DESC");
    User user = new User();
    user.setPhoneNumber("09110000000");
    user.setEmail("");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setVerified(true);

    Mockito.when(roleRepo.existsByName("USER")).thenReturn(true);
    Mockito.when(roleRepo.findByName(any(String.class))).thenReturn(role);
    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(user);

    assertThrows(
        VerifiedUserExistsException.class,
        () -> {
          userService.register(
              user.getEmail(), user.getPhoneNumber(), UserUsernameType.PHONE_NUMBER, anyString());
        });
  }

  @Test
  void verify_successfully() {
    User user = new User();
    user.setPhoneNumber("09110000000");
    user.setUsername("09110000000");
    user.setEmail("");
    user.setVerificationCode("verification_code");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setVerified(false);

    Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(true);
    Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);
    Mockito.when(env.getProperty(anyString())).thenReturn("2");
    Mockito.when(timeService.now())
        .thenReturn(LocalDateTime.ofInstant(Instant.ofEpochSecond(1608373228), ZoneOffset.UTC));
    Mockito.when(timeService.convertUnixTimeToLocalDateTimeUtc(anyLong()))
        .thenReturn(LocalDateTime.ofInstant(Instant.ofEpochSecond(1608373228), ZoneOffset.UTC));
    Mockito.when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");

    // This one gets you back the input argument as the return parameter
    Mockito.when(userRepository.save(any(User.class)))
        .thenAnswer(
            new Answer() {
              public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
              }
            });

    User verified = userService.verify(user.getUsername(), "verification_code", "password");
    assertEquals(verified.isVerified(), true);
  }

  @Test
  void verify_failed_userDoesNotExist() {
    User user = new User();
    user.setPhoneNumber("09110000000");
    user.setUsername("09110000000");
    user.setEmail("");
    user.setType(UserUsernameType.PHONE_NUMBER);

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(false);

    assertThrows(
        NotFoundException.class,
        () -> {
          userService.verify(user.getUsername(), "verification_code", "password");
        });
  }

  @Test
  void verify_failed_verificationCodeExpired() {
    User user = new User();
    user.setPhoneNumber("09110000000");
    user.setUsername("09110000000");
    user.setEmail("");
    user.setType(UserUsernameType.PHONE_NUMBER);

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);
    Mockito.when(env.getProperty(anyString())).thenReturn("0");
    Mockito.when(timeService.now())
        .thenReturn(LocalDateTime.ofInstant(Instant.ofEpochSecond(1608373228), ZoneOffset.UTC));
    Mockito.when(timeService.convertUnixTimeToLocalDateTimeUtc(anyLong()))
        .thenReturn(LocalDateTime.ofInstant(Instant.ofEpochSecond(1608373227), ZoneOffset.UTC));

    assertThrows(
        VerificationCodeExpiredException.class,
        () -> {
          userService.verify(user.getUsername(), "verification_code", "password");
        });
  }

  @Test
  void verify_failed_wrongVerificationCode() {
    User user = new User();
    user.setPhoneNumber("09110000000");
    user.setUsername("09110000000");
    user.setEmail("");
    user.setVerificationCode("verification_code");
    user.setType(UserUsernameType.PHONE_NUMBER);

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);
    Mockito.when(env.getProperty(anyString())).thenReturn("0");
    Mockito.when(timeService.now())
        .thenReturn(LocalDateTime.ofInstant(Instant.ofEpochSecond(1608373228), ZoneOffset.UTC));
    Mockito.when(timeService.convertUnixTimeToLocalDateTimeUtc(anyLong()))
        .thenReturn(LocalDateTime.ofInstant(Instant.ofEpochSecond(1608373228), ZoneOffset.UTC));

    assertThrows(
        WrongVerificationCodeException.class,
        () -> {
          userService.verify(user.getUsername(), "wrong_verification_code", "password");
        });
  }

  @Test
  void login_successfully() {
    Role role = new Role("USER", "DESC");
    User user = new User();
    user.setPhoneNumber("09110000000");
    user.setUsername("09110000000");
    user.setEmail("");
    user.setVerificationCode("verification_code");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setActive(true);
    user.setVerified(true);
    user.setPassword("user_in_db_password");
    user.setRoles(Arrays.asList(role));

    Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(true);
    Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);
    Mockito.when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
    Mockito.when(jwtFactory.generateToken(anyString(), any(JwtWrapper.class))).thenReturn("token");

    String token = userService.login(user.getUsername(), "password");
    assertEquals("token", token);
  }

  @Test
  void login_failed_userDoesNotExist() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);

    Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(false);

    assertThrows(
        NotFoundException.class,
        () -> {
          userService.login(user.getUsername(), anyString());
        });
  }

  @Test
  void login_failed_userIsNotActivated() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setActive(false);

    Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(true);
    Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);

    assertThrows(
        NotActiveUserException.class,
        () -> {
          userService.login(user.getUsername(), "password");
        });
  }

  @Test
  void login_failed_userIsNotVerified() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setActive(true);
    user.setVerified(false);

    Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(true);
    Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);

    assertThrows(
        NotVerifiedUserException.class,
        () -> {
          userService.login(user.getUsername(), "password");
        });
  }

  @Test
  void login_failed_passwordIsWrong() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setActive(true);
    user.setVerified(true);
    user.setPassword("password");

    Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(true);
    Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);
    Mockito.when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

    assertThrows(
        WrongLoginPasswordException.class,
        () -> {
          userService.login(user.getUsername(), "password");
        });
  }

  @Test
  void forgetPasswordRequest_successfully() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setVerified(true);
    user.setActive(true);

    UserServiceImpl userServiceSpy = Mockito.spy(userService);

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);
//    Mockito.doReturn(true).when(userServiceSpy).handleSMSOrEmailRegister(any(User.class));
    Mockito.when(userRepository.save(any(User.class)))
        .thenAnswer(
            new Answer() {
              public Object answer(InvocationOnMock invocation) {
                return invocation.getArguments()[0];
              }
            });

    User savedUser = userServiceSpy.forgetPasswordRequest(user.getUsername());
    assertEquals(6, savedUser.getForgetPasswordCode().length());
  }

  @Test
  void forgetPasswordRequest_failed_userDoesNotExist() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setVerified(true);
    user.setActive(true);

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(false);

    assertThrows(
        NotFoundException.class,
        () -> {
          userService.forgetPasswordRequest(user.getUsername());
        });
  }

  @Test
  void forgetPasswordRequest_failed_userIsNotActivated() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setActive(false);

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);

    assertThrows(
        NotActiveUserException.class,
        () -> {
          userService.forgetPasswordRequest(anyString());
        });
  }

  @Test
  void forgetPasswordRequest_failed_userIsNotVerified() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setActive(true);
    user.setVerified(false);

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);

    assertThrows(
        NotVerifiedUserException.class,
        () -> {
          userService.forgetPasswordRequest(anyString());
        });
  }

  @Test
  void forgetPasswordVerifying_successfully() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setActive(true);
    user.setVerified(true);
    user.setPassword("last_password");
    user.setForgetPasswordCode("forget_password_code");

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);
    Mockito.when(timeService.now())
        .thenReturn(LocalDateTime.ofInstant(Instant.ofEpochSecond(1608373228), ZoneOffset.UTC));
    Mockito.when(timeService.convertUnixTimeToLocalDateTimeUtc(anyLong()))
        .thenReturn(LocalDateTime.ofInstant(Instant.ofEpochSecond(1608373228), ZoneOffset.UTC));
    Mockito.when(env.getProperty(anyString())).thenReturn("0");
    Mockito.when(userRepository.save(user)).thenReturn(user);
    Mockito.when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
    Mockito.when(passwordEncoder.encode("new_password")).thenReturn("new_password");

    User updatedUser =
        userService.forgetPasswordVerifying(
            user.getUsername(), "forget_password_code", "new_password");
    assertEquals(updatedUser.getPassword(), "new_password");
  }

  @Test
  void forgetPasswordVerifying_failed_userDoesNotExist() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(false);

    assertThrows(
        NotFoundException.class,
        () -> {
          userService.forgetPasswordVerifying(
              user.getUsername(), "forget_password_code", "new_password");
        });
  }

  @Test
  void forgetPasswordVerifying_failed_userIsNotActivated() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setActive(false);

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(user);

    assertThrows(
        NotActiveUserException.class,
        () -> {
          userService.forgetPasswordVerifying(
              user.getUsername(), "forget_password_code", "new_password");
        });
  }

  @Test
  void forgetPasswordVerifying_failed_userIsNotVerified() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setActive(true);
    user.setVerified(false);

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(user);

    assertThrows(
        NotVerifiedUserException.class,
        () -> {
          userService.forgetPasswordVerifying(
              user.getUsername(), "forget_password_code", "new_password");
        });
  }

  @Test
  void forgetPasswordVerifying_failed_forgetPasswordCodeIsExpired() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setActive(true);
    user.setVerified(true);

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(user);
    Mockito.when(timeService.now())
        .thenReturn(LocalDateTime.ofInstant(Instant.ofEpochSecond(1608373228), ZoneOffset.UTC));
    Mockito.when(timeService.convertUnixTimeToLocalDateTimeUtc(anyLong()))
        .thenReturn(LocalDateTime.ofInstant(Instant.ofEpochSecond(1608373227), ZoneOffset.UTC));
    Mockito.when(env.getProperty(anyString())).thenReturn("0");

    assertThrows(
        ForgetPasswordCodeExpiredException.class,
        () -> {
          userService.forgetPasswordVerifying(
              user.getUsername(), "forget_password_code", "new_password");
        });
  }

  @Test
  void forgetPasswordVerifying_failed_wrongForgetPasswordCode() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setActive(true);
    user.setVerified(true);
    user.setForgetPasswordCode("forget_password_code");

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(user);
    Mockito.when(timeService.now())
        .thenReturn(LocalDateTime.ofInstant(Instant.ofEpochSecond(1608373228), ZoneOffset.UTC));
    Mockito.when(timeService.convertUnixTimeToLocalDateTimeUtc(anyLong()))
        .thenReturn(LocalDateTime.ofInstant(Instant.ofEpochSecond(1608373227), ZoneOffset.UTC));
    Mockito.when(env.getProperty(anyString())).thenReturn("0");

    assertThrows(
        ForgetPasswordCodeExpiredException.class,
        () -> {
          userService.forgetPasswordVerifying(
              user.getUsername(), "forget_password_code", "new_password");
        });
  }

  @Test
  void changePassword_successfully() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setActive(true);
    user.setVerified(true);
    user.setPassword("last_password");

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(anyString())).thenReturn(user);
    Mockito.when(userRepository.save(user)).thenReturn(user);
    Mockito.when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
    Mockito.when(passwordEncoder.encode("new_password")).thenReturn("new_password");

    User updatedUser =
        userService.changePassword(user.getUsername(), "last_password", "new_password");
    assertEquals(updatedUser.getPassword(), "new_password");
  }

  @Test
  void changePassword_failed_userDoesNotExist() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(false);

    assertThrows(
        NotFoundException.class,
        () -> {
          userService.changePassword(user.getUsername(), "last_password", "new_password");
        });
  }

  @Test
  void changePassword_failed_userIsNotActivated() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setActive(false);

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(user);

    assertThrows(
        NotActiveUserException.class,
        () -> {
          userService.changePassword(user.getUsername(), "last_password", "new_password");
        });
  }

  @Test
  void changePassword_failed_userIsNotVerified() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setActive(true);
    user.setVerified(false);

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(user);

    assertThrows(
        NotVerifiedUserException.class,
        () -> {
          userService.changePassword(user.getUsername(), "last_password", "new_password");
        });
  }

  @Test
  void changePassword_failed_lastPasswordIsNotCorrect() {
    User user = new User();
    user.setUsername("09110000000");
    user.setType(UserUsernameType.PHONE_NUMBER);
    user.setActive(true);
    user.setVerified(true);

    Mockito.when(userRepository.existsByUsername(any(String.class))).thenReturn(true);
    Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(user);
    Mockito.when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

    assertThrows(
        WrongPasswordForChanginPasswordException.class,
        () -> {
          userService.changePassword(user.getUsername(), "last_password", "new_password");
        });
  }
}
