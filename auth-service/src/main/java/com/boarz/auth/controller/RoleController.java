package com.boarz.auth.controller;

import com.boarz.auth.exception.BadInputException;
import com.boarz.auth.helper.ResponseHelper;
import com.boarz.auth.model.RoleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

  @Autowired private com.boarz.auth.service.RoleService roleService;

  @RequestMapping(value = "/add", method = RequestMethod.POST)
  public ResponseEntity add(
      @RequestBody RoleRequest request, BindingResult result, @RequestParam String token) {
    if (result.hasErrors()) throw new BadInputException(result.getFieldErrors());
    return ResponseHelper.response(roleService.add(request.getName(), request.getDescription(), token));
  }

  @RequestMapping(value = "/update", method = RequestMethod.PUT)
  public ResponseEntity update(
      @RequestBody RoleRequest request, BindingResult result, @RequestParam String token) {
    if (result.hasErrors()) throw new BadInputException(result.getFieldErrors());
    return ResponseHelper.response(
        roleService.update(request.getId(), request.getName(), request.getDescription(), token));
  }

  @RequestMapping(value = "/find", method = RequestMethod.GET)
  public ResponseEntity find(@RequestParam long id, @RequestParam String token) {
    return ResponseHelper.response(roleService.findOne(id, token));
  }

  @RequestMapping(value = "/find-all", method = RequestMethod.GET)
  public ResponseEntity findAll(@RequestParam String token) {
    return ResponseHelper.response(roleService.findAll(token));
  }

  @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
  public ResponseEntity delete(@RequestParam long id, @RequestParam String token) {
    return ResponseHelper.response(roleService.delete(id, token));
  }
}
