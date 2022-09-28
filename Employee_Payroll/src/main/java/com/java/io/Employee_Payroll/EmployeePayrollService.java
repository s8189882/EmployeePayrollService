package com.java.io.Employee_Payroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class EmployeePayrollService {
	
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	}
	
	private List<EmployeePayrollData> employeePayrollList;
	
	public EmployeePayrollService() {
		
	}
	
	public EmployeePayrollService(List<EmployeePayrollData> employeePayrollList) {
		this.employeePayrollList = employeePayrollList;
	}

	
	public static void main(String[] args) {
		ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<>();
		EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
		Scanner consoleInputReader = new Scanner(System.in);
		employeePayrollService.readEmployeePayrollData(consoleInputReader);
		employeePayrollService.writeEmployeePayrollData(EmployeePayrollService.IOService.CONSOLE_IO);
	}

	private void readEmployeePayrollData(Scanner consoleInputReader) {
		System.out.println("Enter Employee ID : ");
		int id = consoleInputReader.nextInt();
		System.out.println("Enter Employee Name : ");
		String name = consoleInputReader.next();
		System.out.println("Enter Employee Salary : ");
		double salary = consoleInputReader.nextDouble();
		employeePayrollList.add(new EmployeePayrollData(id, name, salary));
	}
	
	public void writeEmployeePayrollData(IOService ioService) {
		if (ioService.equals(IOService.CONSOLE_IO))
			System.out.println("\nWriting Employee Payroll Roaster to Console\n" + employeePayrollList);	
		else if (ioService.equals(IOService.FILE_IO))
			new EmployeePayrollFileIOService().writeData(employeePayrollList);
	}

	public void printData(IOService fileIo) {
		if(fileIo.equals(IOService.FILE_IO))
			new EmployeePayrollFileIOService().printData();
	}

	public long countEntries(IOService fileIo) {
		long count = 0;
		if(fileIo.equals(IOService.FILE_IO))
			count = new EmployeePayrollFileIOService().countEntries();
		return count;
	}

	public long readDataFromFile(IOService fileIo) {
		long count = 0;
		List<String> employees = new ArrayList<>();
		if(fileIo.equals(IOService.FILE_IO)) {
			employees = new EmployeePayrollFileIOService().readData();
			System.out.println("Data read from file :\n" + employees);
			count = employees.size();
		}
		return count;
	}
}
