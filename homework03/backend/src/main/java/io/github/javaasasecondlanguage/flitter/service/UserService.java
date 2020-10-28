package io.github.javaasasecondlanguage.flitter.service;

import io.github.javaasasecondlanguage.flitter.dao.UserRepository;
import io.github.javaasasecondlanguage.flitter.dto.AuthError;
import io.github.javaasasecondlanguage.flitter.dto.UserAuthDto;
import io.github.javaasasecondlanguage.flitter.dto.UserDto;
import io.github.javaasasecondlanguage.flitter.entity.User;
import io.github.javaasasecondlanguage.flitter.utils.Either;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserService {
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  public Either<UserAuthDto, AuthError> createUser(UserDto userDto) {
    Optional<User> existedUser = userRepository.findById(userDto.getUserName());
    if (existedUser.isPresent()) {
      return Either.second(AuthError.USER_EXISTS);
    }
    User user = userRepository.save(userDtoToEntity(userDto).setUserToken(UUID.randomUUID()));
    return Either.first(entityToAuthDto(user));
  }

  public List<UserDto> getUsers() {
    return userRepository.findAll().stream()
        .map(UserService::entityToUserDto)
        .collect(Collectors.toList());
  }

  @Transactional
  public Either<UserDto, AuthError> getUserByToken(String userToken) {
    UUID uuid;
    try {
      uuid = UUID.fromString(userToken);
    } catch (IllegalArgumentException exception) {
      return Either.second(AuthError.BAD_CREDENTIAL);
    }
    return userRepository.findByUserToken(uuid).map(UserService::entityToUserDto)
        .map((Function<UserDto, Either<UserDto, AuthError>>) Either::first)
        .orElseGet(() -> Either.second(AuthError.BAD_CREDENTIAL));
  }

  @Transactional
  public Optional<UserDto> getUserByName(String publisherName) {
    return userRepository.findById(publisherName).map(UserService::entityToUserDto);
  }


  private static UserDto entityToUserDto(User user) {
    return new UserDto(user.getUserName());
  }

  private static UserAuthDto entityToAuthDto(User user) {
    return new UserAuthDto(user.getUserName(), user.getUserToken().toString());
  }

  private static User userDtoToEntity(UserDto userDto) {
    return new User().setUserName(userDto.getUserName());
  }

}
