package com.junho.homepage.board.mapper;

import com.junho.homepage.board.domain.Article;
import com.junho.homepage.board.dto.ArticleRequest;
import com.junho.homepage.board.dto.ArticleResponse;
import com.junho.homepage.member.domain.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ArticleMapper {

    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    @Mapping(source = "creator.id", target = "creatorId")
    @Mapping(source = "modifier.id", target = "modifierId")
    ArticleResponse toArticleResponse(Article entity);

    @Mapping(target = "board", ignore = true)
    @Mapping(target = "hits", ignore = true)
    @Mapping(target = "id", ignore = true)
    Article toArticle(ArticleRequest request, Member member);
}
