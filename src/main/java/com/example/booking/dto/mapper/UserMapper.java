package com.example.booking.dto.mapper;


import com.example.booking.dto.user.UserRq;
import com.example.booking.dto.user.UserRs;
import com.example.booking.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserRs userToResponse(User user);
    User requestToUser(UserRq rq);

}
