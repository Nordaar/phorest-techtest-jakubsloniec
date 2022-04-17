package pl.sloniec.mapper;

import org.mapstruct.Mapper;
import pl.sloniec.domain.Purchase;
import pl.sloniec.dto.PurchaseDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

    Purchase fromDTO(PurchaseDTO PurchaseDTO);

    List<Purchase> fromDTO(List<PurchaseDTO> PurchaseDTOs);

    PurchaseDTO toDTO(Purchase Purchase);

    List<PurchaseDTO> toDTO(List<Purchase> Purchases);

}
