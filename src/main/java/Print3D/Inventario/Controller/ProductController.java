package Print3D.Inventario.Controller;

import Print3D.Inventario.Service.ProductService;
import Print3D.Inventario.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<List<Producto>> getProductos() {
        List<Producto> productos = productService.getProductos();
        if (productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @PostMapping("")
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {

        if (producto.getNombre() == null || producto.getNombre().isBlank()
        || producto.getPrecio() <= 0
        || producto.getDescripcion() == null || producto.getDescripcion().isBlank()
        || producto.getCategoria() == null || producto.getCategoria().isBlank()
        ) {
            return ResponseEntity.badRequest().build();
        }

        if (producto.getStock() < 0) {
            return ResponseEntity.badRequest().build();
        }

        Producto saved = productService.addProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    
    @GetMapping("/id/{id}")
    public ResponseEntity<Producto> getById(@PathVariable("id") int id) {
        return productService.findProductoById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updateProducto (@PathVariable ("id") int id, @RequestBody Producto productoAct) {

        Producto actualizado = productService.updateProducto(id, productoAct);
        if (actualizado != null) {
            return ResponseEntity.ok(actualizado);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping ("/id/{id}")
    public ResponseEntity <Void> deleteProductoById(@PathVariable("id") int id){
        productService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity <?> getProdctosByCategoria(@PathVariable("categoria") String categoria) {
        List<Producto> productos = productService.getProductosByCategoria(categoria);
        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @PatchMapping("/{id}/oferta")
    public ResponseEntity<?> actualizarOferta(
            @PathVariable("id") int id,
            @RequestParam("oferta") boolean oferta,
            @RequestParam("oferPorcentaje") int oferPorcentaje) {

        try {
            Producto actualizado = productService.actualizarOferta(id, oferta, oferPorcentaje);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    
}