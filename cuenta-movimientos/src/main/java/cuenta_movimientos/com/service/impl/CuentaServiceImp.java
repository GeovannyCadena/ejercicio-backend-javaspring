package cuenta_movimientos.com.service.impl;

import cuenta_movimientos.com.dto.request.CuentaDTO;
import cuenta_movimientos.com.dto.response.CuentaResponseDTO;
import cuenta_movimientos.com.exception.NotFoundException;
import cuenta_movimientos.com.model.Cuenta;
import cuenta_movimientos.com.repository.interfaces.CuentaRepository;
import cuenta_movimientos.com.service.interfaces.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CuentaServiceImp implements CuentaService {

    private final CuentaRepository cuentaRepository;

    @Autowired
    public CuentaServiceImp(CuentaRepository cuentaRepository) {
        this.cuentaRepository = cuentaRepository;
    }

    // Crear o actualizar cuenta
    public CuentaResponseDTO saveCuenta(CuentaDTO cuentaDTO) {
        Cuenta cuenta = mapToEntity(cuentaDTO);
        return mapToResponseDTO(cuentaRepository.save(cuenta));
    }

    // **Actualizar cuenta existente**
    @Override
    public CuentaResponseDTO updateCuenta(Long id, CuentaDTO cuentaDTO) {
        // Verificar si la cuenta existe
        Cuenta cuentaExistente = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta con ID " + id + " no encontrada para actualizar."));

        // Mapeo manual de DTO a entidad
        cuentaExistente.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        cuentaExistente.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuentaExistente.setSaldoInicial(cuentaDTO.getSaldoInicial());
        cuentaExistente.setEstado(cuentaDTO.getEstado());

        return mapToResponseDTO(cuentaRepository.save(cuentaExistente));
    }

    // **Obtener todas las cuentas**
    @Override
    public List<CuentaResponseDTO> getAllCuentas() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        List<CuentaResponseDTO> responseDTOs = new ArrayList<>();

        // Mapeo manual de Entity a ResponseDTO
        for (Cuenta cuenta : cuentas) {
            responseDTOs.add(mapToResponseDTO(cuenta));
        }

        return responseDTOs;
    }

    // Obtener cuenta por ID
    public CuentaResponseDTO getCuentaById(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id).orElseThrow(() -> new NotFoundException("Cuenta con ID " + id + " no encontrado."));
        return mapToResponseDTO(cuenta);
    }

    // Eliminar cuenta por ID
    public void deleteCuenta(Long id) {
        // Verificar si la cuenta existe antes de eliminar
        if (!cuentaRepository.existsById(id)) {
            throw new RuntimeException("Cuenta con ID " + id + " no encontrada para eliminar.");
        }
        cuentaRepository.deleteById(id);
    }

    private Cuenta mapToEntity(CuentaDTO cuentaDTO) {
        Cuenta cuenta = new Cuenta();
        cuenta.setEstado(cuentaDTO.getEstado());
        cuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
        cuenta.setTipoCuenta(cuentaDTO.getTipoCuenta());
        cuenta.setSaldoInicial(cuentaDTO.getSaldoInicial());
        return cuenta;
    }

    private CuentaResponseDTO mapToResponseDTO(Cuenta cuenta) {
        CuentaResponseDTO responseDTO = new CuentaResponseDTO();
        responseDTO.setId(cuenta.getId());
        responseDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
        responseDTO.setTipoCuenta(cuenta.getTipoCuenta());
        responseDTO.setSaldoInicial(cuenta.getSaldoInicial());
        responseDTO.setEstado(cuenta.getEstado());
        return responseDTO;
    }
}
