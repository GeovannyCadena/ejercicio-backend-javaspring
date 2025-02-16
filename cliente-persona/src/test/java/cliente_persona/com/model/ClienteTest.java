package cliente_persona.com.model;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClienteTest {

    @Test
    public void testGettersAndSetters() {
        // Arrange
        Cliente cliente = new Cliente();

        // Datos heredados de Persona
        cliente.setId(1L);
        cliente.setNombre("Marianela");
        cliente.setGenero("Femenino");
        cliente.setEdad(30);
        cliente.setIdentificacion("225487");
        cliente.setDireccion("Calle Falsa 123");
        cliente.setTelefono("123456789");

        // Datos propios de Cliente
        cliente.setClienteId("CLI-001");
        cliente.setContrasena("secreta");
        cliente.setEstado(true);

        // Assert: Verificamos los datos heredados de Persona
        assertThat(cliente.getId()).isEqualTo(1L);
        assertThat(cliente.getNombre()).isEqualTo("Marianela");
        assertThat(cliente.getGenero()).isEqualTo("Femenino");
        assertThat(cliente.getEdad()).isEqualTo(30);
        assertThat(cliente.getIdentificacion()).isEqualTo("225487");
        assertThat(cliente.getDireccion()).isEqualTo("Calle Falsa 123");
        assertThat(cliente.getTelefono()).isEqualTo("123456789");

        // Assert: Verificamos los datos propios de Cliente
        assertThat(cliente.getClienteId()).isEqualTo("CLI-001");
        assertThat(cliente.getContrasena()).isEqualTo("secreta");
        assertThat(cliente.getEstado()).isTrue();
    }

    @Test
    public void testEqualsAndHashCode() {
        // Arrange
        Cliente cliente1 = new Cliente();
        cliente1.setClienteId("CLI-001");
        cliente1.setNombre("Marianela");

        Cliente cliente2 = new Cliente();
        cliente2.setClienteId("CLI-001");
        cliente2.setNombre("Marianela");

        Cliente cliente3 = new Cliente();
        cliente3.setClienteId("CLI-002");
        cliente3.setNombre("Juan");

        // Assert: Verificar que cliente1 y cliente2 son iguales
        assertThat(cliente1).isEqualTo(cliente2);

        // Assert: Verificar que cliente1 y cliente3 no son iguales
        assertThat(cliente1).isNotEqualTo(cliente3);

        // Assert: Verificar que el hashCode es consistente
        assertThat(cliente1.hashCode()).isEqualTo(cliente2.hashCode());
        assertThat(cliente1.hashCode()).isNotEqualTo(cliente3.hashCode());
    }

    @Test
    public void testToString() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setClienteId("CLI-001");
        cliente.setNombre("Marianela");

        // Act
        String resultadoToString = cliente.toString();

        // Assert: Verificamos que el toString contenga la información relevante
        assertThat(resultadoToString).contains("CLI-001");
        assertThat(resultadoToString).contains("Marianela");
    }
}
