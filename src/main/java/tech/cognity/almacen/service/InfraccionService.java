package tech.cognity.almacen.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import tech.cognity.almacen.entity.Infraccion;

public interface InfraccionService {
	public List<Infraccion> findAll(Pageable page);

	public List<Infraccion> findByDNI(String dni, Pageable page);

	public Infraccion findById(int id);

	public Infraccion save(Infraccion infraccion);

	public Infraccion update(Infraccion infraccion);

	public void delete(int id);
	
	public Infraccion anular (int id);

}
