package com.junho.homepage.article.mapper;

import com.junho.homepage.article.Article;
import com.junho.homepage.article.dto.ArticleRequest;
import com.junho.homepage.article.dto.ArticleResponse;
import com.junho.homepage.member.Member;
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

    @Mapping(target = "hits", ignore = true)
    @Mapping(target = "id", ignore = true)
    Article toArticle(ArticleRequest request, Member member);
}
