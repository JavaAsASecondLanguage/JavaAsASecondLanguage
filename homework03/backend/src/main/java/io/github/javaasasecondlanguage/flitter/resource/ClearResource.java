package io.github.javaasasecondlanguage.flitter.resource;

import io.github.javaasasecondlanguage.flitter.service.ClearService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClearResource {
  private final ClearService clearService;

  public ClearResource(ClearService clearService) {
    this.clearService = clearService;
  }


  @DeleteMapping("/clear")
  void clear() {
    clearService.clear();
  }
}
