/**
 * Controlador REST para gerenciar Classes
 * Implementação de um CRUD
 */
package br.dev.amiranda.sge.controller;

import br.dev.amiranda.sge.domain.dto.SchoolClassDTO;
import br.dev.amiranda.sge.domain.models.SchoolClass;
import br.dev.amiranda.sge.service.SchoolClassService;
import br.dev.amiranda.sge.service.StudentService;
import br.dev.amiranda.sge.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/classes")
public class SchoolClassRestController {
    private final SchoolClassService service;
    private final StudentService studentService;

    /**
     *
     * @param service Service de controle das Classes
     * @param  studentService Service de controle de estudantes
     */
    public SchoolClassRestController(SchoolClassService service, TeacherService teacherService, StudentService studentService) {
        this.service = service;
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Set<SchoolClassDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolClassDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SchoolClassDTO> save(@RequestBody SchoolClassDTO schoolClassDTO) {
        SchoolClassDTO schoolClassCreated = service.create(schoolClassDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(schoolClassCreated.id())
                .toUri();

        return ResponseEntity.created(location).body(schoolClassCreated);
    }

    @PutMapping
    public ResponseEntity<SchoolClassDTO> update(@RequestBody SchoolClassDTO schoolClassDTO) {
        SchoolClassDTO schoolClassUpdated = service.update(schoolClassDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(schoolClassUpdated.id())
                .toUri();

        return ResponseEntity.created(location).body(schoolClassUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SchoolClass> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/teacher/{teacherId}")
    public ResponseEntity<SchoolClassDTO> addTeacher(@PathVariable Long id, @PathVariable Long teacherId) {
        var schoolClassUpdated = this.service.addTeacher(id, teacherId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(schoolClassUpdated.id())
                .toUri();

        return ResponseEntity.created(location).body(schoolClassUpdated);
    }

    @PutMapping("{classId}/student/{studentId}")
    public ResponseEntity<SchoolClassDTO> addStudent(@PathVariable Long classId, @PathVariable Long studentId) {
        var classUpdated = this.service.addStudent(classId, studentId);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(classUpdated.id())
                .toUri();

        return ResponseEntity.created(location).body(classUpdated);
    }
}
