package com.SRP.sistema.controlador;

import com.SRP.sistema.modelo.Producto;
import com.SRP.sistema.servicio.ProductoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ControladorProducto {

    @Autowired
    ProductoServicio productoServicio;



    //rutas y procesos
    @GetMapping("/home")
    public String home (Model model){
        return "index";
    }


    @GetMapping("/inventario")
    public String tablaProductos (Model model){
        List<Producto> productoList = productoServicio.listaDeProductos();
        model.addAttribute("productoList",productoList);
        return "inventario";
    }

    @GetMapping("/NuevoProducto")
    public String nuevoProducto (Model model){
        Producto product = new Producto();
        model.addAttribute("product",product);
        product.getFechaCreacion();
        return "nuevoProducto";
    }

    @PostMapping ("/GuardarProducto")
    public String guardarProducto (Producto product){

        System.out.println(product.toString());


        if (productoServicio.guardarOActualizarProducto(product)){
            return "redirect:/inventario";
        }
        return "redirect:/NuevoProducto";
    }

    @GetMapping ("/VerProducto/{id}")
    public String verProducto (Model model, @PathVariable Integer id){
        Producto product = productoServicio.detallesProducto(id);
        model.addAttribute("product", product);
        return "detallesProducto";
    }

    @GetMapping ("/EditarProducto/{id}")
    public String editarProducto (Model model, @PathVariable Integer id){
        Producto product = productoServicio.detallesProducto(id);
        model.addAttribute("product", product);
        return "EditarProducto";
    }


    @PostMapping ("/ActualizarProducto")
    public String actualizarProducto (@ModelAttribute("product") Producto producto , RedirectAttributes redirectAttributes){
        if (productoServicio.guardarOActualizarProducto(producto)){
            return "redirect:/inventario";
        }
        return "redirect:/EditarProducto";
    }

    @GetMapping("/EliminarCliente/{id}")
    public String eliminarProducto(@PathVariable Integer id,RedirectAttributes redirectAttributes){

        if (productoServicio.eliminarProducto(id)){
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");
            return "redirect:/inventario";
        }
        redirectAttributes.addFlashAttribute("mensaje","deleteError");
        return "redirect:/inventario";
    }




}
