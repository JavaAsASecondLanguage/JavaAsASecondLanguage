package io.github.javaasasecondlanguage.flitter.resource;

import io.github.javaasasecondlanguage.flitter.dto.AuthError;
import io.github.javaasasecondlanguage.flitter.dto.ResultOrErrorDto;
import io.github.javaasasecondlanguage.flitter.dto.SubscriptionDto;
import io.github.javaasasecondlanguage.flitter.dto.UserDto;
import io.github.javaasasecondlanguage.flitter.entity.Subscription;
import io.github.javaasasecondlanguage.flitter.resource.mappers.AuthErrorMapper;
import io.github.javaasasecondlanguage.flitter.service.SubscriptionService;
import io.github.javaasasecondlanguage.flitter.service.UserService;
import io.github.javaasasecondlanguage.flitter.utils.Either;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class SubscriptionResource {
  private final SubscriptionService subscriptionService;
  private final UserService userService;

  public SubscriptionResource(SubscriptionService subscriptionService, UserService userService) {
    this.subscriptionService = subscriptionService;
    this.userService = userService;
  }

  @PostMapping("/subscribe")
  ResponseEntity<ResultOrErrorDto<String>> subscribe(@RequestBody SubscriptionDto subscriptionDto) {
    Either<UserDto, AuthError> userByToken = userService.getUserByToken(subscriptionDto.getSubscriberToken());
    Optional<UserDto> subscriber = userByToken.first();
    if (subscriber.isEmpty()) {
      return userByToken.second().map(SubscriptionResource::toError).orElseThrow();
    }
    return userService.getUserByName(subscriptionDto.getPublisherName())
        .map(publisher -> subscriptionService.subscribe(subscriber.get(), publisher))
        .map(Subscription::getId)
        .map(UUID::toString)
        .map(ResultOrErrorDto::result)
        .map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.badRequest().body(ResultOrErrorDto.error("Publisher not found")));
  }

  @PostMapping("/unsubscribe")
  ResponseEntity<ResultOrErrorDto<String>> unsubscribe(@RequestBody SubscriptionDto subscriptionDto) {
    Either<UserDto, AuthError> userByToken = userService.getUserByToken(subscriptionDto.getSubscriberToken());
    Optional<UserDto> subscriber = userByToken.first();
    if (subscriber.isEmpty()) {
      return userByToken.second().map(SubscriptionResource::toError).orElseThrow();
    }
    return userService.getUserByName(subscriptionDto.getPublisherName())
        .map(publisher -> subscriptionService.unsubscribe(subscriber.get(), publisher))
        .map(success -> success
            ? ResponseEntity.ok(ResultOrErrorDto.result("ok"))
            : ResponseEntity.badRequest().body(ResultOrErrorDto.<String>error("Subscription not found"))
        )
        .orElseThrow();
  }

  @GetMapping("/subscribers/list/{userToken}")
  ResponseEntity<ResultOrErrorDto<List<String>>> subscribers(@PathVariable("userToken") String userToken) {
    return userService.getUserByToken(userToken)
        .mapFirst(subscriptionService::subscribers)
        .mapFirst(list -> list.stream().map(SubscriptionDto::getSubscriberName).collect(Collectors.toList()))
        .result(SubscriptionResource::toResultList, SubscriptionResource::toErrorList)
        .orElseThrow();
  }

  @GetMapping("/publishers/list/{userToken}")
  ResponseEntity<ResultOrErrorDto<List<String>>> publishers(@PathVariable("userToken") String userToken) {
    return userService.getUserByToken(userToken)
        .mapFirst(subscriptionService::publishers)
        .mapFirst(list -> list.stream().map(SubscriptionDto::getPublisherName).collect(Collectors.toList()))
        .result(SubscriptionResource::toResultList, SubscriptionResource::toErrorList)
        .orElseThrow();
  }

  private static ResponseEntity<ResultOrErrorDto<List<String>>> toResultList(List<String> users) {
    return ResponseEntity.ok(ResultOrErrorDto.result(users));
  }

  private static ResponseEntity<ResultOrErrorDto<List<String>>> toErrorList(AuthError error) {
    return ResponseEntity.badRequest().body(ResultOrErrorDto.error(AuthErrorMapper.toRestError(error)));
  }

  private static ResponseEntity<ResultOrErrorDto<String>> toError(AuthError error) {
    return ResponseEntity.badRequest().body(ResultOrErrorDto.error(AuthErrorMapper.toRestError(error)));
  }
}
