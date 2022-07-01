package com.task.innowise.service;

import com.google.protobuf.Empty;
import com.task.innowise.User;
import com.task.innowise.UserServiceGrpc;
import com.task.innowise.entity.UserEntity;
import com.task.innowise.exception.UserNotFoundException;
import com.task.innowise.mapper.UserMapper;
import com.task.innowise.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public void create(User.UserCreateRequest request, StreamObserver<User.UserResponse> responseObserver) {
        UserEntity user = userMapper.requestToUserCreate(request);
        user = userRepository.saveAndFlush(user);
        User.UserResponse response = userMapper.userToResponse(user);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void update(User.UserUpdateRequest request, StreamObserver<User.UserResponse> responseObserver) {
        if(!userRepository.existsById(request.getId())) {
            throw new UserNotFoundException(String.format("%s", request.getId()));
        }
        UserEntity user = userMapper.requestToUserUpdate(request);
        user = userRepository.saveAndFlush(user);
        User.UserResponse response = userMapper.userToResponse(user);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(User.UserIdRequest request, StreamObserver<Empty> responseObserver) {
        long id = request.getId();
        if(!userRepository.existsById(id)) throw new UserNotFoundException(String.format("%s", id));
        userRepository.deleteById(id);
        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getById(User.UserIdRequest request, StreamObserver<User.UserResponse> responseObserver) {
        long id = request.getId();
        UserEntity person = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("%s", id)));
        User.UserResponse response = userMapper.userToResponse(person);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAll(Empty request, StreamObserver<User.UserResponse> responseObserver) {
        userRepository.findAll().stream()
                .map(userMapper::userToResponse)
                .forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }
}
