package com.kl.javamongodb.resources;

import com.kl.javamongodb.dto.AddressDTO;
import com.kl.javamongodb.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/addresses")
public class AddressResource {
    @Autowired
    private AddressService service;

    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAll() {
        List<AddressDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<AddressDTO>> findByPersonId(@PathVariable String id) {
        List<AddressDTO> addressDTO = service.findAllByPersonId(id);
        return ResponseEntity.ok().body(addressDTO);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> insert(@RequestBody AddressDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable String id, @RequestBody AddressDTO dto) {
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
