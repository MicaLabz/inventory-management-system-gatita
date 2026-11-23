package com.inventory.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductUpdateRequest(@NotBlank(message = "El nombre es obligatorio")
                                   @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
                                   String name,

                                   @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
                                   String description,

                                   @NotNull(message = "El precio es obligatorio")
                                   @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero")
                                   @Digits(integer = 10, fraction = 2,
                                           message = "El precio admite hasta 10 enteros y 2 decimales")
                                   BigDecimal price,

                                   @NotNull(message = "El stock es obligatorio")
                                   @Min(value = 0, message = "El stock no puede ser negativo")
                                   Integer stock,

                                   @NotNull(message = "El stock mínimo es obligatorio")
                                   @Min(value = 0, message = "El stock mínimo no puede ser negativo")
                                   Integer minStock)
{ }
