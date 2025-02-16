package cliente_persona.com.service.interfaces;

import cliente_persona.com.dto.ClienteDTO;
import cliente_persona.com.dto.ClienteResponseDTO;

import java.util.List;

public interface ClienteService {
    // Crear o actualizar un cliente
    ClienteResponseDTO saveCliente(ClienteDTO cliente);

    // Obtener todos los clientes
    List<ClienteResponseDTO > getAllClientes();

    // Obtener un cliente por ID
    ClienteResponseDTO getClienteById(String id);

    // Buscar cliente por identificacion
    ClienteResponseDTO  getClienteByIdentificacion(String identificacion);

    // Eliminar un cliente por ID
    void deleteCliente(String id);

    ClienteResponseDTO updateCliente(String id, ClienteDTO clienteDTO);
}