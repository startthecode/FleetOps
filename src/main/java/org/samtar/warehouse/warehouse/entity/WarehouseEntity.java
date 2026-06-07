package org.samtar.warehouse.warehouse.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.samtar.warehouse.location.entity.CityEntity;



@Data
@Entity
@Table(name = "warehouse")
public class WarehouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "warehouse_id_generator")
    @SequenceGenerator(name = "warehouse_id_generator",sequenceName = "warehouse_id_generator",allocationSize = 10)
    private long warehouseId;

    @NotBlank(message = "Warehouse name can not be blank")
    @Size(max = 60, min = 3, message = "Warehouse name must be between 5 to 60 character")
    @Column(name = "warehouse_name", nullable = false, unique = true, length = 60)
    String warehouseName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false,name = "city_id")
    CityEntity city;

}
