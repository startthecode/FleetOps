package org.samtar.warehouse.common.embedded;


import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserChild {
    @NotNull(message = "not null")
    @Max(10)
    Integer heightInInch;

    @NotNull(message = "not null")
    @Max(10)
    @Min(1)
    Integer bodyWeight;

    @NotNull(message = "not null")
    Boolean haveDisease;
}
