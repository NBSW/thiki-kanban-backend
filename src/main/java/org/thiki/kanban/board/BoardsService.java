package org.thiki.kanban.board;

import org.springframework.stereotype.Service;
import org.thiki.kanban.foundation.exception.BusinessException;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubitao on 05/26/16.
 */
@Service
public class BoardsService {

    @Resource
    private BoardsPersistence boardsPersistence;

    public Board create(String userName, final Board board) {
        boolean isExists = boardsPersistence.unique(board.getId(), board.getName(), userName);
        if (isExists) {
            throw new BusinessException(BoardCodes.BOARD_IS_ALREADY_EXISTS);
        }
        board.setAuthor(userName);
        boardsPersistence.create(board);
        return boardsPersistence.findById(board.getId());
    }

    public Board findById(String id) {
        return boardsPersistence.findById(id);
    }

    public List<Board> loadByUserName(String userName) {
        return boardsPersistence.loadByUserName(userName);
    }

    public Board update(String userName, Board board) {
        Board boardToDelete = boardsPersistence.findById(board.getId());
        if (boardToDelete == null) {
            throw new ResourceNotFoundException(BoardCodes.BOARD_IS_NOT_EXISTS);
        }
        boolean isExists = boardsPersistence.unique(board.getId(), board.getName(), userName);
        if (isExists) {
            throw new BusinessException(BoardCodes.BOARD_IS_ALREADY_EXISTS);
        }
        boardsPersistence.update(board);
        return boardsPersistence.findById(board.getId());
    }

    public int deleteById(String id) {
        Board boardToDelete = boardsPersistence.findById(id);
        if (boardToDelete == null) {
            throw new ResourceNotFoundException(BoardCodes.BOARD_IS_NOT_EXISTS);
        }
        return boardsPersistence.deleteById(id);
    }
}
