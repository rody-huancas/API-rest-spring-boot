package tech.cognity.almacen.validator;

import tech.cognity.almacen.entity.Infraccion;
import tech.cognity.almacen.exceptions.ValidateServiceException;

public class InfraccionValidator {
	
	public static void save(Infraccion infraccion) {
		if (infraccion.getDni() == null || infraccion.getDni().isEmpty())
			throw new ValidateServiceException("El DNI es requerido");

		if (infraccion.getDni().length() > 8)
			throw new ValidateServiceException("El DNI es muy largo");
	}

}
