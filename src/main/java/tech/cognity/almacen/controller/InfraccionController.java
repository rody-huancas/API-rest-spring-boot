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

import tech.cognity.almacen.converter.InfraccionConverter;
import tech.cognity.almacen.dto.InfraccionDTO;
import tech.cognity.almacen.entity.Infraccion;
import tech.cognity.almacen.service.InfraccionService;
import tech.cognity.almacen.utils.WrapperResponse;

@RestController
@RequestMapping("/v1/infraccion")
public class InfraccionController {
	@Autowired
	private InfraccionService service;

	@Autowired
	InfraccionConverter converter;

	@GetMapping
	public ResponseEntity<List<InfraccionDTO>> findAll(
			@RequestParam(value = "dni", required = false, defaultValue = "") String dni,
			@RequestParam(value = "offset", required = false, defaultValue = "0") int pageNumber,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int pageSize) {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Infraccion> infraccion;

		if (dni == null)
			infraccion = service.findAll(page);
		else
			infraccion = service.findByDNI(dni, page);

		if (infraccion.isEmpty())
			return ResponseEntity.noContent().build();

		List<InfraccionDTO> infraccionDTO = converter.fromEntity(infraccion);
		return new WrapperResponse(true, "success", infraccionDTO).createResponse(HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<WrapperResponse<InfraccionDTO>> findById(@PathVariable("id") int id) {
		Infraccion infraccion = service.findById(id);
		InfraccionDTO infraccionDTO = converter.fromEntity(infraccion);
		return new WrapperResponse<InfraccionDTO>(true, "success", infraccionDTO).createResponse(HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<InfraccionDTO> create(@RequestBody InfraccionDTO infraccionDTO) {
		Infraccion registro = service.save(converter.fromDTO(infraccionDTO));

		InfraccionDTO registroDTO = converter.fromEntity(registro);
		return new WrapperResponse(true, "success", registroDTO).createResponse(HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<InfraccionDTO> update(@PathVariable("id") int id, @RequestBody InfraccionDTO infraccionDTO) {
		Infraccion registro = service.update(converter.fromDTO(infraccionDTO));

		InfraccionDTO registroDTO = converter.fromEntity(registro);
		return new WrapperResponse(true, "success", registroDTO).createResponse(HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<InfraccionDTO> delete(@PathVariable("id") int id) {
		service.delete(id);
		return new WrapperResponse(true, "success", null).createResponse(HttpStatus.OK);
	}

	@PutMapping(value = "/anular/{id}")
	public ResponseEntity<InfraccionDTO> anular(@PathVariable("id") int id) {
		Infraccion registro = service.anular(id);
		InfraccionDTO registroDTO = converter.fromEntity(registro);
		return new WrapperResponse(true, "success", registroDTO).createResponse(HttpStatus.OK);
	}
}
