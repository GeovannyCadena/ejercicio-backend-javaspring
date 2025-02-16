package cliente_persona.com.controller;

import cliente_persona.com.dto.ClienteDTO;
import cliente_persona.com.dto.ClienteResponseDTO;
import cliente_persona.com.service.impl.ClienteServiceImp;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteServiceImp clienteService;

//    @MockBean
//    private ClienteRepository clienteRepository;  // Mockeamos el repositorio

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Se configura el MockMvc antes de cada prueba
//        mockMvc = MockMvcBuilders.standaloneSetup(new ClienteController(clienteService)).build();
    }

    @Test
    public void testCrearCliente() throws Exception {
        //PARA QUE FUNCIONE EL TEST SE DEBE COMENTAR LA CONFIGURACION DE RabbitMQ
        // Arrange: Crear un cliente con todos los campos de la clase Cliente y Persona
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre("Marianela");
        clienteDTO.setGenero("Femenino");
        clienteDTO.setEdad(30);
        clienteDTO.setIdentificacion("1234567894");
        clienteDTO.setDireccion("Calle Ficticia 123");
        clienteDTO.setTelefono("123456789");
        clienteDTO.setClienteId("CLI-001");
        clienteDTO.setContrasena("password123");
        clienteDTO.setEstado(true);

//        Cliente clienteGuardado = new Cliente();
//        clienteGuardado.setNombre(clienteDTO.getNombre());
//        clienteGuardado.setGenero(clienteDTO.getGenero());
//        clienteGuardado.setEdad(clienteDTO.getEdad());
//        clienteGuardado.setIdentificacion(clienteDTO.getIdentificacion());
//        clienteGuardado.setDireccion(clienteDTO.getDireccion());
//        clienteGuardado.setTelefono(clienteDTO.getTelefono());
//        clienteGuardado.setClienteId(clienteDTO.getClienteId());
//        clienteGuardado.setContrasena(clienteDTO.getContrasena());
//        clienteGuardado.setEstado(clienteDTO.getEstado());

        // Mockear el comportamiento del repositorio para que retorne el cliente guardado
//        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteGuardado);

        ClienteResponseDTO clienteGuardadoResponse = new ClienteResponseDTO();
        clienteGuardadoResponse.setNombre(clienteDTO.getNombre());
        clienteGuardadoResponse.setGenero(clienteDTO.getGenero());
        clienteGuardadoResponse.setEdad(clienteDTO.getEdad());
        clienteGuardadoResponse.setIdentificacion(clienteDTO.getIdentificacion());
        clienteGuardadoResponse.setDireccion(clienteDTO.getDireccion());
        clienteGuardadoResponse.setTelefono(clienteDTO.getTelefono());
        clienteGuardadoResponse.setClienteId(clienteDTO.getClienteId());
        clienteGuardadoResponse.setEstado(clienteDTO.getEstado());

        // Mockear el comportamiento del service para que retorne el cliente guardado
        when(clienteService.saveCliente(any(ClienteDTO.class))).thenReturn(clienteGuardadoResponse);

        // Act & Assert: Simular el POST y verificar los campos de la respuesta
        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Marianela"))
                .andExpect(jsonPath("$.genero").value("Femenino"))
                .andExpect(jsonPath("$.edad").value(30))
                .andExpect(jsonPath("$.identificacion").value("1234567894"))
                .andExpect(jsonPath("$.direccion").value("Calle Ficticia 123"))
                .andExpect(jsonPath("$.telefono").value("123456789"))
                .andExpect(jsonPath("$.clienteId").value("CLI-001"))
                .andExpect(jsonPath("$.estado").value(true));
    }
}
