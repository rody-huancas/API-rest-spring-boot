package tech.cognity.almacen.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.cognity.almacen.converter.ArticuloConverter;
import tech.cognity.almacen.dto.ArticuloDTO;
import tech.cognity.almacen.entity.Articulo;
import tech.cognity.almacen.service.ArticuloService;

@RestController
@RequestMapping("/v1/articulos")
public class ArticuloController {
	@Autowired
	private ArticuloService service;
	
	@Autowired
	private ArticuloConverter converter;
	

	@GetMapping
	public ResponseEntity<List<ArticuloDTO>> findAll(
			@RequestParam(value = "nombre", required = false, defaultValue = "") String nombre,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize) {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Articulo> articulos;

		if (nombre == null)
			articulos = service.findAll(page);
		else
			articulos = service.findByNombre(nombre, page);

		if (articulos.isEmpty())
			return ResponseEntity.noContent().build();

		List<ArticuloDTO> articulosDTO = converter.fromEntity(articulos);
		return ResponseEntity.ok(articulosDTO);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ArticuloDTO> findById(@PathVariable("id") int id) {
		Articulo articulo = service.findById(id);

		if (articulo == null)
			return ResponseEntity.notFound().build();
		
		ArticuloDTO articulosDTO = converter.fromEntity(articulo);
		return ResponseEntity.ok(articulosDTO);
	}

	@PostMapping()
	public ResponseEntity<ArticuloDTO> create(@RequestBody ArticuloDTO articuloDTO) {
		Articulo registro = service.save(converter.fromDTO(articuloDTO));
		
		ArticuloDTO registroDTO = converter.fromEntity(registro);
		return ResponseEntity.status(HttpStatus.CREATED).body(registroDTO);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ArticuloDTO> update(@PathVariable("id") int id, @RequestBody ArticuloDTO articuloDTO) {
		Articulo registro = service.update(converter.fromDTO(articuloDTO));
		
		if (registro == null)
			return ResponseEntity.notFound().build();
		
		ArticuloDTO registroDTO = converter.fromEntity(registro);
		return ResponseEntity.ok(registroDTO);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ArticuloDTO> delete(@PathVariable("id") int id) {
		service.delete(id);
		return ResponseEntity.ok(null);
	}
}
