package gradproject.demo.repository;

import gradproject.demo.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditRepository extends JpaRepository<Credit, Long> {

    @Query("SELECT credit FROM Credit credit")
    List<Credit> findAllCredits();

    Credit findAllById(Long id);

    Credit findByUserIdentityNumber(Long userIdentityNumber);


}