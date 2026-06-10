package com.chuvannam.applogin.service.impl;

import com.chuvannam.applogin.entity.Sell;
import com.chuvannam.applogin.repository.SellRepository;
import com.chuvannam.applogin.service.SellService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellServiceImpl implements SellService {

    private final SellRepository sellRepository;

    @Override
    public List<Sell> findAll() {
        return sellRepository.findAll();
    }

    @Override
    public Sell findById(UUID id) {
        return sellRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Sell not found: " + id));
    }

    @Override
    public Sell findByProductId(UUID productId) {
        return sellRepository.findByProductId(productId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Sell not found for product: " + productId
                        ));
    }

    @Override
    public Sell save(Sell sell) {
        return sellRepository.save(sell);
    }

    @Override
    public Sell update(UUID id, Sell sell) {

        Sell existing = findById(id);

        existing.setProduct(
                sell.getProduct()
        );

        existing.setPrice(
                sell.getPrice()
        );

        existing.setQuantity(
                sell.getQuantity()
        );

        return sellRepository.save(existing);
    }

    @Override
    public void delete(UUID id) {
        sellRepository.deleteById(id);
    }
}