package io.github.javaasasecondlanguage.flitter.service;

import io.github.javaasasecondlanguage.flitter.dao.FlitRepository;
import io.github.javaasasecondlanguage.flitter.dao.UserRepository;
import io.github.javaasasecondlanguage.flitter.dto.FlitDto;
import io.github.javaasasecondlanguage.flitter.dto.UserDto;
import io.github.javaasasecondlanguage.flitter.entity.Flit;
import io.github.javaasasecondlanguage.flitter.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlitService {
  private final FlitRepository flitRepository;
  private final UserRepository userRepository;

  public FlitService(FlitRepository flitRepository, UserRepository userRepository) {
    this.flitRepository = flitRepository;
    this.userRepository = userRepository;
  }

  @Transactional
  public FlitDto post(UserDto user, String content) {
    Flit flit = new Flit().setContent(content).setAuthor(new User().setUserName(user.getUserName())).setCreationTime(LocalDateTime.now());
    return entityToDto(flitRepository.save(flit));
  }

  public List<FlitDto> discover() {
    return flitRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "creationTime")))
        .stream()
        .map(FlitService::entityToDto)
        .collect(Collectors.toList());
  }

  public Optional<List<FlitDto>> list(String userName) {
    return userRepository.findByUserName(userName)
        .map(flitRepository::findAllByAuthor)
        .map((flits) -> flits.stream().map(FlitService::entityToDto).collect(Collectors.toList()));
  }

  @Transactional
  public void clear() {
    flitRepository.deleteAll();
  }

  public List<FlitDto> getFlits(Collection<UserDto> publishers) {
    return flitRepository.findAllByAuthor_UserNameInOrderByCreationTime(publishers.stream().map(UserDto::getUserName).collect(Collectors.toList()))
        .stream()
        .map(FlitService::entityToDto)
        .collect(Collectors.toList());
  }

  private static FlitDto entityToDto(Flit flit) {
    return new FlitDto(flit.getId().toString(), flit.getAuthor().getUserName(), flit.getContent());
  }
}
