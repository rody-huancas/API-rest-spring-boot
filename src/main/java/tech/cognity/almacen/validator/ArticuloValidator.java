package tech.cognity.almacen.validator;

import tech.cognity.almacen.entity.Articulo;
import tech.cognity.almacen.exceptions.ValidateServiceException;

public class ArticuloValidator {

	public static void save(Articulo articulo) {
		if (articulo.getNombre() == null || articulo.getNombre().isEmpty())
			throw new ValidateServiceException("El nombre es requerido");
			//throw new RuntimeException("El nombre es requerido");

		if (articulo.getNombre().length() > 100)
			throw new ValidateServiceException("El nombre es muy largo");

		if (articulo.getPrecio() == null)
			throw new ValidateServiceException("El precio es requerido");

		if (articulo.getPrecio() < 0)
			throw new ValidateServiceException("El precio es incorrecto");
	}

}
