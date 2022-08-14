package gradproject.demo.service;

import gradproject.demo.entity.Credit;
import gradproject.demo.entity.CreditResult;
import gradproject.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CreditCalculationServiceTest {

    @Mock
    private CreditService mockCreditService;
    @Mock
    private SmsService mockSmsService;
    @Mock
    private CreditScoreService mockCreditScoreService;

    @InjectMocks
    private CreditCalculationService mockCreditCalculationService;

    //    Kredi skoru 500’ün altında ise kullanıcı reddedilir. (Kredi sonucu: Red)
    @Test
    void shouldReturnRejected_creditScore_lessThan500() {

        User user = customUser();
        user.setSalary(14000);
        Credit credit = mockCreditCalculationService.CreateUserCredit(user, 400L);

        assertEquals(CreditResult.REJECTED.toString(), credit.getCreditResult());
        assertEquals(0L, credit.getCreditAmount());

    }

    //    Kredi skoru 500 puan ile 1000 puan arasında ise ve aylık geliri 5000 TL’nin altında ise
    //    kullanıcının kredi başvurusu onaylanır ve kullanıcıya 10.000 TL limit atanır.
    //    (Kredi Sonucu: Onay)
    @Test
    void shouldReturnConfirmed_creditScore_between500and1000_salary_lowerThan5000() {
        User user = customUser();
        user.setSalary(4500);

        Credit credit = mockCreditCalculationService.CreateUserCredit(user, 750L);

        assertEquals(CreditResult.CONFIRMED.toString(), credit.getCreditResult());
        assertEquals(10000L, credit.getCreditAmount());

    }

    //    Kredi skoru 500 puan ile 1000 puan arasında ise ve aylık geliri 5000 TL’nin üstünde ise
    //    kullanıcının kredi başvurusu onaylanır ve kullanıcıya 20.000 TL limit atanır.
    //    (Kredi Sonucu: Onay)
    @Test
    void shouldReturnConfirmed_creditScore_between500and1000_salary_higherThan5000() {
        User user = customUser();
        user.setSalary(5500);
        Credit credit = mockCreditCalculationService.CreateUserCredit(user, 750L);

        assertEquals(CreditResult.CONFIRMED.toString(), credit.getCreditResult());
        assertEquals(20000L, credit.getCreditAmount());

    }

    //    Kredi skoru 1000 puana eşit veya üzerinde ise kullanıcıya
    //    AYLIK GELİR BİLGİSİ * KREDİ LİMİT ÇARPANI kadar limit atanır. (Kredi Sonucu: Onay)
    @Test
    void shouldReturnConfirmed_creditScore_equalOrHigherThan1000() {
        User userEqual = customUser();
        userEqual.setSalary(5500);

        User userHigher = customUser();
        userHigher.setSalary(5500);
        userHigher.setIdentityNumber(23456654780L);

        Credit creditEqual = mockCreditCalculationService.CreateUserCredit(userEqual, 1000L);
        Credit creditHigher = mockCreditCalculationService.CreateUserCredit(userHigher, 1500L);

        assertEquals(CreditResult.CONFIRMED.toString(), creditEqual.getCreditResult());
        assertEquals(userEqual.getSalary() * 4, creditEqual.getCreditAmount());

        assertEquals(CreditResult.CONFIRMED.toString(), creditHigher.getCreditResult());
        assertEquals(userHigher.getSalary() * 4, creditHigher.getCreditAmount());
    }

    private User customUser() {
        User user = new User();
        user.setId(1L);
        user.setIdentityNumber(26335477280L);
        user.setName("Gözdem");
        user.setSurname("Oğuş");
        //  user.setSalary(10000);
        user.setPhoneNumber("5345648897");
        return user;
    }

}