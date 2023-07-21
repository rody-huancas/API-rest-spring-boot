package tech.cognity.almacen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import tech.cognity.almacen.entity.Infraccion;
import tech.cognity.almacen.exceptions.GeneralServiceException;
import tech.cognity.almacen.exceptions.NoDataFoundException;
import tech.cognity.almacen.exceptions.ValidateServiceException;
import tech.cognity.almacen.repository.InfraccionRepository;
import tech.cognity.almacen.service.InfraccionService;
import tech.cognity.almacen.validator.InfraccionValidator;

@Slf4j
@Service
public class InfraccionServiceImpl implements InfraccionService {
	@Autowired
	private InfraccionRepository repository;

	@Override
	@Transactional(readOnly = true)
	public List<Infraccion> findAll(Pageable page) {
		try {
			return repository.findAll(page).toList();
		} catch (NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Infraccion> findByDNI(String dni, Pageable page) {
		try {
			return repository.findByDniContaining(dni, page);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Infraccion findById(int id) {
		try {
			Infraccion registro = repository.findById(id)
					.orElseThrow(() -> new NoDataFoundException("No existe el registro con ese ID"));
			return registro;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public Infraccion save(Infraccion infraccion) {
		try {

			InfraccionValidator.save(infraccion);
			if (repository.findByDni(infraccion.getDni()) != null) {
				throw new ValidateServiceException("Ya existe un registro con el dni " + infraccion.getDni());
			}
			infraccion.setActivo(true);
			Infraccion registro = repository.save(infraccion);
			return registro;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public Infraccion update(Infraccion infraccion) {
		try {
			Infraccion registro = repository.findById(infraccion.getId())
					.orElseThrow(() -> new NoDataFoundException("No existe un registro con ese ID"));
			Infraccion registroD = repository.findByDni(infraccion.getDni());
			if (registroD != null && registroD.getId() != infraccion.getId()) {
				throw new ValidateServiceException("Ya existe un registro con el nombre " + infraccion.getDni());
			}
			registro.setDni(infraccion.getDni());
			registro.setFecha(infraccion.getFecha());
			registro.setFalta(infraccion.getFalta());
			registro.setInfraccion(infraccion.getInfraccion());
			registro.setDescripcion(infraccion.getDescripcion());
			repository.save(registro);
			return registro;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public void delete(int id) {
		try {
			Infraccion registro = repository.findById(id)
					.orElseThrow(() -> new NoDataFoundException("No existe un registro con ese ID"));
			repository.delete(registro);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public Infraccion anular(int id) {
		try {
			 Infraccion registro = repository.findById(id).orElseThrow(()->new NoDataFoundException("No existe el registro con ese ID."));
			 registro.setActivo(false);
			 repository.save(registro);
			 return registro;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage());
			throw e;
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new GeneralServiceException(e.getMessage(), e);
		}
	}

}
