package uz.work.worldcamp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.work.worldcamp.dtos.createDto.ContractCreateDto;
import uz.work.worldcamp.dtos.responceDto.ContractResponseDto;
import uz.work.worldcamp.service.contractService.ContractService;

import java.util.List;
import java.util.UUID;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contracts")
public class ContractController {
    private  final ContractService contractService;
    @Operation(
            description = "This method creates a new contract",
            method = "POST",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ContractResponseDto> createContract(@RequestBody ContractCreateDto contractCreateDto) {
        ContractResponseDto contract = contractService.createContract(contractCreateDto);
        return new ResponseEntity<>(contract, HttpStatus.CREATED);
    }

    @Operation(
            description = "This method updates an existing contract",
            method = "PUT",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ContractResponseDto> updateContract(@PathVariable UUID id, @RequestBody ContractCreateDto contractCreateDto) {
        ContractResponseDto contract = contractService.updateContract(id, contractCreateDto);
        return ResponseEntity.ok(contract);
    }

    @Operation(
            description = "This method deletes an existing contract",
            method = "DELETE",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{contractId}")
    public ResponseEntity<String> deleteContract(@PathVariable UUID contractId) {
        String s = contractService.deleteContract(contractId);
        return ResponseEntity.ok(s);
    }

    @Operation(
            description = "This method retrieves a contract by its ID",
            method = "GET",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{contractId}")
    public ResponseEntity<ContractResponseDto> getContractById(@PathVariable UUID contractId) {
        ContractResponseDto contract = contractService.getContractById(contractId);
        return ResponseEntity.ok(contract);
    }

    @Operation(
            description = "This method retrieves all contracts",
            method = "GET",
            security = @SecurityRequirement(name = "pre authorize", scopes = {"ADMIN"})
    )
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAll/{universityId}")
    public ResponseEntity<List<ContractResponseDto>> getAllContracts(@PathVariable UUID universityId) {
        List<ContractResponseDto> contracts = contractService.getAllContracts(universityId);
        return ResponseEntity.ok(contracts);
    }
}
