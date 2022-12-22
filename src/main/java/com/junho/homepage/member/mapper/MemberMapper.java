package com.junho.homepage.member.mapper;

import com.junho.config.security.AuthorityType;
import com.junho.homepage.member.domain.Authority;
import com.junho.homepage.member.domain.Member;
import com.junho.homepage.member.dto.request.SignUpRequest;
import com.junho.homepage.member.dto.response.MemberResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "parseRole")
    MemberResponse toMemberResponse(Member entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(source = "password", target = "password", qualifiedByName = "encryptPassword")
    Member toMemberEntity(SignUpRequest request);

    @Named("encryptPassword")
    default String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Named("parseRole")
    default List<String> parseRole(List<Authority> roles) {
        return roles.stream()
                .map(role -> AuthorityType.of(role.getName()).getDescription())
                .collect(Collectors.toUnmodifiableList());
    }
}
