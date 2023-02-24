package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {
    @Autowired
    EditorialRepositorio editorialrepositorio;
    
    @Transactional
    public void crearEditorial(String nombre) throws MiException{
        
        validar(nombre, nombre);
        
        Editorial editorial = new Editorial();
        
        editorial.setNombre(nombre);
        
        editorialrepositorio.save(editorial);
    }
    
    public List<Editorial> listarEditoriales(){
        List<Editorial> editoriales = new ArrayList();
        
        editoriales = editorialrepositorio.findAll();
        
        return editoriales;
    }
    
    public void modificarEditorial(String id, String nombre) throws MiException{
        
        validar(nombre, id);
        
        Optional<Editorial> respuesta = editorialrepositorio.findById(id);
        
        if(respuesta.isPresent()){
            Editorial editorial = respuesta.get();
            
            editorial.setNombre(nombre);
            
            editorialrepositorio.save(editorial);
        }
    }
    
    private void validar(String nombre, String id) throws MiException{
        
        if(nombre.isEmpty() || nombre == null){
            throw new MiException("el nombre no puede ser nulo o estar vacío");
        }
        
        if(id.isEmpty() || id == null){
            throw new MiException("el id no puede ser nulo o estar vacío");
        }
    }
}
