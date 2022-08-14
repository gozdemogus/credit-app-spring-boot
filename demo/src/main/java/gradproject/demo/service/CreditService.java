package gradproject.demo.service;

import gradproject.demo.entity.Credit;
import gradproject.demo.repository.CreditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditService {

    private final CreditRepository creditRepository;

    public List<Credit> findAllCredits() {
        return creditRepository.findAllCredits();
    }

    public Credit findById(Long id) {
        return creditRepository.findAllById(id);
    }

    public Credit findByUserIdentityNumber(Long userIdentityNumber) {
        return creditRepository.findByUserIdentityNumber(userIdentityNumber);
    }

    public Credit saveCredit(Credit credit) {
        log.info("Credit assigned to: " + credit.getUserIdentityNumber());
        return creditRepository.save(credit);

    }


    public void deleteCredit(Credit credit) {
        creditRepository.delete(credit);
        log.info("Credit with id: " + credit.getId() + " has been deleted.");
    }

    public Credit getCreditByIdentityNumber(Long identityNumber) {
        return creditRepository.findByUserIdentityNumber(identityNumber);
    }
}