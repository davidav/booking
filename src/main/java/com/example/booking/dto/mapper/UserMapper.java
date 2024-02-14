package com.example.booking.dto.mapper;


import com.example.booking.dto.user.UserRequest;
import com.example.booking.dto.user.UserResponse;
import com.example.booking.entity.User;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(UserMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserResponse userToResponse(User user);

    User requestToUser(UserRequest request);

    User requestToUser(Long id, UserRequest request);

}
