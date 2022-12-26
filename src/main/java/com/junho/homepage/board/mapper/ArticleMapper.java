package com.junho.homepage.board.mapper;

import com.junho.homepage.board.domain.Article;
import com.junho.homepage.board.dto.response.ArticleResponse;
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
}
