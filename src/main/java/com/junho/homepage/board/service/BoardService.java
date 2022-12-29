package com.junho.homepage.board.service;

import com.junho.homepage.board.Board;
import com.junho.homepage.board.dto.request.CreateBoard;
import com.junho.homepage.board.dto.response.BoardResponse;
import com.junho.homepage.board.repository.BoardMapper;
import com.junho.homepage.board.repository.BoardRepository;
import com.junho.support.error.ErrorCode;
import com.junho.support.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardResponse> getBoards() {
        
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(BoardMapper.INSTANCE::toBoardResponse)
                .collect(Collectors.toList());
    }

    public Boolean createBoard(CreateBoard request) {

        if (boardRepository.findByTitle(request.getTitle()).isPresent()) {
            throw new ApiException(ErrorCode.BOARD_ALREADY_EXIST);
        }

        Board board = BoardMapper.INSTANCE.toBoard(request);
        boardRepository.save(board);

        return true;
    }

    public BoardResponse updateBoard(Long id, CreateBoard request) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.BOARD_NOT_EXIST));

        board.update(request);
        return BoardMapper.INSTANCE.toBoardResponse(board);
    }

    public Boolean deleteBoard(Long id) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.ARTICLE_NOT_EXIST));

        board.setEnabled(false);
        return true;
    }
}
