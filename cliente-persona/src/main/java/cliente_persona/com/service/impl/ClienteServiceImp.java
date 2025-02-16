package cliente_persona.com.service.impl;

import cliente_persona.com.dto.ClienteDTO;
import cliente_persona.com.dto.ClienteResponseDTO;
import cliente_persona.com.exception.NotFoundException;
import cliente_persona.com.model.Cliente;
import cliente_persona.com.service.ClienteProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cliente_persona.com.repository.interfaces.ClienteRepository;
import cliente_persona.com.service.interfaces.ClienteService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImp implements ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ClienteProducer clienteProducer;

    // Crear o actualizar un cliente
    public ClienteResponseDTO saveCliente(ClienteDTO clienteDTO) {
        Cliente cliente = mapToEntity(clienteDTO);
        Cliente clienteGuardado = clienteRepository.save(cliente);
        // Enviar mensaje a RabbitMQ
        clienteProducer.enviarMensajeCliente(clienteGuardado);
        ClienteResponseDTO responseDTO = mapToResponseDTO(clienteGuardado);
        System.out.println("ClienteResponseDTO: " + responseDTO);  // Depuración
        return responseDTO;
    }

    // Obtener todos los clientes
    public List<ClienteResponseDTO> getAllClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Obtener un cliente por ID
    public ClienteResponseDTO  getClienteById(String id) {
        Cliente cliente = clienteRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new NotFoundException("Cliente con ID " + id + " no encontrado."));
        return mapToResponseDTO(cliente);
    }

    // Buscar cliente por identificacion
    public ClienteResponseDTO getClienteByIdentificacion(String identificacion) {
        return clienteRepository.findByIdentificacion(identificacion);
    }

    // Eliminar un cliente por ID
    public void deleteCliente(String id) {
        clienteRepository.deleteById(Long.parseLong(id));
    }

    public ClienteResponseDTO updateCliente(String id, ClienteDTO clienteDTO) {
        Optional<Cliente> clienteExistente = clienteRepository.findById(Long.parseLong(id));
        if (clienteExistente.isPresent()) {
            // Actualizamos los datos del cliente con el DTO recibido
            Cliente cliente = clienteExistente.get();
            cliente.setNombre(clienteDTO.getNombre());
            cliente.setGenero(clienteDTO.getGenero());
            cliente.setEdad(clienteDTO.getEdad());
            cliente.setIdentificacion(clienteDTO.getIdentificacion());
            cliente.setDireccion(clienteDTO.getDireccion());
            cliente.setTelefono(clienteDTO.getTelefono());
            cliente.setClienteId(clienteDTO.getClienteId());
            cliente.setContrasena(clienteDTO.getContrasena());
            cliente.setEstado(clienteDTO.getEstado());

            // Guardamos el cliente actualizado
            Cliente clienteActualizado = clienteRepository.save(cliente);

            // Convertimos la entidad Cliente a DTO para devolverla
            return mapToResponseDTO(clienteActualizado);
        } else {
            throw new RuntimeException("Cliente no encontrado con el id: " + id);
        }
    }

    private Cliente mapToEntity(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        // 📌 Mapeo de campos de Persona
        cliente.setNombre(clienteDTO.getNombre());
        cliente.setGenero(clienteDTO.getGenero());
        cliente.setEdad(clienteDTO.getEdad());
        cliente.setIdentificacion(clienteDTO.getIdentificacion());
        cliente.setDireccion(clienteDTO.getDireccion());
        cliente.setTelefono(clienteDTO.getTelefono());

        // 📌 Mapeo de campos de Cliente
        cliente.setClienteId(clienteDTO.getClienteId());
        cliente.setContrasena(clienteDTO.getContrasena());
        cliente.setEstado(clienteDTO.getEstado());
        return cliente;
    }

    private ClienteResponseDTO mapToResponseDTO(Cliente cliente) {
        ClienteResponseDTO responseDTO = new ClienteResponseDTO();
        // 📌 Mapeo de campos de Persona
        responseDTO.setNombre(cliente.getNombre());
        responseDTO.setGenero(cliente.getGenero());
        responseDTO.setEdad(cliente.getEdad());
        responseDTO.setIdentificacion(cliente.getIdentificacion());
        responseDTO.setDireccion(cliente.getDireccion());
        responseDTO.setTelefono(cliente.getTelefono());

        // 📌 Mapeo de campos de Cliente
        responseDTO.setClienteId(cliente.getClienteId());
        responseDTO.setEstado(cliente.getEstado());
        return responseDTO;
    }

}
