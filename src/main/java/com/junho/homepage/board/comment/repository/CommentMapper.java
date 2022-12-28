package com.junho.homepage.board.comment.repository;

import com.junho.homepage.board.comment.Comment;
import com.junho.homepage.board.comment.dto.response.CommentResponse;
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
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "creator", target = "creator", qualifiedByName = "parseMember")
    @Mapping(source = "modifier", target = "modifier", qualifiedByName = "parseMember")
    CommentResponse toCommentResponse(Comment entity);

    @Named("parseMember")
    default MemberResponse parseMember(Member entity) {
        return MemberMapper.INSTANCE.toMemberResponse(entity);
    }
}
