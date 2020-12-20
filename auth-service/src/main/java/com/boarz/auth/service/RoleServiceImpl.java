package com.boarz.auth.service;

import com.boarz.auth.entity.Role;
import com.boarz.auth.entity.User;
import com.boarz.auth.exception.NotFoundException;
import com.boarz.auth.exception.NotPermittedException;
import com.boarz.auth.exception.RoleNameIsNullException;
import com.boarz.auth.exception.RoleWithThisNameExistsException;
import com.boarz.auth.jwt.JwtFactory;
import com.boarz.auth.jwt.UserRoleModel;
import com.boarz.auth.repo.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

  @Autowired private RoleRepo roleRepository;
  @Autowired private JwtFactory jwtFactory;

  @Override
  public Role add(String name, String description, String token) {
    if (!this.checkAdminRoleInToken(token)) throw new NotPermittedException();
    if (name == null || name.isEmpty()){
      throw new RoleNameIsNullException();
    }
    if (roleRepository.findByName(name) != null){
      throw new RoleWithThisNameExistsException();
    }
    return roleRepository.save(new Role(name, description));
  }

  @Override
  public Role update(long id, String name, String description, String token) {
    if (!this.checkAdminRoleInToken(token)) throw new NotPermittedException();
    if (name == null || name.isEmpty()){
      throw new RoleNameIsNullException();
    }
    if (roleRepository.findById(id) == null){
      throw new NotFoundException("Role Not Found");
    }
    return roleRepository.save(new Role(id, name, description));
  }

  @Override
  public Role findOne(long id, String token) {
    if (!this.checkAdminRoleInToken(token)) throw new NotPermittedException();
    if (roleRepository.findById(id) == null){
      throw new NotFoundException("Role Not Found");
    }
    return roleRepository.findById(id).get();
  }

  @Override
  public List<Role> findAll(String token) {
    if (!this.checkAdminRoleInToken(token)) throw new NotPermittedException();
    return roleRepository.findAll();
  }

  @Override
  public boolean delete(long id, String token) {
    if (!this.checkAdminRoleInToken(token)) throw new NotPermittedException();
    if (roleRepository.findById(id) == null){
      throw new NotFoundException("Role Not Found");
    }
    roleRepository.deleteById(id);
    return true;
  }

  private boolean checkAdminRoleInToken(String token){
    UserRoleModel userRoleModel = jwtFactory.parseToken(token);
    if (userRoleModel.getRole().equalsIgnoreCase("admin")) return true;
    else return false;
  }
}
