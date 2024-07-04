package uz.work.worldcamp.service.contractService;

import uz.work.worldcamp.dtos.createDto.ContractCreateDto;
import uz.work.worldcamp.dtos.responceDto.ContractResponseDto;
import uz.work.worldcamp.entities.ContractEntity;

import java.util.List;
import java.util.UUID;

public interface ContractService {
    ContractResponseDto createContract(ContractCreateDto contractCreateDto);
    ContractResponseDto updateContract(UUID id, ContractCreateDto contractCreateDto);
    String deleteContract(UUID id);
    ContractResponseDto getContractById(UUID id);
    List<ContractResponseDto> getAllContracts(UUID universityId);
}
