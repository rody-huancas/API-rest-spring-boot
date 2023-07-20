package tech.cognity.almacen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tech.cognity.almacen.entity.Articulo;
import tech.cognity.almacen.repository.ArticuloRepository;
import tech.cognity.almacen.service.ArticuloService;
import tech.cognity.almacen.validator.ArticuloValidator;

@Service
public class ArticuloServiceImpl implements ArticuloService {
	@Autowired
	private ArticuloRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<Articulo> findAll(Pageable page) {
		try {
			return repository.findAll(page).toList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Articulo> findByNombre(String nombre, Pageable page) {
		try {
			return repository.findByNombreContaining(nombre, page);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Articulo findById(int id) {
		try {
			Articulo registro = repository.findById(id).orElseThrow();
			return registro;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Articulo save(Articulo articulo) {
		try {
			ArticuloValidator.save(articulo);
			articulo.setActivo(true);
			Articulo registro = repository.save(articulo);
			return registro;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Articulo update(Articulo articulo) {
		try {
			Articulo registro = repository.findById(articulo.getId()).orElseThrow();
			registro.setNombre(articulo.getNombre());
			registro.setPrecio(articulo.getPrecio());
			repository.save(registro);
			return registro;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void delete(int id) {
		try {
			Articulo registro = repository.findById(id).orElseThrow();
			repository.delete(registro);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}
