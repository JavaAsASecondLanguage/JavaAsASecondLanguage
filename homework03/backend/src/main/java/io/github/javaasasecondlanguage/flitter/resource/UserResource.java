package io.github.javaasasecondlanguage.flitter.resource;

import io.github.javaasasecondlanguage.flitter.dto.ResultDto;
import io.github.javaasasecondlanguage.flitter.dto.ResultOrErrorDto;
import io.github.javaasasecondlanguage.flitter.dto.UserAuthDto;
import io.github.javaasasecondlanguage.flitter.dto.UserDto;
import io.github.javaasasecondlanguage.flitter.resource.mappers.AuthErrorMapper;
import io.github.javaasasecondlanguage.flitter.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserResource {
  private final UserService userService;

  public UserResource(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("register")
  ResponseEntity<ResultOrErrorDto<UserAuthDto>> register(@RequestBody UserDto userDto) {
    return userService.createUser(userDto)
        .mapSecond(AuthErrorMapper::toRestError)
        .map(ResultOrErrorDto::result, err -> ResultOrErrorDto.<UserAuthDto>error(err))
        .result(ResponseEntity::ok, u -> ResponseEntity.badRequest().body(u))
        .orElseThrow();
  }

  @GetMapping("list")
  ResultDto<List<String>> list() {
    List<UserDto> users = userService.getUsers();
    return ResultDto.of(users.stream().map(UserDto::getUserName).collect(Collectors.toList()));
  }
}
