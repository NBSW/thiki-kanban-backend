package org.thiki.kanban.assignment;

import org.springframework.stereotype.Service;
import org.thiki.kanban.card.Card;
import org.thiki.kanban.card.CardsCodes;
import org.thiki.kanban.card.CardsPersistence;
import org.thiki.kanban.foundation.exception.InvalidParamsException;
import org.thiki.kanban.foundation.exception.ResourceNotFoundException;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xubitao on 6/16/16.
 */
@Service
public class AssignmentService {

    @Resource
    private AssignmentPersistence assignmentPersistence;
    @Resource
    private CardsPersistence cardsPersistence;

    public Assignment create(final Assignment assignment, String cardId, String authorUserId) {
        assignment.setCardId(cardId);
        assignment.setAuthor(authorUserId);
        assignmentPersistence.create(assignment);
        return assignmentPersistence.findById(assignment.getId());
    }

    public Assignment findById(String id) {
        return assignmentPersistence.findById(id);
    }

    public List<Assignment> findByCardId(String cardId) {
        Card card = cardsPersistence.findById(cardId);
        if (card == null) {
            throw new InvalidParamsException(CardsCodes.CARD_IS_NOT_EXISTS.code(), CardsCodes.CARD_IS_NOT_EXISTS.message());
        }
        return assignmentPersistence.findByCardId(cardId);
    }

    public int deleteById(String id) {
        Assignment assignmentToDelete = assignmentPersistence.findById(id);
        if (assignmentToDelete == null) {
            throw new ResourceNotFoundException("assignment[" + id + "] is not found.");
        }
        return assignmentPersistence.deleteById(id);
    }
}
