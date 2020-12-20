package com.boarz.auth.service;

import com.boarz.auth.entity.Role;
import com.boarz.auth.exception.NotFoundException;
import com.boarz.auth.exception.RoleNameIsNullException;
import com.boarz.auth.exception.RoleWithThisNameExistsException;
import com.boarz.auth.exception.TokenNotValidateWithThisSecretPass;
import com.boarz.auth.jwt.JwtFactory;
import com.boarz.auth.jwt.UserRoleModel;
import com.boarz.auth.repo.RoleRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;

@RunWith(MockitoJUnitRunner.class)
class RoleServiceImplTest {

  @Mock RoleRepo roleRepository;
  @Mock JwtFactory jwtFactory;

  @InjectMocks RoleServiceImpl roleService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void add_successfully() {
    Role role = new Role("Role-Name", "Role-desc");
    UserRoleModel userRoleModel = new UserRoleModel("USERNAME", "ADMIN");

    Mockito.when(roleRepository.save(any(Role.class))).thenReturn(role);
    Mockito.when(jwtFactory.parseToken(anyString())).thenReturn(userRoleModel);

    Role createdRole = roleService.add(role.getName(), role.getDescription(), "token");
    assertEquals(createdRole, role);
  }

  @Test
  void add_fail_tokenIsNotValidated() {
    Role role = new Role("Role-Name", "Role-desc");

    Mockito.when(jwtFactory.parseToken(anyString())).thenThrow(TokenNotValidateWithThisSecretPass.class);

    assertThrows(
            TokenNotValidateWithThisSecretPass.class,
            () -> {
              roleService.add(role.getName(), role.getDescription(), "token");
            });
  }

  @Test
  void add_fail_roleWithThisNameExists() {
    Role role = new Role("Role-Name", "Role-desc");
    UserRoleModel userRoleModel = new UserRoleModel("USERNAME", "ADMIN");

    Mockito.when(roleRepository.save(any(Role.class))).thenReturn(role);
    Mockito.when(roleRepository.findByName(any(String.class))).thenReturn(role);
    Mockito.when(jwtFactory.parseToken(anyString())).thenReturn(userRoleModel);

    assertThrows(
        RoleWithThisNameExistsException.class,
        () -> {
          roleService.add(role.getName(), role.getDescription(), "token");
        });
  }

  @Test
  void add_fail_nullOrEmptyName() {
    Role role = new Role("", "Role-desc");
    UserRoleModel userRoleModel = new UserRoleModel("USERNAME", "ADMIN");

    Mockito.when(jwtFactory.parseToken(anyString())).thenReturn(userRoleModel);

    assertThrows(
        RoleNameIsNullException.class,
        () -> {
          roleService.add(role.getName(), role.getDescription(), "token");
        });
  }

  @Test
  void update_successfully() {
    Role role = new Role(1, "Role-Name", "Role-desc");
    UserRoleModel userRoleModel = new UserRoleModel("USERNAME", "ADMIN");

    Mockito.when(roleRepository.save(any(Role.class))).thenReturn(role);
    Mockito.when(jwtFactory.parseToken(anyString())).thenReturn(userRoleModel);

    Role updatedRole = roleService.update(role.getId(), role.getName(), role.getDescription(), "token");
    assertEquals(updatedRole, role);
  }

  @Test
  void update_fail_tokenIsNotValidated() {
    Role role = new Role("Role-Name", "Role-desc");

    Mockito.when(jwtFactory.parseToken(anyString())).thenThrow(TokenNotValidateWithThisSecretPass.class);

    assertThrows(
            TokenNotValidateWithThisSecretPass.class,
            () -> {
              roleService.update(anyLong(), role.getName(), role.getDescription(), "token");
            });
  }

  @Test
  void update_fail_nullOrEmptyName() {
    Role role = new Role(1, "", "Role-desc");
    UserRoleModel userRoleModel = new UserRoleModel("USERNAME", "ADMIN");

    Mockito.when(jwtFactory.parseToken(anyString())).thenReturn(userRoleModel);

    assertThrows(
        RoleNameIsNullException.class,
        () -> {
          roleService.add(role.getName(), role.getDescription(), "token");
        });
  }

  @Test
  void update_fail_roleDoesNotExist() {
    UserRoleModel userRoleModel = new UserRoleModel("USERNAME", "ADMIN");

    Mockito.when(roleRepository.findById(any(Long.class))).thenReturn(null);
    Mockito.when(jwtFactory.parseToken(anyString())).thenReturn(userRoleModel);

    assertThrows(
        NotFoundException.class,
        () -> {
          roleService.findOne(any(Long.class), "token");
        });
  }

  @Test
  void findOne_successfully() {
    Role role = new Role(1, "Role-Name", "Role-desc");
    UserRoleModel userRoleModel = new UserRoleModel("USERNAME", "ADMIN");

    Mockito.when(roleRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(role));
    Mockito.when(jwtFactory.parseToken(anyString())).thenReturn(userRoleModel);

    Role foundedRole = roleService.findOne(any(Long.class), "token");
    assertEquals(foundedRole, role);
  }

  @Test
  void findOne_fail_tokenIsNotValidated() {
    Mockito.when(jwtFactory.parseToken(anyString())).thenThrow(TokenNotValidateWithThisSecretPass.class);

    assertThrows(
            TokenNotValidateWithThisSecretPass.class,
            () -> {
              roleService.findOne(anyLong(), "token");
            });
  }

  @Test
  void findOne_fail_roleDoesNotExist() {
    UserRoleModel userRoleModel = new UserRoleModel("USERNAME", "ADMIN");

    Mockito.when(roleRepository.findById(anyLong())).thenReturn(null);
    Mockito.when(jwtFactory.parseToken(anyString())).thenReturn(userRoleModel);

    assertThrows(
        NotFoundException.class,
        () -> {
          roleService.findOne(anyLong(), "token");
        });
  }

  @Test
  void findAll_successfully() {
    Role role1 = new Role(1, "Role-Name-1", "Role-desc-1");
    Role role2 = new Role(2, "Role-Name-2", "Role-desc-2");
    Role role3 = new Role(3, "Role-Name-3", "Role-desc-3");
    UserRoleModel userRoleModel = new UserRoleModel("USERNAME", "ADMIN");

    List<Role> roles = new ArrayList<>();
    roles.add(role1);
    roles.add(role2);
    roles.add(role3);

    Mockito.when(roleRepository.findAll()).thenReturn(roles);
    Mockito.when(jwtFactory.parseToken(anyString())).thenReturn(userRoleModel);

    List<Role> foundedRoles = roleService.findAll("token");
    assertEquals(foundedRoles, roles);
  }

  @Test
  void findAll_fail_tokenIsNotValidated() {
    Mockito.when(jwtFactory.parseToken(anyString())).thenThrow(TokenNotValidateWithThisSecretPass.class);

    assertThrows(
            TokenNotValidateWithThisSecretPass.class,
            () -> {
              roleService.findAll("token");
            });
  }

  @Test
  void delete_successfully() {
    UserRoleModel userRoleModel = new UserRoleModel("USERNAME", "ADMIN");

    Mockito.doNothing().when(roleRepository).deleteById(anyLong());
    Mockito.when(roleRepository.findById(anyLong())).thenReturn(java.util.Optional.of(new Role()));
    Mockito.when(jwtFactory.parseToken(anyString())).thenReturn(userRoleModel);

    boolean result = roleService.delete(anyLong(), "token");

    assertEquals(result, true);
  }

  @Test
  void delete_fail_tokenIsNotValidated() {
    Role role = new Role("Role-Name", "Role-desc");

    Mockito.when(jwtFactory.parseToken(anyString())).thenThrow(TokenNotValidateWithThisSecretPass.class);

    assertThrows(
            TokenNotValidateWithThisSecretPass.class,
            () -> {
              roleService.delete(anyLong(), "token");
            });
  }

  @Test
  void delete_fail_roleDoesNotExist() {
    UserRoleModel userRoleModel = new UserRoleModel("USERNAME", "ADMIN");

    Mockito.when(roleRepository.findById(anyLong())).thenReturn(null);
    Mockito.when(jwtFactory.parseToken(anyString())).thenReturn(userRoleModel);

    assertThrows(
        NotFoundException.class,
        () -> {
          roleService.delete(anyLong(), "token");
        });
  }
}
