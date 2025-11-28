package Print3D.Inventario.Service;

import Print3D.Inventario.model.Producto;
import Print3D.Inventario.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private  ProductoRepository productoRepository;

    public List <Producto> getProductos(){
        return productoRepository.findAll();
    }

    public Producto addProducto(Producto producto){
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto updateProducto(int id, Producto datosNuevos){
        Optional<Producto> optionalProducto = productoRepository.findById(id);

        if (optionalProducto.isPresent()) {
            Producto productoExistente = optionalProducto.get();

            productoExistente.setNombre(datosNuevos.getNombre());
            productoExistente.setDescripcion(datosNuevos.getDescripcion());
            productoExistente.setPrecio(datosNuevos.getPrecio());
            productoExistente.setStock(datosNuevos.getStock());

            return productoRepository.save(productoExistente);            
        }else{
            return null;
        }
    }

    @Transactional
    public void deleteProducto(int id){
        if(!productoRepository.existsById(id)){
            throw new RuntimeException("producto no encontrado");
        }
        productoRepository.deleteById(id);
    }

    public Optional<Producto> findProductoById(int id){
        return productoRepository.findById(id);
    }

    public List<Producto> getProductosByCategoria(String categoria) {
       return productoRepository.findByCategoria(categoria);
    }

    @Transactional
    public Producto actualizarOferta(int id, boolean oferta, int oferPorcentaje) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("producto no encontrado"));

        if (!oferta) {
            producto.setOferta(false);
            producto.setOferPorcentaje(0);
        } else {
            if (oferPorcentaje <= 0 || oferPorcentaje > 100) {
                throw new IllegalArgumentException("El porcentaje de oferta debe estar entre 1 y 100");
            }
            producto.setOferta(true);
            producto.setOferPorcentaje(oferPorcentaje);
        }

        return productoRepository.save(producto);
    }
}
