package cuenta_movimientos.com.service.interfaces;

import cuenta_movimientos.com.dto.request.CuentaDTO;
import cuenta_movimientos.com.dto.response.CuentaResponseDTO;

import java.util.List;

public interface CuentaService {
    // Crear cuenta
    CuentaResponseDTO saveCuenta(CuentaDTO cuentaDTO);

    // Actualizar cuenta
    CuentaResponseDTO updateCuenta(Long id, CuentaDTO cuentaDTO);

    // Obtener todas las cuentas
    List<CuentaResponseDTO> getAllCuentas();

    // Obtener cuenta por ID
    CuentaResponseDTO getCuentaById(Long id);

    // Eliminar cuenta por ID
    void deleteCuenta(Long id);
}
