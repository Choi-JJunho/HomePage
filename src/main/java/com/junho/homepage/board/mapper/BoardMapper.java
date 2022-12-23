package com.junho.homepage.board.mapper;

import com.junho.homepage.board.domain.Board;
import com.junho.homepage.board.dto.request.CreateBoard;
import com.junho.homepage.board.dto.response.BoardResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BoardMapper {

    BoardMapper INSTANCE = Mappers.getMapper(BoardMapper.class);

    @Mapping(source = "creator.id", target = "creatorId")
    @Mapping(source = "modifier.id", target = "modifierId")
    BoardResponse toBoardResponse(Board entity);

    @Mapping(target = "id", ignore = true)
    Board toBoard(CreateBoard request);
}
