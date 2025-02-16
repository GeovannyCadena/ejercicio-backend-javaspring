package cuenta_movimientos.com.service.interfaces;

import cuenta_movimientos.com.dto.request.MovimientoDTO;
import cuenta_movimientos.com.dto.response.MovimientoResponseDTO;

import java.util.List;

public interface MovimientoService {

    // Crear movimiento
    MovimientoResponseDTO createMovimiento(MovimientoDTO movimientoDTO);

    // Actualizar movimiento
    MovimientoResponseDTO updateMovimiento(Long id, MovimientoDTO movimientoDTO);

    // Obtener todos los movimientos
    List<MovimientoResponseDTO> getAllMovimientos();

    // Obtener movimiento por ID
    MovimientoResponseDTO getMovimientoById(Long id);

    // Eliminar movimiento por ID
    void deleteMovimiento(Long id);

    // Obtener movimientos por cuenta
    List<MovimientoDTO> getMovimientosByCuentaId(Long cuentaId);
}
