package com.java.io.Employee_Payroll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import com.java.io.Employee_Payroll.EmployeePayrollService.IOService;

class EmployeePayrollServiceTest {
	EmployeePayrollData[] arrayOfEmps = {
			new EmployeePayrollData(101, "Jeff Bezos", 100000.00),
			new EmployeePayrollData(111, "Bill Gates", 200000.00),
			new EmployeePayrollData(121, "Mark Zuckerberg", 300000.00)
	};
	EmployeePayrollService employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));


	@Test
	public void given3EmployeesWhenWrittenToFileSouldMatchemplyeeentries() {
		employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
		employeePayrollService.printData(IOService.FILE_IO);
		long entries = employeePayrollService.countEntries(IOService.FILE_IO);
		assertEquals(3, entries);
	}
	
	@Test
	public void test() {
		long count = employeePayrollService.readDataFromFile(IOService.FILE_IO);
		assertEquals(3, count);
	}
	
	@Test
	public void givenEmployeePayrollInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		assertEquals(3, employeePayrollData.size());
	}
	
	@Test
	public void givenNewSalaryFor_Employee_WhenUpdated_ShouldSyncWithDB() {
		employeePayrollService.updateEmployeeSalary("Mark", 5000000.00);
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Mark");
		assertTrue(result);
	}
	
	@Test
	public void givenDateRange_WhenRetrieved_ShouldMatchEmployeeCount() {
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		LocalDate startDate = LocalDate.of(2019, 01, 01);
		LocalDate endDate = LocalDate.now();
		List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataForDateRange(IOService.DB_IO, startDate, endDate);
		assertEquals(3, employeePayrollData.size());
	}
	
	@Test
	public void givenPayrollData_WhenAverageSalaryRetrievedByGender_ShouldReturnCorrectResults() {
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		Map<String, Double> averageSalaryByGender = employeePayrollService.readAverageSalaryByGender(IOService.DB_IO);
		assertTrue(averageSalaryByGender.get("M").equals(2000000.00) && averageSalaryByGender.get("F").equals(3727890.00));
	}
	
	@Test
	public void givenNewEmployee_WhenAdded_ShouldSyncWithDB() {
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.addEmployeeToPayroll("Melissa", 5000000.00, LocalDate.now(), "F");
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Melissa");
		assertTrue(result);
	}
	
	@Test
	public void givenNewEmployee_WhenAdded_ShouldSyncWithDBInAllTables() {
		employeePayrollService.readEmployeePayrollData(IOService.DB_IO);
		employeePayrollService.addEmployeeToPayrollToBothTables("John", 8326782.00, LocalDate.now(), "M");
		boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("John");
		assertTrue(result);
	}
}
