package tech.cognity.almacen.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InfraccionDTO {
	private int id;
	private String dni;
	private Date fecha;
	private String falta;
	private String infraccion;
	private String descripcion;
	private int activo;	
}
