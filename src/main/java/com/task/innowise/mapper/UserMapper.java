package com.task.innowise.mapper;

import com.task.innowise.User;
import com.task.innowise.dto.UserDto;
import com.task.innowise.entity.UserEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    UserDto userToDto(UserEntity user);
    List<UserDto> usersToDto (List<UserEntity> users);

    UserEntity dtoToUser (UserDto userDto);
    List<UserEntity> dtoToUsers (List<UserDto> userDtos);

    UserEntity requestToUserCreate(User.UserCreateRequest request);

    public User.UserResponse userToResponse(UserEntity user);

    public UserEntity requestToUserUpdate(User.UserUpdateRequest request);
}
