package io.github.javaasasecondlanguage.flitter.resource.mappers;

import io.github.javaasasecondlanguage.flitter.dto.AuthError;

public class AuthErrorMapper {
  public static String toRestError(AuthError error) {
    return switch (error) {
      case USER_EXISTS -> "This name is already taken";
      case BAD_CREDENTIAL -> "User not found";
    };
  }
}
