package cliente_persona.com.repository.impl;

import cliente_persona.com.dto.ClienteResponseDTO;
import org.springframework.stereotype.Repository;
import cliente_persona.com.repository.interfaces.ClienteRepositoryCustom;

@Repository
public class ClienteRepositoryImp implements ClienteRepositoryCustom {

    // Puedes agregar métodos de consulta personalizados aquí si es necesario

    // Ejemplo: Buscar por identificación (única para cada cliente)
    @Override
    public ClienteResponseDTO findByIdentificacion(String identificacion) {
        return null;
    }
}
