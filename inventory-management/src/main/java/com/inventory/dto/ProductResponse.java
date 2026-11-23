package com.inventory.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(Long id,
                             String name,
                             String sku,
                              BigDecimal price,
                             Integer stock,
                             Integer minStock,
                             LocalDateTime createdAt,
                             LocalDateTime updatedAt)
{}
