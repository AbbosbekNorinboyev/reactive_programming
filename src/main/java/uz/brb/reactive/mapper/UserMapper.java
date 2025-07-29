package uz.brb.reactive.mapper;

import org.springframework.stereotype.Component;
import uz.brb.reactive.dto.UserDto;
import uz.brb.reactive.entity.AuthUser;

@Component
public class UserMapper {
    public AuthUser toEntity(UserDto userDto) {
        return AuthUser.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .build();
    }
}
