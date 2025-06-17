package com.cars.microservices.repair_service.controller;

import com.cars.microservices.repair_service.DTO.SparePartDTO;
import com.cars.microservices.repair_service.entity.SparePart;
import com.cars.microservices.repair_service.service.SparePartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spare-parts")
public class SparePartController {

    private final SparePartService sparePartService;

    public SparePartController(SparePartService sparePartService) {
        this.sparePartService = sparePartService;
    }

    @GetMapping
    public List<SparePart> getAllSpareParts() {
        return sparePartService.getAllSpareParts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SparePart> getSparePartById(@PathVariable Integer id) {
        SparePart sparePart = sparePartService.getSparePartById(id);
        return ResponseEntity.ok(sparePart);
    }

    @PostMapping
    public ResponseEntity<SparePart> createSparePart(@Valid @RequestBody SparePartDTO sparePartDTO) {
        SparePart savedPart = sparePartService.createSparePart(sparePartDTO);
        return new ResponseEntity<>(savedPart, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SparePart> updateSparePart(@PathVariable Integer id, @Valid @RequestBody SparePartDTO sparePartDTO) {
        SparePart updatedPart = sparePartService.updateSparePart(id, sparePartDTO);
        return ResponseEntity.ok(updatedPart);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSparePart(@PathVariable Integer id) {
        sparePartService.deleteSparePart(id);
        return ResponseEntity.noContent().build();
    }
}
