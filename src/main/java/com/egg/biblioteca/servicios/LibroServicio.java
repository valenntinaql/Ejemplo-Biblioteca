package com.egg.biblioteca.servicios;

import com.egg.biblioteca.entidades.Autor;
import com.egg.biblioteca.entidades.Editorial;
import com.egg.biblioteca.entidades.Libro;
import com.egg.biblioteca.excepciones.MiException;
import com.egg.biblioteca.repositorios.AutorRepositorio;
import com.egg.biblioteca.repositorios.EditorialRepositorio;
import com.egg.biblioteca.repositorios.LibroRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service

public class LibroServicio {
    @Autowired
    private LibroRepositorio librorepositorio;
    @Autowired
    private AutorRepositorio autorrepositorio;
    @Autowired
    private EditorialRepositorio editorialrepositorio;
    
    @Transactional
    public void crearLibro(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
        
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        Autor autor = autorrepositorio.findById(idAutor).get();
        Libro libro =new Libro();
        Editorial editorial = editorialrepositorio.findById(idEditorial).get();
        
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setEjemplares(ejemplares);
        libro.setAlta(new Date());
        
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        
        librorepositorio.save(libro);
    }
    
    public List<Libro> listarLibros(){
        
        List<Libro> libros = new ArrayList();
        
        libros =librorepositorio.findAll();
        
        return libros;
    }
    
    public void modificarLibro(Long isbn, String titulo, String idAutor, String idEditorial, Integer ejemplares) throws MiException{
        
        validar(isbn, titulo, ejemplares, idAutor, idEditorial);
        
        Optional<Libro> respuesta = librorepositorio.findById(isbn);
        Optional<Autor> respuestaAutor = autorrepositorio.findById(idAutor);
        Optional<Editorial> respuestaEditorial = editorialrepositorio.findById(idEditorial);
        
        Autor autor = new Autor();
        Editorial editorial = new Editorial();
        
        if (respuestaEditorial.isPresent()){
            editorial = respuestaEditorial.get();
        }
        
        if (respuestaAutor.isPresent()){
            autor = respuestaAutor.get();
        }
        
        if (respuesta.isPresent()){
            Libro libro = respuesta.get();
            libro.setTitulo(titulo);
            libro.setAutor(autor);
            libro.setEditorial(editorial);
            libro.setEjemplares(ejemplares);
            librorepositorio.save(libro);
        }
    }
    
    private void validar(Long isbn, String titulo, Integer ejemplares, String idAutor, String idEditorial) throws MiException{
       
        if(isbn == null){
            throw new MiException("el isbn no puede ser nulo");
        }
        
        if(titulo.isEmpty() || titulo == null){
            throw new MiException("el titulo no puede ser nulo o estar vacío");
        }
        
        if(ejemplares == null){
            throw new MiException("ejemplares no puede ser nulo");
        }
        
        if(idAutor.isEmpty() || idAutor == null){
            throw new MiException("el autor no puede ser nulo o estar vacío");
        }
        
        if(idEditorial.isEmpty() || idEditorial == null){
            throw new MiException("el editorial no puede ser nulo o estar vacío");
        }
    }
}
