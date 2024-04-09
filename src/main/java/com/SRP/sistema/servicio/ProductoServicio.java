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

    public List<Producto> listaDeProductos(){
        return new ArrayList<>(productoRepositorio.findAll());
    }

    public Producto detallesProducto (Integer id) {
        return  productoRepositorio.findById(id).get();
    }

    public boolean guardarOActualizarProducto (Producto producto){
        Producto producto1 = productoRepositorio.save(producto);
        return productoRepositorio.findById(producto1.getId()).isPresent();  // retorna un verdadero  si esta presente el registro que guardo y si no nose pudo guardar
    }

    public boolean eliminarProducto(Integer id){
        productoRepositorio.deleteById(id);
        return productoRepositorio.findById(id).isEmpty();
    }


}
