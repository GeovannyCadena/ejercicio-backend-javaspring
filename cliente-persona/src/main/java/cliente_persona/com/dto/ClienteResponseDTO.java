package cliente_persona.com.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponseDTO {

    // 📌 Campos de Persona
    private String nombre;
    private String genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;

    // 📌 Campos de Cliente
    private String clienteId;
    private Boolean estado;
}
