package simple.simplenote.service;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simple.simplenote.domain.contents.Card;
import simple.simplenote.repository.CardRepository;


import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;


    @Transactional(readOnly = false)
    public String add(Card card){
        validateDuplicatedTitle(card);
        cardRepository.save(card);
        return card.getTitle();
    }

    public Card findById(Long id){
        return cardRepository.findById(id);
    }

    @Transactional(readOnly = false)
    public void updateCard(Card card){
        cardRepository.save(card);
    }

    public List<Card> findAll(){
        return cardRepository.findAll();
    }


    private void validateDuplicatedTitle(Card card) {
        Card findCard = cardRepository.findById(card.getId());
        if (findCard != null){
            throw new IllegalStateException("duplicated id!");
        }

    }

    @Transactional(readOnly = false)
    public void removeCard(Card findCard) {
        cardRepository.deleteCard(findCard);
    }
}
