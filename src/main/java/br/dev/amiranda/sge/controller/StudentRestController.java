/**
 * Controlador REST para gerenciar estudantes
 * Implementação de um CRUD
 */

package br.dev.amiranda.sge.controller;

import br.dev.amiranda.sge.domain.dto.StudentDTO;
import br.dev.amiranda.sge.domain.models.Student;
import br.dev.amiranda.sge.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/students")
public class StudentRestController {
    private final StudentService service;

    public StudentRestController(StudentService service) {

        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Set<StudentDTO>> list() {

        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable Long id) {

        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<StudentDTO> save(@RequestBody StudentDTO studentDTO) {
        var studentCreated = service.create(studentDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(studentCreated.id())
                .toUri();

        return ResponseEntity.created(location).body(studentCreated);
    }

    @PutMapping
    public ResponseEntity<StudentDTO> update(@RequestBody StudentDTO studentDTO) {
        var studentUpdated = service.update(studentDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(studentUpdated.id())
                .toUri();

        return ResponseEntity.created(location).body(studentUpdated);
    }

    @DeleteMapping("/id")
    public ResponseEntity<Student> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }




}
