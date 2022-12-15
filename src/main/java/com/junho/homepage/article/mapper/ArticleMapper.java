package com.junho.homepage.article.mapper;

import com.junho.homepage.article.Article;
import com.junho.homepage.article.dto.ArticleRequest;
import com.junho.homepage.article.dto.ArticleResponse;
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

    @Mapping(source = "member.id", target = "memberId")
    ArticleResponse toArticleResponse(Article entity);

    @Mapping(target = "member", ignore = true)
    @Mapping(target = "id", ignore = true)
    Article toArticle(ArticleRequest request);
}
