package com.java.io.Employee_Payroll;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import com.java.io.Employee_Payroll.EmployeePayrollService.IOService;

class EmployeePayrollServiceTest {

	@Test
	public void given3EmployeesWhenWrittenToFileSouldMatchemplyeeentries() {
		EmployeePayrollData[] arrayOfEmps = {
				new EmployeePayrollData(101, "Jeff Bezos", 100000.00),
				new EmployeePayrollData(111, "Bill Gates", 200000.00),
				new EmployeePayrollData(121, "Mark Zuckerberg", 300000.00)
		};
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
		employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
		employeePayrollService.printData(IOService.FILE_IO);
		long entries = employeePayrollService.countEntries(IOService.FILE_IO);
		assertEquals(3, entries);
	}
}
