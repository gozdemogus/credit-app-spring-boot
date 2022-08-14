package gradproject.demo.mapper;

import gradproject.demo.dto.UserDTO;
import gradproject.demo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserDTO userdto);

    UserDTO toUserDTO(User user);

    List<User> toUserList(List<UserDTO> userDTOList);

    List<UserDTO> toUserDTOList(List<User> userList);
}