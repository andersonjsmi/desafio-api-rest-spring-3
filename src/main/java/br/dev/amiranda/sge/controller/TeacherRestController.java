/**
 * Controlador REST para gerenciar professores
 * Implementação de um CRUD
 */
package br.dev.amiranda.sge.controller;

import br.dev.amiranda.sge.domain.dto.TeacherDTO;
import br.dev.amiranda.sge.domain.models.Teacher;
import br.dev.amiranda.sge.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/teachers")
public class TeacherRestController {
    private final TeacherService service;


    public TeacherRestController(TeacherService service) {
        this.service = service;

    }

    @GetMapping
    public ResponseEntity<Set<TeacherDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> findById(@PathVariable Long id) {

        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TeacherDTO> save(@RequestBody TeacherDTO teacherDTO) {
        var teacherCreated = service.create(teacherDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(teacherCreated.id())
                .toUri();

        return ResponseEntity.created(location).body(teacherCreated);
    }

    @PutMapping
    public ResponseEntity<TeacherDTO> update(@RequestBody TeacherDTO teacherDTO) {
        var teacherUpdated = service.update(teacherDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(teacherUpdated.id())
                .toUri();

        return ResponseEntity.created(location).body(teacherUpdated);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Teacher> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
