package com.junho.homepage.member.mapper;

import com.junho.homepage.member.Member;
import com.junho.homepage.member.dto.MemberResponse;
import com.junho.homepage.member.dto.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

    MemberResponse toMemberResponse(Member entity);

    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "refreshToken", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(source = "password", target = "password", qualifiedByName = "encryptPassword")
    Member toMemberEntity(SignUpRequest request);

    @Named("encryptPassword")
    default String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
