package Print3D.Inventario.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(nullable = false)
    private double precio;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @Column(nullable = true)
    private Integer stock;

    @Column(nullable = false, length = 255)
    private String categoria;

    @Column(nullable = false)
    private boolean oferta;

    @Column(nullable = true)
    private int oferPorcentaje;
}

