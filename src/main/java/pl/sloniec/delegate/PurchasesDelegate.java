package pl.sloniec.delegate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pl.sloniec.controller.PurchasesApiDelegate;
import pl.sloniec.domain.Purchase;
import pl.sloniec.dto.PurchaseDTO;
import pl.sloniec.mapper.PurchaseMapper;
import pl.sloniec.parser.PurchaseCSVParser;
import pl.sloniec.service.PurchaseService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class PurchasesDelegate implements PurchasesApiDelegate {

    private final PurchaseService purchaseService;
    private final PurchaseMapper purchaseMapper;
    private final PurchaseCSVParser purchaseCSVParser;

    @Override
    public ResponseEntity<List<PurchaseDTO>> listPurchases() {
        return ResponseEntity.ok(purchaseMapper.toDTO(purchaseService.findAll()));
    }

    @Override
    public ResponseEntity<PurchaseDTO> showPurchaseById(String PurchaseId) {
        try {
            Purchase Purchase = purchaseService.getById(UUID.fromString(PurchaseId));
            return ResponseEntity.ok(purchaseMapper.toDTO(Purchase));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<PurchaseDTO> createPurchase(PurchaseDTO PurchaseDTO) {
        Purchase Purchase1 = purchaseMapper.fromDTO(PurchaseDTO);
        Purchase Purchase = purchaseService.create(Purchase1);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseMapper.toDTO(Purchase));
    }


    @Override
    public ResponseEntity<Void> importPurchases(MultipartFile file) {
        List<Purchase> Purchases = purchaseCSVParser.parse(file);
        purchaseService.create(Purchases);

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
