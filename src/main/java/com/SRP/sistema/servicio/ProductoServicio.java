package com.SRP.sistema.servicio;

import com.SRP.sistema.modelo.Producto;
import com.SRP.sistema.repositorio.ProductoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoServicio {

    @Autowired
    ProductoRepositorio productoRepositorio;

    // listar productos 
    public List<Producto> listaDeProductos(){
        return new ArrayList<>(productoRepositorio.findAll());
    }

    // obtener producto por id
    public Producto detallesProducto (Integer id) {
        return  productoRepositorio.findById(id).get();
    }

    // Guardar o actualizar si ya esta creado el producto
    public boolean guardarOActualizarProducto (Producto producto){
        Producto producto1 = productoRepositorio.save(producto);
        return productoRepositorio.findById(producto1.getId()).isPresent();  // retorna un verdadero  si esta presente el registro que guardo y si no nose pudo guardar
    }

    // eliminar el producto
    public boolean eliminarProducto(Integer id){
        productoRepositorio.deleteById(id);
        return productoRepositorio.findById(id).isEmpty();          //retorna true si lo elimino 
    }


    // actualizar stock de un producto
    public void actualizarStock(Integer id, int tipoMovimiento, int cantidadMovimiento) {
        Producto producto = productoRepositorio.findById(id).get();

        int nuevoStock;
        if (tipoMovimiento == 1) { // Entrada
            nuevoStock = producto.getUnidadesActuales() + cantidadMovimiento;
        } else { // Salida
            nuevoStock = producto.getUnidadesActuales() - cantidadMovimiento;
        }

        producto.setUnidadesActuales(nuevoStock);
        productoRepositorio.save(producto);
    }
}
