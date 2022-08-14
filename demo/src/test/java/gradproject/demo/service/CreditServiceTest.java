package gradproject.demo.service;

import gradproject.demo.entity.Credit;
import gradproject.demo.entity.CreditResult;
import gradproject.demo.exception.RecordNotFoundException;
import gradproject.demo.repository.CreditRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CreditServiceTest {

    @Mock
    private CreditRepository mockCreditRepository;

    @InjectMocks
    private CreditService mockCreditService;

    @Test
    void findAllCredits() {
        List<Credit> allCredits = new ArrayList<>();
        allCredits.add(customCredit());
        allCredits.add(customCredit());

        when(mockCreditRepository.findAllCredits()).thenReturn(allCredits);

        List<Credit> allCredits2 = mockCreditService.findAllCredits();

        assertEquals(allCredits, allCredits2);
    }

    @Test
    void findByUserIdentityNumber() {
        Credit credit = customCredit();

        when(mockCreditRepository.findByUserIdentityNumber(credit.getUserIdentityNumber())).thenReturn(credit);

        Credit findByIdentityNumber = mockCreditService.getCreditByIdentityNumber(credit.getUserIdentityNumber());

        assertEquals(credit, findByIdentityNumber);
    }

    @Test
    void not_findByUserIdentityNumber() {

        when(mockCreditRepository.findByUserIdentityNumber(00000000000L)).thenThrow(new NotFoundException("Credit Not Found."));

        assertThrows(NotFoundException.class, () -> mockCreditService.findByUserIdentityNumber(00000000000L));

        verify(mockCreditRepository).findByUserIdentityNumber(00000000000L);
    }

    @Test
    void saveCredit() {
        Credit credit = customCredit();

        when(mockCreditRepository.save(credit)).thenReturn(credit);

        Credit credit1 = mockCreditService.saveCredit(credit);

        assertEquals(credit, credit1);
    }

    @Test
    void not_saveCredit() {

        when(mockCreditRepository.findByUserIdentityNumber(11111111111L))
                .thenThrow(new RecordNotFoundException("Credit Not Found"));

        assertThrows(RecordNotFoundException.class, () -> mockCreditService.getCreditByIdentityNumber(11111111111L));

        verify(mockCreditRepository).findByUserIdentityNumber(11111111111L);

    }

    @Test
    void deleteCredit() {
        Credit credit = customCredit();

        when(mockCreditRepository.findById(credit.getId())).thenReturn(Optional.of(credit));

        mockCreditService.deleteCredit(credit);
        verify(mockCreditRepository).delete(credit);
    }

    private Credit customCredit() {

        Credit credit = new Credit();
        credit.setId(1L);
        credit.setCreditResult(CreditResult.CONFIRMED.toString());
        credit.setApplicationDate(new Date());
        credit.setCreditAmount(10000);
        credit.setUserIdentityNumber(26335477280L);
        return credit;
    }

}