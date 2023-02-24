package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {
    @Autowired
    AutorRepositorio autorrepositorio;
    
    @Transactional
    public void crearAutor(String nombre) throws MiException{
        
        validar(nombre, nombre);
        
        Autor autor = new Autor();
        
        autor.setNombre(nombre);
        
        autorrepositorio.save(autor);
    }
    
    public List<Autor> listarAutores(){
        List<Autor> autores = new ArrayList();
        
        autores =autorrepositorio.findAll();
        
        return autores;
    }
    
    public void modificarAutor (String nombre, String id) throws MiException{
        
        validar(nombre, id);
        
        Optional<Autor> respuesta = autorrepositorio.findById(id);
        
        if (respuesta.isPresent()){
            Autor autor = respuesta.get();
            
            autor.setNombre(nombre);
            
            autorrepositorio.save(autor);
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
