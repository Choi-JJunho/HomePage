package com.junho.homepage.board.repository;

import com.junho.homepage.board.Board;
import com.junho.homepage.board.dto.request.CreateBoard;
import com.junho.homepage.board.dto.response.BoardResponse;
import com.junho.homepage.member.Member;
import com.junho.homepage.member.dto.response.MemberResponse;
import com.junho.homepage.member.repository.MemberMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BoardMapper {

    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    @Mapping(source = "creator", target = "creator", qualifiedByName = "parseMember")
    @Mapping(source = "modifier", target = "modifier", qualifiedByName = "parseMember")
    BoardResponse toBoardResponse(Board entity);

    @Mapping(target = "id", ignore = true)
    Board toBoard(CreateBoard request);

    @Named("parseMember")
    default MemberResponse parseMember(Member entity) {
        return MemberMapper.INSTANCE.toMemberResponse(entity);
    }
}
