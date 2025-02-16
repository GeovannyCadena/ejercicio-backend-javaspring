package cliente_persona.com.repository.interfaces;

import cliente_persona.com.dto.ClienteResponseDTO;

public interface ClienteRepositoryCustom {
    ClienteResponseDTO findByIdentificacion(String identificacion);
}
