package pl.sloniec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.sloniec.domain.Purchase;
import pl.sloniec.repository.PurchaseRepository;
import pl.sloniec.service.parser.PurchaseCSVParser;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PurchaseCSVParser purchaseCSVParser;

    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }

    public Purchase getById(UUID id) {
        return purchaseRepository.getById(id);
    }

    public Purchase create(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }

    public void create(List<Purchase> purchases) {
        purchaseRepository.saveAll(purchases);
    }

    public void importFromCSV(MultipartFile file) {
        List<Purchase> appointments = purchaseCSVParser.parse(file);
        purchaseRepository.saveAll(appointments);
    }
}
