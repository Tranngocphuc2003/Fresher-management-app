package com.springboot.spring_boot_project.controller;

import com.springboot.spring_boot_project.dto.request.ApiResponse;
import com.springboot.spring_boot_project.dto.request.AssignmentCreationRequest;
import com.springboot.spring_boot_project.dto.request.AssignmentUpdateRequest;
import com.springboot.spring_boot_project.entity.Assignment;
import com.springboot.spring_boot_project.exception.AppException;
import com.springboot.spring_boot_project.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Validated
@RestController
@RequestMapping("/assignment")
public class AssignmentController {
    @Autowired
    private AssignmentService assignmentService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    ApiResponse<Assignment> createAssignment(@RequestBody @Valid AssignmentCreationRequest request){
        try {
            Assignment createAssignment = assignmentService.createAssignment(request);
            ApiResponse<Assignment> apiResponse = new ApiResponse<>(200, "Add score successfully", createAssignment);
            return apiResponse;
        }catch (AppException e){
            return new ApiResponse<>(e.getErrorCode().getCode(), e.getErrorCode().getMessage(), null);
        }
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    ApiResponse<List<Assignment>> getAllAssignment(){
        List<Assignment> getAssignments = assignmentService.getAllScore();
        ApiResponse<List<Assignment>> apiResponse = new ApiResponse<>(200, "Get all score successfully", getAssignments);
        return apiResponse;
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/fresher/{id}")
    ApiResponse<Assignment> getScoreByFresherId(@PathVariable("id") int fresherId){
        try {
            Assignment assignment = assignmentService.getScoreByFresherId(fresherId);
            return new ApiResponse<>(200, "Fresher found", assignment);
        }catch (AppException e){
            return new ApiResponse<>(e.getErrorCode().getCode(), e.getErrorCode().getMessage(), null);
        }
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/fresher/{fresherId}")
    ApiResponse<Assignment> updateScore(@RequestBody @Valid AssignmentUpdateRequest request, @PathVariable("fresherId") int fresherId){
        try {
            Assignment updateAssignment = assignmentService.updateAssignment(request, fresherId);
            ApiResponse<Assignment> apiResponse = new ApiResponse<>(200, "Update score successfully", updateAssignment);
            return apiResponse;
        } catch (AppException e){
            return new ApiResponse<>(e.getErrorCode().getCode(), e.getErrorCode().getMessage(), null);
        }
    }
}
