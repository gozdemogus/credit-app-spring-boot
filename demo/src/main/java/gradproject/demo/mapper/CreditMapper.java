package gradproject.demo.mapper;

import gradproject.demo.dto.CreditDTO;
import gradproject.demo.entity.Credit;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreditMapper {
    CreditMapper INSTANCE = Mappers.getMapper(CreditMapper.class);
    Credit toCredit(CreditDTO creditDto);

    CreditDTO toCreditDTO(Credit credit);

    List<Credit> toCreditList(List<CreditDTO> creditDTOList);

    List<CreditDTO> toCreditDTOList(List<Credit> creditList);

}