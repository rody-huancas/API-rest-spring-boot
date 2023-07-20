
/*package tech.cognity.almacen.controller;

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

import tech.cognity.almacen.entity.Articulo;
import tech.cognity.almacen.service.ArticuloService;

@RestController
@RequestMapping("/v1/articulos")
public class ArticuloControllerSinDTO {
	@Autowired
	private ArticuloService service;
	

	@GetMapping
	public ResponseEntity<List<Articulo>> findAll(
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

		return ResponseEntity.ok(articulos);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Articulo> findById(@PathVariable("id") int id) {
		Articulo articulo = service.findById(id);

		if (articulo == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(articulo);
	}

	@PostMapping()
	public ResponseEntity<Articulo> create(@RequestBody Articulo articulo) {
		Articulo registro = service.save(articulo);

		return ResponseEntity.status(HttpStatus.CREATED).body(registro);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Articulo> update(@PathVariable("id") int id, @RequestBody Articulo articulo) {
		Articulo registro = service.update(articulo);

		if (registro == null)
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok(registro);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Articulo> delete(@PathVariable("id") int id) {
		service.delete(id);
		return ResponseEntity.ok(null);
	}
}
*/