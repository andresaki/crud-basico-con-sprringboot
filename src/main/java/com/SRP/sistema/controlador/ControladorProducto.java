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

import java.util.List;

@Controller
public class ControladorProducto {

    // instanciamos para utilizar los metodos
    @Autowired
    ProductoServicio productoServicio;         



    //rutas y procesos

    //Home 
    @GetMapping({"/home" , "/"})
    public String home (Model model){
        return "index";
    }


    //Inventario
    @GetMapping("/inventario")
    public String tablaProductos (Model model , @ModelAttribute("mensaje") String mensaje){      
        List<Producto> productoList = productoServicio.listaDeProductos();                          //utilizamos de metodo del servidor y lo introducimos en un List<>
        model.addAttribute("productoList",productoList);
        model.addAttribute("mensaje",mensaje);                                                      //modelo para toast
        return "inventario";
    }


    //Nuevo producto 
    @GetMapping("/NuevoProducto")
    public String nuevoProducto (Model model , @ModelAttribute("mensaje") String mensaje){
        Producto product = new Producto();               //instanciamos un objeto Producto
        model.addAttribute("product",product);           //Enviamos como modelo el objeto para que lo llenen en el front
        model.addAttribute("mensaje",mensaje);           ////modelo para toast
        return "nuevoProducto";                          //llamamos el template
    }

    @PostMapping ("/GuardarProducto")
    public String guardarProducto (Producto product , RedirectAttributes redirectAttributes){       //Recibe el objeto producto ya llenado con los datos

        if (productoServicio.guardarOActualizarProducto(product)){                                  //ultilizamos el metodo del servicio para guardar el objeto que nos llego
            redirectAttributes.addFlashAttribute("mensaje","saveOK");                               //enviamos info al modelo de toast
            return "redirect:/inventario";                                                          //si retorna true, es por que se guardo y esta presente y redirecionaremos a /inventario
        }
        redirectAttributes.addFlashAttribute("mensaje","saveError");                                //enviamos info al modelo de toast
        return "redirect:/NuevoProducto";                                                           //si no esta presente, retorna false y redirecciona al template para rellenar el formulario nuevamente
    }
    


    //Ver datos de un producto
    @GetMapping ("/VerProducto/{id}")
    public String verProducto (Model model, @PathVariable Integer id ){      //preparamos para recibir un pathVariable que seria el id del producto para ver los detalles
        Producto product = productoServicio.detallesProducto(id);           //utilizamos el metodo del servicio para obtener el objeto producto mandando el id de la url para buscarlo por el id
        model.addAttribute("product", product);                             //le mandamos al front el objeto que se lleno con el metodo anterior para que lo muestre en el front
        return "detallesProducto";                                          //llamamos al template
    }


    //Editar producto
    @GetMapping ("/EditarProducto/{id}")
    public String editarProducto (Model model, @PathVariable Integer id , @ModelAttribute("mensaje") String mensaje){   //mismo proceso que ver producto , pero en este en el front si da acceso para editar los datos
        Producto product = productoServicio.detallesProducto(id);
        model.addAttribute("product", product);
        model.addAttribute("mensaje", mensaje);                                         //modelo para toast
        return "EditarProducto";
    }

    @PostMapping ("/ActualizarProducto")
    public String actualizarProducto (@ModelAttribute("product") Producto producto , RedirectAttributes redirectAttributes){    //Recibe el objeto producto ya editado 
        if (productoServicio.guardarOActualizarProducto(producto)){                                                             //mismo proceso que el post de crear pero si o si se tiene que manda el id, para que se actualize los datos de ese objeto y no cree otro porducto
            redirectAttributes.addFlashAttribute("mensaje", "updateOK");                                                        //enviamos info al modelo de toast
            return "redirect:/inventario";
        }
        redirectAttributes.addFlashAttribute("mensaje","updateError");                                                          //enviamos info al modelo de toast
        return "redirect:/EditarProducto";
    }



    //Eliminar producto  
    @GetMapping("/EliminarCliente/{id}")
    public String eliminarProducto(@PathVariable Integer id,RedirectAttributes redirectAttributes){    

        if (productoServicio.eliminarProducto(id)){                             //utilizando el metodo eliminar del servicio se elimina mediante el id , que se recibe desde la url
            redirectAttributes.addFlashAttribute("mensaje","deleteOK");         //enviamos info al modelo de toast
            return "redirect:/inventario";                                      //el metodo, hace la verificacion si no esta presente es por no se guardo y retorna true
        }
        redirectAttributes.addFlashAttribute("mensaje","deleteError");          //enviamos info al modelo de toast
        return "redirect:/inventario";                                          //y retorna false , por que esta presente y no se pudo eliminar el producto
    }




}
