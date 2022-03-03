package simple.simplenote.repository;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import simple.simplenote.domain.Member;
import simple.simplenote.domain.contents.Card;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CardRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(Card card) {
        em.persist(card);
    }

    public List<Card> findAll() {
        return em.createQuery("select c from Card as c", Card.class)
                .getResultList();
    }

    public Card findById(Long id) {
        Card card = em.find(Card.class, id);
        return card;
    }

    public void deleteCard(Card findCard) {
        em.remove(findCard);
    }

}
