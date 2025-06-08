package com.coding.university_management.University.Management.mapper;

import com.coding.university_management.University.Management.dto.request.UserCreationRequest;
import com.coding.university_management.University.Management.dto.request.UserUpdateRequest;
import com.coding.university_management.University.Management.dto.response.UserResponse;
import com.coding.university_management.University.Management.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUSer(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);

}
