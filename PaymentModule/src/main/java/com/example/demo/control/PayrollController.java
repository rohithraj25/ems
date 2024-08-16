package com.example.demo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Payslip;
import com.example.demo.model.User;
import com.example.demo.service.PayrollService;

@RestController
@RequestMapping("/payroll")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    @PostMapping("/generate-payslips")
    public void generatePayslips() {
        payrollService.generateAndSendPayslips();
    }
    
    @PostMapping("/generate-payslip/{employeeId}")
    public ResponseEntity<String> generatePayslipById(@PathVariable Long employeeId) {
        boolean result = payrollService.generateAndSendPayslipById(employeeId);
        if (result) {
            return ResponseEntity.ok("Payslip generated and sent to employee with ID: " + employeeId);
        } else {
            return ResponseEntity.badRequest().body("Failed to generate payslip for employee with ID: " + employeeId);
        }
    }

    @GetMapping("/payslip/{empId}")
    public ResponseEntity<User> getPayslipById(@PathVariable int payslipId) {
        User payslip = payrollService.getPayslipById(payslipId);
        if (payslip != null) {
            return ResponseEntity.ok(payslip);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
