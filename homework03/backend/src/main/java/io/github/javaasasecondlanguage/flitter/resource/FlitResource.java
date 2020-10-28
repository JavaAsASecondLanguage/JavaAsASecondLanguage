package io.github.javaasasecondlanguage.flitter.resource;

import io.github.javaasasecondlanguage.flitter.dto.FlitDto;
import io.github.javaasasecondlanguage.flitter.dto.PostFlitDto;
import io.github.javaasasecondlanguage.flitter.dto.ResultDto;
import io.github.javaasasecondlanguage.flitter.dto.ResultOrErrorDto;
import io.github.javaasasecondlanguage.flitter.dto.SubscriptionDto;
import io.github.javaasasecondlanguage.flitter.dto.UserDto;
import io.github.javaasasecondlanguage.flitter.resource.mappers.AuthErrorMapper;
import io.github.javaasasecondlanguage.flitter.service.FlitService;
import io.github.javaasasecondlanguage.flitter.service.SubscriptionService;
import io.github.javaasasecondlanguage.flitter.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/flit")
public class FlitResource {
  private final FlitService flitService;
  private final UserService userService;
  private final SubscriptionService subscriptionService;

  public FlitResource(FlitService flitService, UserService userService, SubscriptionService subscriptionService) {
    this.flitService = flitService;
    this.userService = userService;
    this.subscriptionService = subscriptionService;
  }

  @PostMapping("add")
  ResponseEntity<ResultOrErrorDto<FlitDto>> add(@RequestBody PostFlitDto postFlitCommand) {
    return userService.getUserByToken(postFlitCommand.getUserToken())
        .mapFirst(user -> flitService.post(user, postFlitCommand.getContent()))
        .mapFirst(FlitResource::toResult)
        .mapSecond(AuthErrorMapper::toRestError)
        .mapSecond(err -> ResultOrErrorDto.<FlitDto>error(err))
        .mapSecond(err -> ResponseEntity.badRequest().body(err))
        .result(Function.identity(), Function.identity())
        .orElseThrow();
  }

  @GetMapping("discover")
  ResultDto<List<FlitDto>> discover() {
    return ResultDto.of(flitService.discover());
  }

  @GetMapping("list/{userName}")
  ResponseEntity<ResultOrErrorDto<List<FlitDto>>> list(@PathVariable("userName") String userName) {
    return flitService.list(userName)
        .map(ResultOrErrorDto::result)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ResultOrErrorDto.error("user not found"))
        );

  }

  @GetMapping("list/feed/{userToken}")
  ResponseEntity<ResultOrErrorDto<List<FlitDto>>> feed(@PathVariable("userToken") String userToken) {
    return userService.getUserByToken(userToken)
        .mapFirst(subscriptionService::publishers)
        .mapFirst(l -> l.stream().map(SubscriptionDto::getPublisherName).map(UserDto::new).collect(Collectors.toList()))
        .mapFirst(flitService::getFlits)
        .mapFirst(FlitResource::toResult)
        .mapSecond(AuthErrorMapper::toRestError)
        .mapSecond(err -> ResultOrErrorDto.<List<FlitDto>>error(err))
        .mapSecond(err -> ResponseEntity.badRequest().body(err))
        .result(Function.identity(), Function.identity())
        .orElseThrow();
  }

  private static <T> ResponseEntity<ResultOrErrorDto<T>> toResult(T data) {
    return ResponseEntity.ok(ResultOrErrorDto.result(data));
  }

}
